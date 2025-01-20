package com.ws.langchain4j.utils;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
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
public class OllamaModelUtil {

    //public static final String OLLAMA_API_KEY = "sk-211PNl36WaZpoS6iCq36q3NH7DKl899lmHLafWnNr65ctBtI";

    public static final String OLLAMA_BASE_URL = "http://127.0.0.1:11434";


    public static final String OLLAMA_MODE_NAME = "qwen2:7b";


    /**
     * 获取模型
     * @return
     */
    public static ChatLanguageModel getModel() {
        ChatLanguageModel model = OllamaChatModel.builder()
                //.apiKey(OLLAMA_API_KEY)
                .modelName(OLLAMA_MODE_NAME)
                .baseUrl(OLLAMA_BASE_URL)
                .logRequests(true)
                .logResponses(true)
                .build();
        return model ;
    }

    /**
     * 获取流式模型
     * @return
     */
    public static StreamingChatLanguageModel getStreamModel() {
        StreamingChatLanguageModel model = OllamaStreamingChatModel.builder()
                //.apiKey(OLLAMA_API_KEY)
                .modelName(OLLAMA_MODE_NAME)
                .baseUrl(OLLAMA_BASE_URL)
                .logRequests(true)
                .logResponses(true)
                .build();
        return model ;
    }

    /**
     * 获取图片模型
     * @return
     */
    /*public static ImageModel getImageModel() {
        ImageModel model = OllamaIm.builder()
                //.apiKey(OLLAMA_API_KEY)
                .modelName(OLLAMA_MODE_NAME)
                .baseUrl(OLLAMA_BASE_URL)
                .logRequests(true)
                .logResponses(true)
                .build();
        return model ;
    }*/

    /**
     * 获取向量模型
     * @return
     */
    public static EmbeddingModel getEmbeddingModel() {
        EmbeddingModel model = OllamaEmbeddingModel.builder()
                //.apiKey(OLLAMA_API_KEY)
                .modelName(OLLAMA_MODE_NAME)
                .baseUrl(OLLAMA_BASE_URL)
                .logRequests(true)
                .logResponses(true)
                .build();
        return model ;
    }

    /**
     * 获取向量存储
     * @param dimension
     * @param indexName
     * @return
     */
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

    