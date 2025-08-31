package com.league.league_infos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.league_infos.dto.FreeChampionsDTO;
import com.league.league_infos.services.riot.RiotChampionsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChampionsController.class)
class ChampionsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RiotChampionsService riotChampionsService;

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

        when(riotChampionsService.getFreeChampionsInfos()).thenReturn(dto);

        // WHEN + THEN
        mockMvc.perform(get("/champions/free"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));

        verify(riotChampionsService, times(1)).getFreeChampionsInfos();
    }
}
