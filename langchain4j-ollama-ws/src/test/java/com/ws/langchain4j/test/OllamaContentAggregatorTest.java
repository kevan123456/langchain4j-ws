package com.ws.langchain4j.test;

import com.ws.langchain4j.base.BaseTest;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.aggregator.ContentAggregator;
import dev.langchain4j.rag.content.aggregator.DefaultContentAggregator;
import dev.langchain4j.rag.query.Query;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author yunhua
 * @date 2025-01-24
 * @see
 * @since 1.0.0
 */
public class OllamaContentAggregatorTest extends BaseTest {


    @Test
    public void test() {
        Query query1 = Query.from("1");
        Query query2 = Query.from("2");
        Query query3 = Query.from("3");
        Map<Query, Collection<List<Content>>> queryToContents = Map.of(query1, List.of(List.of(Content.from("1"),Content.from("2"),Content.from("3"))),
                query2,List.of(List.of(Content.from("3"),Content.from("4"),Content.from("5"))),
                query3,List.of(List.of(Content.from("3"),Content.from("5"),Content.from("7")))) ;
        ContentAggregator contentAggregator = new DefaultContentAggregator() ;
        List<Content> aggregate = contentAggregator.aggregate(queryToContents);
        for (Content content : aggregate){
            System.out.println(content.toString());
        }

    }
}

    