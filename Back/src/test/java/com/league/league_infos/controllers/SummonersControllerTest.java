package com.league.league_infos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.league_infos.models.dto.SummonerDTO;
import com.league.league_infos.services.SummonersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SummonersController.class)
class SummonersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SummonersService summonersService;

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

        when(summonersService.getSummonerByPuuid(anyString())).thenReturn(ResponseEntity.ok(summonerDTO));

        // WHEN + THEN
        mockMvc.perform(get("/summoner/122355"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(summonerDTO)));

        verify(summonersService, times(1)).getSummonerByPuuid("122355");
    }
}
