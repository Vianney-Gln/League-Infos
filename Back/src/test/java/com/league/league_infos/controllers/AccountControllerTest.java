package com.league.league_infos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.league_infos.models.dto.AccountDTO;
import com.league.league_infos.services.api.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("should return dto and status Ok")
    void getAccountByPuuid_succes() throws Exception {
        // GIVEN
        AccountDTO accountDTO = new AccountDTO.Builder()
                .gameName("Test name")
                .puuid("2445545445")
                .tagLine("tag line")
                .build();

        when(accountService.getAccountByPuuid(anyString())).thenReturn(ResponseEntity.ok(accountDTO));

        // WHEN + THEN
        mockMvc.perform(get("/account/123987"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(accountDTO)));

        verify(accountService, times(1)).getAccountByPuuid("123987");
    }

    @Test
    @DisplayName("should return dto and status Ok")
    void getAccountByGameName_succes() throws Exception {
        // GIVEN
        AccountDTO accountDTO = new AccountDTO.Builder()
                .gameName("Test")
                .puuid("2445545445")
                .tagLine("EUW")
                .build();

        when(accountService.getAccountByRiotId(anyString(), anyString())).thenReturn(ResponseEntity.ok(accountDTO));

        // WHEN + THEN
        mockMvc.perform(get("/account/by-riot-id/Test/EUW"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(accountDTO)));

        verify(accountService, times(1)).getAccountByRiotId("Test", "EUW");
    }
}
