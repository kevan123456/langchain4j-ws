package com.ws.langchain4j.controller;

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
@RestController
@RequestMapping("ollama")
public class OllamaController {

    @GetMapping("/test")
    public String chat(@RequestParam(value = "message",defaultValue = "给我讲个笑话") String message) {

        return "ollama hello word" ;
    }
}

    