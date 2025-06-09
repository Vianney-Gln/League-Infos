package com.league.league_infos.services;

import com.league.league_infos.models.constants.ApiRiotUrls;
import com.league.league_infos.models.dto.LeagueItemDTO;
import com.league.league_infos.models.dto.LeagueListDTO;
import com.league.league_infos.models.enums.QueueEnum;
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
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayersServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PlayersService playersService;

    @Test
    @DisplayName("Doit contacter l'api riot game et retourner un LeagueListDTO qui représente les meilleurs joueurs challenger en solo Q")
    void getLeagueChallengerDataSoloQ_succes() {
        // GIVEN
        LeagueListDTO mockResponse = new LeagueListDTO.Builder()
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
                ))
                .build();

        ResponseEntity<LeagueListDTO> responseEntity = ResponseEntity.ok(mockResponse);
        when(restTemplate.exchange(ApiRiotUrls.CHALLENGERS_LEAGUE_API_URL + "/" + QueueEnum.RANKED_SOLO_5x5.getLibelle(), HttpMethod.GET, null, LeagueListDTO.class)).thenReturn(responseEntity);

        // WHEN
        ResponseEntity<LeagueListDTO> result = playersService.getLeagueChallengerDataSoloQ();

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull()
                .extracting("leagueId", "name", "tier", "queue")
                .containsExactly("50", "nom de la ligue", "CHALLENGER", "SOLO Q 5V5");
        assertThat(result.getBody().getEntries()).isNotNull().isNotEmpty()
                .extracting("losses", "wins", "leaguePoints", "rank", "puuid", "freshBlood", "hotStreak", "inactive", "summonerId", "veteran")
                .containsExactly(tuple(103, 187, 2200, "I", "12345678910", false, true, false, "12345", true));

        assertThat(result.getBody().getEntries().getFirst().getMiniSeriesDTO()).isNull();
    }

    @Test
    @DisplayName("Doit contacter l'api riot game et retourner un LeagueListDTO qui représente les meilleurs joueurs challenger en flex Q")
    void getLeagueChallengerDataFlexQ_succes() {
        // GIVEN
        LeagueListDTO mockResponse = new LeagueListDTO.Builder()
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
                ))
                .build();

        ResponseEntity<LeagueListDTO> responseEntity = ResponseEntity.ok(mockResponse);
        when(restTemplate.exchange(ApiRiotUrls.CHALLENGERS_LEAGUE_API_URL + "/" + QueueEnum.RANKED_FLEX_SR.getLibelle(), HttpMethod.GET, null, LeagueListDTO.class)).thenReturn(responseEntity);

        // WHEN
        ResponseEntity<LeagueListDTO> result = playersService.getLeagueChallengerDataFlexQ();

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull()
                .extracting("leagueId", "name", "tier", "queue")
                .containsExactly("50", "nom de la ligue", "CHALLENGER", "SOLO Q 5V5");
        assertThat(result.getBody().getEntries()).isNotNull().isNotEmpty()
                .extracting("losses", "wins", "leaguePoints", "rank", "puuid", "freshBlood", "hotStreak", "inactive", "summonerId", "veteran")
                .containsExactly(tuple(103, 187, 2200, "I", "12345678910", false, true, false, "12345", true));

        assertThat(result.getBody().getEntries().getFirst().getMiniSeriesDTO()).isNull();
    }
}
