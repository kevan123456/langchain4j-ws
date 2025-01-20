package com.ws.langchain4j.test;

import com.ws.langchain4j.base.BaseTest;
import com.ws.langchain4j.utils.OllamaModelUtil;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.output.Response;
import org.junit.Test;

/**
 * @author yunhua
 * @date 2025-01-11
 * @see
 * @since 1.0.0
 */
public class OllamaAiChatTest extends BaseTest {


    StreamingChatLanguageModel streamModel = OllamaModelUtil.getStreamModel() ;

    ChatLanguageModel model =  OllamaModelUtil.getModel() ;

    //ImageModel imageModel = OpenAiModelUtil.getImageModel() ;


    /**
     * 自定义key
     */
    @Test
    public void testChat() {
        UserMessage userMessage = UserMessage.from("你好");
        Response<AiMessage> response = null ;
        try {
            response = model.generate(userMessage);
        }catch (Exception e){
            System.out.println("message:"+e.getMessage());
            System.out.println("cause:"+e.getCause());
        }

        AiMessage aiMessage = response.content() ;
        System.out.println("aiMessage1:"+aiMessage.text());
        UserMessage userMessage2 = UserMessage.from("请重复一次刚说过的话");
        Response<AiMessage> response2 = model.generate(userMessage,aiMessage,userMessage2) ;
        AiMessage aiMessage2 = response2.content() ;
        System.out.println("aiMessage2:"+aiMessage2.text());
    }

    /**
     * 使用流式处理
     * @throws Exception
     */
    @Test
    public void testStreaming() throws Exception{
        streamModel.generate("给我讲个笑话",new StreamingResponseHandler<AiMessage>(){
            @Override
            public void onNext(String token) {
                System.out.print(token);
            }
            @Override
            public void onComplete(Response<AiMessage> response) {
                System.out.println("onComplete");
            }
            @Override
            public void onError(Throwable error) {
                System.out.println("onError");
            }
        });
    }

    @Test
    public void testImage(){
        /*Response<Image> response = imageModel.generate("画一个狮子头") ;
        System.out.println(response.content().url());*/
    }
}

    