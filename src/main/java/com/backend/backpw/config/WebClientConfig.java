package com.backend.backpw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfig {
	
	private static final String TOKEN = "apidemo";
    private static final String BASE_URL = "http://192.168.18.215/api/index.php";

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .baseUrl(BASE_URL);

    }
	 
	 

}
