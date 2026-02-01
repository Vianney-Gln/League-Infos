package com.league.league_infos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class OpenAiRestTemplateConfig {
    @Value("${app.apiOpenAi.apiKey}")
    private String openAiApiKey;

    @Bean
    public RestTemplate openAiRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(15000);
        factory.setReadTimeout(15000);

        RestTemplate restTemplate = new RestTemplate(factory);

        restTemplate.setInterceptors(List.of((request, body, execution) -> {
            request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + openAiApiKey);
            return execution.execute(request, body);
        }));

        return restTemplate;
    }
}
