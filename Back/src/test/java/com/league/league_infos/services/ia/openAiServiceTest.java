package com.league.league_infos.services.ia;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.league_infos.dto.ia.ChoiceDTO;
import com.league.league_infos.dto.ia.GenerationCommentaryDTO;
import com.league.league_infos.dto.ia.MessageDTO;
import com.league.league_infos.dto.ia.OpenAiResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenAiServiceTest {

    @InjectMocks
    private openAiService openAiService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private RestTemplate restTemplate;

    @Captor
    private ArgumentCaptor<HttpEntity<Map<String, Object>>> httpEntityArgumentCaptor;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(openAiService, "promptCommentary", "Prompt de test");
        ReflectionTestUtils.setField(openAiService, "model", "gpt-4");
        ReflectionTestUtils.setField(openAiService, "maxToken", 2000);
        ReflectionTestUtils.setField(openAiService, "temperature", 0.5d);
    }

    @Test
    @DisplayName("should call openAi api with good parameters")
    void generateCommentaries_succes() throws JsonProcessingException {
        // GIVEN
        OpenAiResponseDTO openAiResponseDTO = new OpenAiResponseDTO.Builder()
                .id("chatcmpl-D4OSM6AZyMkE6cvFVjopelXU6dRt0")
                .object("chat.completion")
                .created(123456L)
                .model("gpt-4.1-mini-2025-04-14")
                .serviceTier("default")
                .fingerPrint("fp_38699cb0fe")
                .choices(List.of(new ChoiceDTO.Builder()
                        .index(0)
                        .message(new MessageDTO.Builder()
                                .content("Pun1sher Reborn, incarnant Cassiopeia, a livré une performance décisive au cœur de la midlane...")
                                .role("assistant")
                                .build())
                        .build()
                ))
                .build();


        ResponseEntity<OpenAiResponseDTO> responseEntity = ResponseEntity.ok(openAiResponseDTO);
        GenerationCommentaryDTO generationCommentaryDTO = new GenerationCommentaryDTO.Builder().build();

        when(objectMapper.writeValueAsString(any())).thenReturn("json");
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(), eq(OpenAiResponseDTO.class))).thenReturn(responseEntity);

        // WHEN
        OpenAiResponseDTO result = openAiService.generateCommentaries(generationCommentaryDTO);

        // THEN
        assertThat(result).isNotNull().extracting("id",
                        "object",
                        "model",
                        "serviceTier",
                        "fingerPrint")
                .containsExactly("chatcmpl-D4OSM6AZyMkE6cvFVjopelXU6dRt0",
                        "chat.completion",
                        "gpt-4.1-mini-2025-04-14",
                        "default",
                        "fp_38699cb0fe");

        assertThat(result.getChoices())
                .isNotEmpty()
                .extracting(
                        "index",
                        "message.role",
                        "message.content"
                )
                .containsExactly(
                        tuple(
                                0,
                                "assistant",
                                "Pun1sher Reborn, incarnant Cassiopeia, a livré une performance décisive au cœur de la midlane...")
                );

        verify(objectMapper, times(1)).writeValueAsString(generationCommentaryDTO);
        verify(restTemplate, times(1)).exchange(
                eq("https://api.openai.com/v1/chat/completions"),
                eq(HttpMethod.POST),
                httpEntityArgumentCaptor.capture(),
                eq(OpenAiResponseDTO.class)
        );

        HttpEntity<Map<String, Object>> httpEntityCaptor = httpEntityArgumentCaptor.getValue();
        assertThat(httpEntityCaptor).isNotNull();
        assertThat(httpEntityArgumentCaptor.getValue().getBody()).isNotNull()
                .extractingByKeys("model", "temperature", "max_tokens", "messages")
                .containsExactly("gpt-4", 0.5d, 2000, List.of(Map.of("role", "user", "content", "Prompt de test\n\njson")));
    }
}
