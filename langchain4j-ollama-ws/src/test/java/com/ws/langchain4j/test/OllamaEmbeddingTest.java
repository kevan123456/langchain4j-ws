package com.ws.langchain4j.test;

import com.ws.langchain4j.base.BaseTest;
import com.ws.langchain4j.utils.OllamaModelUtil;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.junit.Test;

import java.util.List;

/**
 * @author yunhua
 * @date 2025-01-19
 * @see
 * @since 1.0.0
 */
public class OllamaEmbeddingTest extends BaseTest {

    EmbeddingModel embeddingModel = OllamaModelUtil.getEmbeddingModel();
    EmbeddingStore embeddingStore = OllamaModelUtil.getEmbeddingStore(null,null);

    @Test
    public void testEmbedding() {
        String text = "你好，我叫kevan";
        Response<Embedding>  embed= embeddingModel.embed(text);
        System.out.println(embed);
        System.out.println(embed.content().vector().length);



        //向量结果存到redis
        embeddingStore.add(embed.content()) ;

        //比较相关性
        List<EmbeddingMatch<TextSegment>> matchList =  embeddingStore.findRelevant(embeddingModel.embed("我的名字叫kevan").content(),10,-1);
        for (EmbeddingMatch<TextSegment> embeddingMatch : matchList){
            //打印相似度
            System.out.println(embeddingMatch.score());
        }



    }



}

    