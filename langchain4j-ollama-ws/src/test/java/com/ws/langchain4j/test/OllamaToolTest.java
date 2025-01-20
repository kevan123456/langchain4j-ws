package com.ws.langchain4j.test;

import com.ws.langchain4j.base.BaseTest;
import com.ws.langchain4j.utils.OllamaModelUtil;
import dev.langchain4j.agent.tool.*;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.tool.DefaultToolExecutor;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author yunhua
 * @date 2025-01-12
 * @see
 * @since 1.0.0
 */
public class OllamaToolTest extends BaseTest {

    private ChatLanguageModel model = OllamaModelUtil.getModel() ;

    @Tool("获取当前日期")
    public static String dateUtil(){
        return LocalDateTime.now().toString();
    }

    @Test
    public void test1() throws Exception{
        ToolSpecification toolSpecification = ToolSpecifications.toolSpecificationFrom(OllamaToolTest.class.getMethod("dateUtil")) ;
        UserMessage userMessage = UserMessage.from("今天几月几号");
        Response<AiMessage> aiMessageResponse = model.generate(Collections.singletonList(userMessage)) ;
        AiMessage aiMessage = aiMessageResponse.content() ;
        System.out.println(aiMessage);
        if(aiMessage.hasToolExecutionRequests()){
            List<ToolExecutionRequest> toolExecutionRequestList = aiMessage.toolExecutionRequests() ;
            for (ToolExecutionRequest toolExecutionRequest : toolExecutionRequestList){
                String methodName = toolExecutionRequest.name();
                Method method = OllamaToolTest.class.getMethod(methodName) ;

                String result = (String)method.invoke(null) ;
                System.out.println(result);
                ToolExecutionResultMessage toolExecutionResultMessage = ToolExecutionResultMessage.from(toolExecutionRequest.id(),toolExecutionRequest.name(),result);
                Response<AiMessage> response = model.generate(Lists.newArrayList(userMessage,aiMessage,toolExecutionResultMessage)) ;
                System.out.println(response.content().text());
            }
        }
    }


    class WeatherUtil{
        @Tool("获取某一个具体城市的天气")
        public String getWeather(@P("指定的城市") String city){
            return city+"今天天气晴朗";
        }
    }


    /**
     * 带参数的工具调用
     */
    @Test
    public void test2() {
        //构建工具
        List<ToolSpecification> toolSpecificationList = ToolSpecifications.toolSpecificationsFrom(WeatherUtil.class) ;
        List<ChatMessage> chatMessageList = new ArrayList<>() ;
        UserMessage userMessage1 = UserMessage.from("北京今天天气怎么样？");
        chatMessageList.add(userMessage1) ;
        //第一次AI交互需要调用的工具
        Response<AiMessage> aiMessageResponse = model.generate(chatMessageList,toolSpecificationList) ;
        AiMessage aiMessage = aiMessageResponse.content() ;
        List<ToolExecutionRequest> toolExecutionRequestList = aiMessage.toolExecutionRequests() ;

        //判断是否需要调用工具
        if(aiMessage.hasToolExecutionRequests()){
            for (ToolExecutionRequest toolExecutionRequest : toolExecutionRequestList){
                String methodName = toolExecutionRequest.name();
                String arguments = toolExecutionRequest.arguments();
                System.out.println("调用的方法："+methodName);
                System.out.println("调用参数"+arguments);
            }
        }
        chatMessageList.add(aiMessage) ;
        WeatherUtil weatherUtil = new WeatherUtil();
        //将工具调用的方法与聊天消息一起传给AI模型
        toolExecutionRequestList.forEach(toolExecutionRequest -> {
            DefaultToolExecutor toolExecutor = new DefaultToolExecutor(weatherUtil,toolExecutionRequest);
            String result = toolExecutor.execute(toolExecutionRequest, UUID.randomUUID().toString()) ;
            System.out.println("工具执行结果："+result);
            ToolExecutionResultMessage toolExecutionResultMessage = ToolExecutionResultMessage.from(toolExecutionRequest,result);
            chatMessageList.add(toolExecutionResultMessage);
        });

        //拿到本地工具最终执行后的结果
        AiMessage finalResponse = model.generate(chatMessageList).content() ;
        System.out.println("最终结果："+finalResponse.text());

    }
}

    