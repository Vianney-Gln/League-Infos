package com.league.league_infos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.league_infos.dto.SummonerDTO;
import com.league.league_infos.services.riot.RiotSummonersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SummonersController.class)
class SummonersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RiotSummonersService riotSummonersService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("should return dto and status Ok")
    void getSummonerByPuuid_succes() throws Exception {
        // GIVEN
        SummonerDTO summonerDTO = new SummonerDTO.Builder()
                .summonerLevel(338L)
                .profileIconId(244)
                .accountId("336658")
                .revisionDate(145678L)
                .id("10005")
                .puuid("abcdefgh")
                .build();

        when(riotSummonersService.getSummonerByPuuid(anyString())).thenReturn(summonerDTO);

        // WHEN + THEN
        mockMvc.perform(get("/summoner/122355"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(summonerDTO)));

        verify(riotSummonersService, times(1)).getSummonerByPuuid("122355");
    }
}
