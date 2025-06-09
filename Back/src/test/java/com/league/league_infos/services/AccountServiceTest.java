package com.league.league_infos.services;

import com.league.league_infos.models.constants.ApiRiotUrls;
import com.league.league_infos.models.dto.AccountDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @InjectMocks
    private AccountService accountService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    @DisplayName("Doit contacter l'api riot game et retourner un AccountDTO")
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
}
