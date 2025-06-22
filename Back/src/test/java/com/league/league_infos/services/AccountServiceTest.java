package com.league.league_infos.services;

import com.league.league_infos.common.exceptions.BusinessException;
import com.league.league_infos.models.constants.ApiRiotUrls;
import com.league.league_infos.models.dto.AccountDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static com.league.league_infos.common.constants.ErrorMessagesEnum.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @InjectMocks
    private AccountService accountService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    @DisplayName("Should call api riot game et return AccountDTO")
    void getAccountByPuuid_succes() {
        // GIVEN
        AccountDTO mockResponse = new AccountDTO.Builder()
                .gameName("Test name")
                .puuid("2445545445")
                .tagLine("tag line")
                .build();
        ResponseEntity<AccountDTO> responseEntity = ResponseEntity.ok(mockResponse);
        when(restTemplate.exchange(ApiRiotUrls.ACCOUNT_BY_PUUID_API_URL + "/1234", HttpMethod.GET, null, AccountDTO.class)).thenReturn(responseEntity);

        // WHEN
        ResponseEntity<AccountDTO> result = accountService.getAccountByPuuid("1234");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull().extracting("gameName", "tagLine", "puuid")
                .containsExactly("Test name", "tag line", "2445545445");

        verify(restTemplate, times(1)).exchange(ApiRiotUrls.ACCOUNT_BY_PUUID_API_URL + "/1234", HttpMethod.GET, null, AccountDTO.class);
    }

    @Test
    @DisplayName("Should call api riot game et return AccountDTO")
    void getAccountByGameName_succes() {
        // GIVEN
        AccountDTO mockResponse = new AccountDTO.Builder()
                .gameName("Test")
                .puuid("2445545445")
                .tagLine("euw")
                .build();
        ResponseEntity<AccountDTO> responseEntity = ResponseEntity.ok(mockResponse);
        when(restTemplate.exchange(ApiRiotUrls.ACCOUNT_BY_RIOT_ID_API_URL + "/Test/euw", HttpMethod.GET, null, AccountDTO.class)).thenReturn(responseEntity);

        // WHEN
        ResponseEntity<AccountDTO> result = accountService.getAccountByRiotId("Test", "euw");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull().extracting("gameName", "tagLine", "puuid")
                .containsExactly("Test", "euw", "2445545445");

        verify(restTemplate, times(1)).exchange(ApiRiotUrls.ACCOUNT_BY_RIOT_ID_API_URL + "/Test/euw", HttpMethod.GET, null, AccountDTO.class);
    }

    @Test
    @DisplayName("Should throw a 404 not found exception")
    void getAccountByGameName_echec_1() {
        // GIVEN
        when(restTemplate.exchange(ApiRiotUrls.ACCOUNT_BY_RIOT_ID_API_URL + "/Test/euw", HttpMethod.GET, null, AccountDTO.class)).thenThrow(new HttpClientErrorException(HttpStatusCode.valueOf(404)));

        // WHEN + THEN
        assertThatThrownBy(() -> accountService.getAccountByRiotId("Test", "euw"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ERROR_BUSINESS_1.getLibelle());
    }

    @Test
    @DisplayName("Should throw a 400 bad request exception")
    void getAccountByGameName_echec_2() {
        // GIVEN
        when(restTemplate.exchange(ApiRiotUrls.ACCOUNT_BY_RIOT_ID_API_URL + "/Test/euw", HttpMethod.GET, null, AccountDTO.class)).thenThrow(new HttpClientErrorException(HttpStatusCode.valueOf(400)));

        // WHEN + THEN
        assertThatThrownBy(() -> accountService.getAccountByRiotId("Test", "euw"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ERROR_BUSINESS_2.getLibelle());
    }

    @Test
    @DisplayName("Should throw a 503 unavailable service exception")
    void getAccountByGameName_echec_3() {
        // GIVEN
        when(restTemplate.exchange(ApiRiotUrls.ACCOUNT_BY_RIOT_ID_API_URL + "/Test/euw", HttpMethod.GET, null, AccountDTO.class)).thenThrow(new ResourceAccessException(ERROR_BUSINESS_3.getLibelle()));

        // WHEN + THEN
        assertThatThrownBy(() -> accountService.getAccountByRiotId("Test", "euw"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ERROR_BUSINESS_3.getLibelle());
    }
}
