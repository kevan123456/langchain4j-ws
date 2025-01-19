package com.ws.langchain4j.test;

import com.ws.langchain4j.base.BaseTest;
import com.ws.langchain4j.utils.OpenAiModelUtil;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.agent.tool.ToolSpecifications;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.*;

import java.time.LocalDateTime;

/**
 * @author yunhua
 * @date 2025-01-18
 * @see
 * @since 1.0.0
 */
public class OpenAiService2Test extends BaseTest {

    ChatLanguageModel demoModel = OpenAiModelUtil.getDemoModel() ;

    interface Assistent{
        String chat(@MemoryId Long memoryId, @UserMessage String message);
    }

    @Tool
    public static String dataUtil(){
        return LocalDateTime.now().toString() ;
    }

    public void test() throws Exception{
        ToolSpecification toolSpecification = ToolSpecifications.toolSpecificationFrom(OpenAiService2Test.class.getMethod("dataUtil")) ;

        Assistent assistent = AiServices.builder(Assistent.class)
                .chatLanguageModel(demoModel)
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(100))
                .tools(toolSpecification)
                .build();

        System.out.println(assistent.chat(1L,"你好，我是小李。"));
        System.out.println(assistent.chat(1L,"我的名字是什么？"));

        System.out.println(assistent.chat(2L,"你好，我是老王。"));
        System.out.println(assistent.chat(2L,"我的名字是什么？"));

    }


}

    