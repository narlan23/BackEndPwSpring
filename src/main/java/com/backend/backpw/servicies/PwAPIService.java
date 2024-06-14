package com.backend.backpw.servicies;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PwAPIService {

    private final WebClient webClient;

    @Autowired
    public PwAPIService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<String> consultarDados(String userId, String function) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/index.php")
                        .queryParam("userid", userId)
                        .queryParam("function", function)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
