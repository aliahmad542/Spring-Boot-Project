package com.example.first.AI.Service;

import com.example.first.AI.dto.GeminiRequestDTO;
import com.example.first.AI.util.GeminiPromptBuilder;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.url}")
    private String url;

    @Value("${gemini.service-account-file}")
    private String serviceAccountFile; 

    private final WebClient webClient = WebClient.builder().build();

    public String suggest(GeminiRequestDTO request) throws Exception {
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new FileInputStream(serviceAccountFile))
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
        credentials.refreshIfExpired(); 

        String prompt = GeminiPromptBuilder.build(request);

        Map<String, Object> body = Map.of(
                "model", "gemini-pro",
                "prompt", Map.of(
                        "messages", List.of(
                                Map.of(
                                        "role", "user",
                                        "content", List.of(
                                                Map.of("type", "text", "text", prompt)
                                        )
                                )
                        )
                )
        );

        return webClient.post()
                .uri(url + "/models/gemini-pro:generateContent")
                .headers(h -> h.setBearerAuth(credentials.getAccessToken().getTokenValue()))
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
