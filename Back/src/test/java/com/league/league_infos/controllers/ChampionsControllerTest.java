package com.league.league_infos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.league_infos.models.dto.FreeChampionsDTO;
import com.league.league_infos.services.ChampionsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChampionsController.class)
class ChampionsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ChampionsService championsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("should return dto and status Ok")
    void getFreeChampions_succes() throws Exception {
        // GIVEN
        FreeChampionsDTO dto = new FreeChampionsDTO();
        dto.setMaxNewPlayerLevel(10);
        dto.setFreeChampionIdsForNewPlayers(List.of(1, 2, 3));
        dto.setFreeChampionIds(List.of(101, 102));

        when(championsService.getFreeChampionsInfos()).thenReturn(ResponseEntity.ok(dto));

        // WHEN + THEN
        mockMvc.perform(get("/champions/free"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));

        verify(championsService, times(1)).getFreeChampionsInfos();
    }
}
