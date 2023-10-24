package com.example.openaitest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OpenAiTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenAiTestApplication.class, args);
    }

    // Retrieve the OpenAI API key from application properties
    @Value("${openai.api.key}")
    String openaiApiKey;

    // Configure a RestTemplate bean with an interceptor to add the API key to requests
    @Bean
    public RestTemplate template(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + openaiApiKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}
