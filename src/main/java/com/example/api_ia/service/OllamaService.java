package com.example.api_ia.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.api_ia.dto.OllamaRequest;
import com.example.api_ia.dto.OllamaResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;

@Service
public class OllamaService {
    
    private final WebClient client;

    public OllamaService(WebClient ollamaClient) {
        this.client = ollamaClient;
    }

    public Flux<OllamaResponse> gerar(String prompt) {
        return client.post()
                .uri("/api/chat")
                .bodyValue(new OllamaRequest("llama3", prompt))
                .retrieve()
                .bodyToFlux(String.class)
                .map(this::parseJsonLine);

    }

    private OllamaResponse parseJsonLine(String line) {
        try {
            return new ObjectMapper().readValue(line, OllamaResponse.class);
        } catch (JsonProcessingException e) {
            return new OllamaResponse(line, false);
        }
    }

}
