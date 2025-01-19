package com.ws.langchain4j.controller;

import com.ws.langchain4j.utils.OpenAiModelUtil;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.model.output.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yunhua
 * @date 2025-01-10
 * @see
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("openAI")
public class OpenAiController {

    @GetMapping("/test")
    public String test(@RequestParam(value = "message",defaultValue = "给我讲个笑话") String message) {

        return "openAI hello word" ;
    }

    @GetMapping("/demo")
    public String demo(@RequestParam(value = "message",defaultValue = "给我讲个笑话") String message) {
        ChatLanguageModel model =  OpenAiModelUtil.getDemoModel() ;
        String answer = model.generate(message);

        return  answer;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(value = "message",defaultValue = "给我讲个笑话") String message) {
        ChatLanguageModel model =  OpenAiModelUtil.getModel() ;
        String answer = model.generate(message);
        return  answer;
    }

    @GetMapping("/stream")
    public void stream(@RequestParam(value = "message",defaultValue = "给我讲个笑话") String message) {
        StreamingChatLanguageModel streamingModel = OpenAiStreamingChatModel.builder()
                .apiKey("sk-211PNl36WaZpoS6iCq36q3NH7DKl899lmHLafWnNr65ctBtI")
                .baseUrl("https://api.xty.app")
                .build();

        streamingModel.generate("你好啊",new StreamingResponseHandler<AiMessage>(){
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
                log.error("stream",error);
                System.out.println("onError");
            }
        });
    }
}

    