package com.ws.langchain4j.utils;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiImageModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.redis.RedisEmbeddingStore;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author yunhua
 * @date 2025-01-18
 * @see
 * @since 1.0.0
 */
public class OpenAiModelUtil {

    public static final String OPENAI_API_KEY = "sk-211PNl36WaZpoS6iCq36q3NH7DKl899lmHLafWnNr65ctBtI";

    public static final String OPENAI_BASE_URL = "https://api.xty.app";


    public static final String OPENAI_MODE_NAME = "gpt-3.5-turbo";

    public static ChatLanguageModel getDemoModel() {
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .build();
        return model ;
    }

    public static StreamingChatLanguageModel getDemoStreamModel() {
        StreamingChatLanguageModel model = OpenAiStreamingChatModel.builder()
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .build();
        return model ;
    }

    public static ChatLanguageModel getModel() {
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(OPENAI_API_KEY)
                .modelName(OPENAI_MODE_NAME)
                .baseUrl(OPENAI_BASE_URL)
                .logRequests(true)
                .logResponses(true)
                .build();
        return model ;
    }

    public static StreamingChatLanguageModel getStreamModel() {
        StreamingChatLanguageModel model = OpenAiStreamingChatModel.builder()
                .apiKey(OPENAI_API_KEY)
                .modelName(OPENAI_MODE_NAME)
                .baseUrl(OPENAI_BASE_URL)
                .logRequests(true)
                .logResponses(true)
                .build();
        return model ;
    }

    public static ImageModel getImageModel() {
        ImageModel model = OpenAiImageModel.builder()
                .apiKey(OPENAI_API_KEY)
                .modelName(OPENAI_MODE_NAME)
                .baseUrl(OPENAI_BASE_URL)
                .logRequests(true)
                .logResponses(true)
                .build();
        return model ;
    }

    public static EmbeddingModel getEmbeddingModel() {
        EmbeddingModel model = OpenAiEmbeddingModel.builder()
                .apiKey(OPENAI_API_KEY)
                .modelName(OPENAI_MODE_NAME)
                .baseUrl(OPENAI_BASE_URL)
                .logRequests(true)
                .logResponses(true)
                .build();
        return model ;
    }

    public static EmbeddingStore getEmbeddingStore(Integer dimension,String indexName) {
        if(Objects.isNull(dimension)){
            dimension = getEmbeddingModel().dimension() ;
        }
        if(StringUtils.isBlank(indexName)){

        }
        EmbeddingStore embeddingStore = RedisEmbeddingStore.builder()
                .host("127.0.0.1")
                .port(6379)
                .password("123456")
                .dimension(dimension)
                .indexName(indexName)
                .build();
        return embeddingStore ;
    }

}

    