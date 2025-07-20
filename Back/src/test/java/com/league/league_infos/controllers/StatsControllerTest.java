package com.league.league_infos.controllers;

import com.league.league_infos.services.business.StatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatsController.class)
class StatsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StatsService statsService;

    @Test
    @DisplayName("should return id from the bost banned champion")
    void getMostBannedChampions_success() throws Exception {
        // GIVEN
        when(statsService.calculateMostBannedChampion()).thenReturn(120L);

        // WHEN + THEN
        mockMvc.perform(get("/stats/most-banned-champion"))
                .andExpect(status().isOk())
                .andExpect(content().json("120"));

        verify(statsService, times(1)).calculateMostBannedChampion();
    }
}
