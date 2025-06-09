package com.league.league_infos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Configuration
public class RestTemplateConfig {
    @Value("${app.apiRiotGame.apiKey}")
    private String apiRiotKey;

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);

        RestTemplate restTemplate = new RestTemplate(factory);

        restTemplate.setInterceptors(List.of((request, body, execution) -> {
            request.getHeaders().add("X-Riot-Token", apiRiotKey);
            return execution.execute(request, body);
        }));

        return restTemplate;
    }
}
