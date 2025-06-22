package com.league.league_infos.services;

import com.league.league_infos.models.constants.ApiRiotUrls;
import com.league.league_infos.models.dto.SummonerDTO;
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
class SummonersServiceTest {

    @InjectMocks
    private SummonersService summonersService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    @DisplayName("Doit contacter l'api riot game et retourner un SummonerDTO")
    void getSummonerByPuuid_succes() {
        // GIVEN
        SummonerDTO mockResponse = new SummonerDTO.Builder()
                .summonerLevel(338L)
                .profileIconId(244)
                .accountId("336658")
                .revisionDate(145678L)
                .id("10005")
                .puuid("abcdefgh")
                .build();
        ResponseEntity<SummonerDTO> responseEntity = ResponseEntity.ok(mockResponse);
        when(restTemplate.exchange(ApiRiotUrls.SUMMONER_BY_PUUID_API_URL + "/1234", HttpMethod.GET, null, SummonerDTO.class)).thenReturn(responseEntity);

        // WHEN
        ResponseEntity<SummonerDTO> result = summonersService.getSummonerByPuuid("1234");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull().extracting("summonerLevel", "profileIconId", "accountId", "revisionDate", "id", "puuid")
                .containsExactly(338L, 244, "336658", 145678L, "10005", "abcdefgh");

        verify(restTemplate, times(1)).exchange(ApiRiotUrls.SUMMONER_BY_PUUID_API_URL + "/1234", HttpMethod.GET, null, SummonerDTO.class);
    }
}
