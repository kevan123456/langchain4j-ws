package com.ws.langchain4j.test;

import com.ws.langchain4j.base.BaseTest;
import com.ws.langchain4j.utils.OpenAiModelUtil;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.Test;

/**
 * @author yunhua
 * @date 2025-01-12
 * @see
 * @since 1.0.0
 */
public class OpenAiChatMemoryTest extends BaseTest {

    @Test
    public void test() {
        ChatLanguageModel model = OpenAiModelUtil.getModel() ;
        ChatMemory chatMemory=MessageWindowChatMemory.builder()
                .maxMessages(10)
                .build();

        chatMemory.add(UserMessage.from("你好，我是小明，你是我最好的兄弟，你叫阿强"));
        AiMessage aiMessage = model.generate(chatMemory.messages()).content();
        System.out.println(aiMessage.text());
        chatMemory.add(aiMessage);

        chatMemory.add(UserMessage.from("小明最好的兄弟是谁？"));
        AiMessage aiMessage2 = model.generate(chatMemory.messages()).content();
        System.out.println(aiMessage2.text());
        chatMemory.add(aiMessage2);

    }
}

    