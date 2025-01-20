package com.ws.langchain4j.utils;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.segment.TextSegment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yunhua
 * @date 2025-01-19
 * @see
 * @since 1.0.0
 */
public class MyDocumentSplitter implements DocumentSplitter {

    public static final String SPLIT_EXP = "\\s*\\R\\s*\\R\\s*" ;

    @Override
    public List<TextSegment> split(Document document) {
        List<TextSegment> segmentList = new ArrayList<>() ;
        String[] paths = document.text().split(SPLIT_EXP) ;
        for (String path : paths){
            segmentList.add(TextSegment.from(path)) ;
        }
        return segmentList;
    }
}

    