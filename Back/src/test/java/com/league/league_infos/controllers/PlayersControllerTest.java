package com.league.league_infos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.league_infos.models.dto.LeagueItemDTO;
import com.league.league_infos.models.dto.LeagueListDTO;
import com.league.league_infos.services.PlayersService;
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

@WebMvcTest(PlayersController.class)
public class PlayersControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlayersService playersService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("should return dto and status Ok")
    void getLeagueChallengerDataSoloQ_succes() throws Exception {
        // GIVEN
        LeagueListDTO leagueListDTO = new LeagueListDTO.Builder()
                .leagueId("50")
                .name("nom de la ligue")
                .tier("CHALLENGER")
                .queue("SOLO Q 5V5")
                .entries(List.of(new LeagueItemDTO.Builder()
                        .losses(103)
                        .wins(187)
                        .leaguePoints(2200)
                        .rank("I")
                        .puuid("12345678910")
                        .freshBlood(false)
                        .hotStreak(true)
                        .inactive(false)
                        .summonerId("12345")
                        .veteran(true)
                        .miniSeriesDTO(null)
                        .build()
                )).build();

        when(playersService.getLeagueChallengerDataSoloQ()).thenReturn(ResponseEntity.ok(leagueListDTO));

        // WHEN + THEN
        mockMvc.perform(get("/league-challengers-solo-queue"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(leagueListDTO)));

        verify(playersService, times(1)).getLeagueChallengerDataSoloQ();
    }

    @Test
    @DisplayName("should return dto and status Ok ..")
    void getLeagueChallengerDataFlexQ_succes() throws Exception {
        // GIVEN
        LeagueListDTO leagueListDTO = new LeagueListDTO.Builder()
                .leagueId("50")
                .name("nom de la ligue")
                .tier("CHALLENGER")
                .queue("SOLO Q 5V5")
                .entries(List.of(new LeagueItemDTO.Builder()
                        .losses(103)
                        .wins(187)
                        .leaguePoints(2200)
                        .rank("I")
                        .puuid("12345678910")
                        .freshBlood(false)
                        .hotStreak(true)
                        .inactive(false)
                        .summonerId("12345")
                        .veteran(true)
                        .miniSeriesDTO(null)
                        .build()
                )).build();

        when(playersService.getLeagueChallengerDataFlexQ()).thenReturn(ResponseEntity.ok(leagueListDTO));

        // WHEN + THEN
        mockMvc.perform(get("/league-challengers-flex-queue"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(leagueListDTO)));

        verify(playersService, times(1)).getLeagueChallengerDataFlexQ();
    }

}
