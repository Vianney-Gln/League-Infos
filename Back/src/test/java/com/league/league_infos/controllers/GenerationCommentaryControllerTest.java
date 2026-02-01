package com.league.league_infos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.league_infos.dto.ia.ChoiceDTO;
import com.league.league_infos.dto.ia.MessageDTO;
import com.league.league_infos.dto.ia.OpenAiResponseDTO;
import com.league.league_infos.services.handler.CommentaryHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenerationCommentaryController.class)
class GenerationCommentaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CommentaryHandler commentaryHandler;

    @Test
    @DisplayName("should return dto and status Ok")
    void getCommentary_success() throws Exception {
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

        when(commentaryHandler.getCommentaryFromAGameByPlayer(anyLong(), anyString(), anyString(), anyString())).thenReturn(openAiResponseDTO);

        // WHEN + THEN
        mockMvc.perform(get("/matchCommentary/EUW1_7706979430")
                        .param("gameId", "7706979430")
                        .param("puuid", "puuid")
                        .param("pseudo", "pseudo")
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(openAiResponseDTO)));

        verify(commentaryHandler, times(1)).getCommentaryFromAGameByPlayer(7706979430L, "puuid", "EUW1_7706979430", "pseudo");
    }

}
