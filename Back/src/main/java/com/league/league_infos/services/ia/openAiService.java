package com.league.league_infos.services.ia;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.league_infos.dto.ia.GenerationCommentaryDTO;
import com.league.league_infos.dto.ia.OpenAiResponseDTO;
import com.league.league_infos.services.api.IAService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.league.league_infos.common.constants.OpenAIUrl.OPEN_AI_URL;

@Service
public class openAiService implements IAService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.openAi.maxToken}")
    private Integer maxToken;

    @Value("${app.openAi.model}")
    private String model;

    @Value("${app.openAi.temperature}")
    private Double temperature;

    @Value("${app.openAi.prompt-commentary}")
    private String promptCommentary;

    public openAiService(@Qualifier("openAiRestTemplate") RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public OpenAiResponseDTO generateCommentaries(GenerationCommentaryDTO generationCommentaryDTO) throws JsonProcessingException {
        String jsonData = objectMapper.writeValueAsString(generationCommentaryDTO);
        String content = promptCommentary + "\n\n" + jsonData;

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", content);

        Map<String, Object> body = new HashMap<>();
        body.put("model", model);
        body.put("temperature", temperature);
        body.put("max_tokens", maxToken);
        body.put("messages", List.of(message));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body);

        ResponseEntity<OpenAiResponseDTO> openAiResponse = restTemplate.exchange(
                OPEN_AI_URL,
                HttpMethod.POST,
                entity,
                OpenAiResponseDTO.class
        );

        return openAiResponse.getBody();
    }
}
