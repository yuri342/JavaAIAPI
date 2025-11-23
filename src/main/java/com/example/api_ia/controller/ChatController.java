package com.example.api_ia.controller;



import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_ia.dto.OllamaRequest;
import com.example.api_ia.dto.OllamaResponse;
import com.example.api_ia.service.OllamaService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final OllamaService ollamaService;

    public ChatController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @GetMapping
    public String hello() {
        return "API is running";
    }

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<OllamaResponse> chat(@RequestBody OllamaRequest request){
        return ollamaService.gerar(request.prompt());
    }

    @PostMapping(
        path = "/json",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<OllamaResponse> chatJson(@RequestBody OllamaRequest request) {
        return ollamaService.gerar(request.prompt()).last();
    }
}
