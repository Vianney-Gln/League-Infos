package com.league.league_infos.services.api;

import com.league.league_infos.models.constants.ApiRiotUrls;
import com.league.league_infos.models.dto.FreeChampionsDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChampionsServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ChampionsService championService;

    @Test
    @DisplayName("Doit contacter l'api riot game et retourner un FreeChampionsDTO ")
    void getFreeChampionsInfos_succes() {
        // GIVEN
        FreeChampionsDTO mockResponse = new FreeChampionsDTO.Builder()
                .freeChampionIds(List.of(1, 2, 3))
                .freeChampionIdsForNewPlayers(List.of(4, 5, 6))
                .maxNewPlayerLevel(10)
                .build();

        ResponseEntity<FreeChampionsDTO> responseEntity = ResponseEntity.ok(mockResponse);
        when(restTemplate.exchange(ApiRiotUrls.CHAMPION_ROTATIONS_API_URL, HttpMethod.GET, null, FreeChampionsDTO.class)).thenReturn(responseEntity);

        // WHEN
        ResponseEntity<FreeChampionsDTO> result = championService.getFreeChampionsInfos();

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getMaxNewPlayerLevel()).isEqualTo(10);
        assertThat(result.getBody().getFreeChampionIds()).contains(1, 2, 3);
        assertThat(result.getBody().getFreeChampionIdsForNewPlayers()).contains(4, 5, 6);
    }
}
