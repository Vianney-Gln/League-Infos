package com.league.league_infos.services.riot;

import com.league.league_infos.common.constants.ApiRiotUrls;
import com.league.league_infos.common.exceptions.BusinessException;
import com.league.league_infos.dto.FreeChampionsDTO;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RiotChampionsServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RiotChampionsService championService;

    @Test
    @DisplayName("Doit contacter l'api riot game et retourner un FreeChampionsDTO ")
    void getFreeChampionsInfos_succes() {
        // GIVEN
        FreeChampionsDTO mockResponse = new FreeChampionsDTO.Builder()
                .freeChampionIds(List.of(1, 2, 3))
                .freeChampionIdsForNewPlayers(List.of(4, 5, 6))
                .build();

        ResponseEntity<FreeChampionsDTO> responseEntity = ResponseEntity.ok(mockResponse);
        when(restTemplate.exchange(ApiRiotUrls.CHAMPION_ROTATIONS_API_URL, HttpMethod.GET, null, FreeChampionsDTO.class)).thenReturn(responseEntity);

        // WHEN
        FreeChampionsDTO result = championService.getFreeChampionsInfos();

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getFreeChampionIds()).contains(1, 2, 3);
        assertThat(result.getFreeChampionIdsForNewPlayers()).contains(4, 5, 6);
    }

    @Test
    @DisplayName("Doit contacter l'api riot game et retourner une exception si un des resultat est null ")
    void getFreeChampionsInfos_fail() {
        // GIVEN
        FreeChampionsDTO mockResponse = new FreeChampionsDTO.Builder()
                .freeChampionIds(null)
                .freeChampionIdsForNewPlayers(null)
                .build();

        ResponseEntity<FreeChampionsDTO> responseEntity = ResponseEntity.ok(mockResponse);
        when(restTemplate.exchange(ApiRiotUrls.CHAMPION_ROTATIONS_API_URL, HttpMethod.GET, null, FreeChampionsDTO.class)).thenReturn(responseEntity);

        // WHEN + THEN
        assertThatThrownBy(() -> championService.getFreeChampionsInfos())
                .isInstanceOf(BusinessException.class)
                .hasMessage("Fonctionalité temporairement indisponible, veuillez réesayer utltérieurement");
    }
}
