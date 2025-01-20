package com.ws.langchain4j.test;

import com.ws.langchain4j.base.BaseTest;
import com.ws.langchain4j.utils.OllamaModelUtil;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * @author yunhua
 * @date 2025-01-18
 * @see
 * @since 1.0.0
 */
public class OllamaServiceTest extends BaseTest {

    ChatLanguageModel model = OllamaModelUtil.getModel() ;

    interface Writer{
        @SystemMessage("你是个散文作家，根据输入的题目写一篇200字以内的散文")
        String write(String content);
    }

    public void testWrite(){
        Writer writer = AiServices.create(Writer.class,model) ;
        String content = writer.write("我最感谢的人") ;
        System.out.println(content);
    }

    interface Writer2{
        @SystemMessage("你是个散文作家，写一篇散文，题目是{{title}}，字数不超过{{count}}")
        String write(@UserMessage String content, @V("title") String title, @V("count") Long count);
    }

    public void testWrite2(){
        Writer2 writer = AiServices.create(Writer2.class,model) ;
        String content = writer.write("写一篇散文", "最爱看的电影", 200L) ;
        System.out.println(content);
    }
}

    