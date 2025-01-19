package com.ws.langchain4j.test;

import com.ws.langchain4j.utils.MyDocumentSplitter;
import com.ws.langchain4j.utils.OpenAiModelUtil;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.injector.ContentInjector;
import dev.langchain4j.rag.content.injector.DefaultContentInjector;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author yunhua
 * @date 2025-01-19
 * @see
 * @since 1.0.0
 */
public class OpenAiRAGTest {

    private EmbeddingStore embeddingStore = OpenAiModelUtil.getEmbeddingStore(null,"meituan-index") ;
    private EmbeddingModel embeddingModel = OpenAiModelUtil.getEmbeddingModel() ;
    private ChatLanguageModel model = OpenAiModelUtil.getModel() ;
    @Test
    public void test() throws Exception {
        String question = "我要退款，多久到账？" ;

        //1、读取本地知识库文件
        Path docPath = Paths.get(OpenAiRAGTest.class.getClassLoader().getResource("meituan-questin.txt").toURI()) ;
        DocumentParser parser = new TextDocumentParser() ;
        Document document = FileSystemDocumentLoader.loadDocument(docPath,parser);
        //把知识文件分解一个个条目
        DocumentSplitter documentSplitter = new MyDocumentSplitter() ;
        List<TextSegment> segmentList = documentSplitter.split(document) ;
        //2、对每个条目进行文本向量化，并保存redis中
        List<Embedding> embeddingList = embeddingModel.embedAll(segmentList).content();
        //保存到redis中，一定要segmentList原始文本！！！
        embeddingStore.addAll(embeddingList,segmentList) ;

        ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(5)
                .minScore(0.7)
                .build();
        Query query = Query.from(question) ;
        List<Content>  contentList = contentRetriever.retrieve(query) ;

        //问题相关的
        for (Content content:contentList){
            System.out.println(content);
        }

        //3、将问题和相关知识打包给大模型
        ContentInjector contentInjector = new DefaultContentInjector() ;
        UserMessage promptMessage = contentInjector.inject(contentList, UserMessage.from(question)) ;

        System.out.println(promptMessage.singleText());


        System.out.println("===================>");
        Response<AiMessage> response = model.generate(promptMessage);
        System.out.println(response.content().text());



    }
}

    