package com.league.league_infos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.league_infos.models.dto.ChampionMasteryDTO;
import com.league.league_infos.models.dto.LeagueEntryDTO;
import com.league.league_infos.models.dto.LeagueItemDTO;
import com.league.league_infos.models.dto.LeagueListDTO;
import com.league.league_infos.services.api.PlayersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.league.league_infos.models.enums.QueueEnum.RANKED_FLEX_SR;
import static com.league.league_infos.models.enums.QueueEnum.RANKED_SOLO_5x5;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayersController.class)
class PlayersControllerTest {
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

    @Test
    @DisplayName("should return dto and status Ok ..")
    void getLeagueEntriesByPuuid_succes() throws Exception {
        // GIVEN
        LeagueEntryDTO leagueEntrySoloQ = new LeagueEntryDTO.Builder()
                .leagueId("abcd")
                .summonerId("summ id")
                .puuid("puuidxxxxx")
                .rank("III")
                .tier("Bronze")
                .queueType(RANKED_SOLO_5x5.getLibelle())
                .leaguePoints(97)
                .wins(197)
                .losses(192)
                .veteran(true)
                .inactive(false)
                .freshBlood(false)
                .hotStreak(true)
                .build();

        LeagueEntryDTO leagueEntryFlexQ = new LeagueEntryDTO.Builder()
                .leagueId("ijkl")
                .summonerId("summ id")
                .puuid("puuidxxxxx")
                .rank("II")
                .tier("Silver")
                .queueType(RANKED_FLEX_SR.getLibelle())
                .leaguePoints(56)
                .wins(26)
                .losses(14)
                .veteran(true)
                .inactive(false)
                .freshBlood(false)
                .hotStreak(true)
                .build();

        List<LeagueEntryDTO> leagueEntries = List.of(leagueEntrySoloQ, leagueEntryFlexQ);
        when(playersService.getLeagueEntriesByPuuid(anyString())).thenReturn(ResponseEntity.ok(leagueEntries));

        // WHEN + THEN
        mockMvc.perform(get("/league-entries-by-puuid/puuidxxxxx"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(leagueEntries)));

        verify(playersService, times(1)).getLeagueEntriesByPuuid("puuidxxxxx");
    }

    @Test
    @DisplayName("should return dto and status Ok ..")
    void getChampionMasteriesByPuuid_succes() throws Exception {
        // GIVEN
        ChampionMasteryDTO championMasteryDTO = new ChampionMasteryDTO.Builder()
                .puuid("puuid")
                .championId(50521L)
                .lastPlayTime(123456L)
                .championPointsSinceLastLevel(10000L)
                .championPointsUntilNextLevel(10000L)
                .championLevel(368)
                .championPoints(2500000)
                .markRequiredForNextLevel(12)
                .championSeasonMilestone(16)
                .chestGranted(true)
                .build();

        List<ChampionMasteryDTO> championsMasteryDTO = List.of(championMasteryDTO);
        when(playersService.getChampionMasteriesByPuuid(anyString())).thenReturn(ResponseEntity.ok(championsMasteryDTO));

        // WHEN + THEN
        mockMvc.perform(get("/champion-masteries/puuid/top"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(championsMasteryDTO)));

        verify(playersService, times(1)).getChampionMasteriesByPuuid("puuid");
    }
}
