package com.league.league_infos.services.api;

import com.league.league_infos.common.exceptions.BusinessException;
import com.league.league_infos.models.constants.ApiRiotUrls;
import com.league.league_infos.models.dto.ChampionMasteryDTO;
import com.league.league_infos.models.dto.LeagueEntryDTO;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.league.league_infos.common.constants.ErrorMessagesEnum.*;
import static com.league.league_infos.models.enums.QueueEnum.RANKED_FLEX_SR;
import static com.league.league_infos.models.enums.QueueEnum.RANKED_SOLO_5x5;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayersServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PlayersService playersService;

    @Test
    @DisplayName("Should call riot game api and return a LeagueListDTO")
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

        assertThat(result.getBody().getEntries().getFirst().getMiniSeries()).isNull();
    }

    @Test
    @DisplayName("Should call riot game api and return a LeagueListDTO")
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

        assertThat(result.getBody().getEntries().getFirst().getMiniSeries()).isNull();
    }

    @Test
    @DisplayName("Should call riot game api and return a List of LeagueEntryDTO")
    void getLeagueEntriesByPuuid_succes() {
        // GIVEN
        LeagueEntryDTO leagueEntrySoloQ = new LeagueEntryDTO.Builder()
                .leagueId("abcd")
                .summonerId("summ id")
                .puuid("puuidxxxxx")
                .rank("III")
                .tier("Bronze")
                .queueType(RANKED_SOLO_5x5.getLibelle())
                .leaguePoints(97)
                .wins(197)
                .losses(192)
                .veteran(true)
                .inactive(false)
                .freshBlood(false)
                .hotStreak(true)
                .build();

        LeagueEntryDTO leagueEntryFlexQ = new LeagueEntryDTO.Builder()
                .leagueId("ijkl")
                .summonerId("summ id")
                .puuid("puuidxxxxx")
                .rank("II")
                .tier("Silver")
                .queueType(RANKED_FLEX_SR.getLibelle())
                .leaguePoints(56)
                .wins(26)
                .losses(14)
                .veteran(true)
                .inactive(false)
                .freshBlood(false)
                .hotStreak(true)
                .build();

        ResponseEntity<LeagueEntryDTO[]> responseEntity = ResponseEntity.ok(new LeagueEntryDTO[]{leagueEntrySoloQ, leagueEntryFlexQ});
        when(restTemplate.exchange(ApiRiotUrls.PLAYER_LEAGUE_API_URL + "/puuidxxxxx", HttpMethod.GET, null, LeagueEntryDTO[].class)).thenReturn(responseEntity);

        // WHEN
        ResponseEntity<List<LeagueEntryDTO>> result = playersService.getLeagueEntriesByPuuid("puuidxxxxx");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull().isNotEmpty().hasSize(2);

        assertThat(result.getBody()).extracting("leagueId", "summonerId", "puuid", "rank", "tier", "queueType",
                "leaguePoints", "wins", "losses", "veteran", "inactive", "freshBlood", "hotStreak"
        ).contains(
                tuple("abcd", "summ id", "puuidxxxxx", "III", "Bronze", "RANKED_SOLO_5x5", 97, 197, 192, true, false, false, true),
                tuple("ijkl", "summ id", "puuidxxxxx", "II", "Silver", "RANKED_FLEX_SR", 56, 26, 14, true, false, false, true)
        );
    }


    @Test
    @DisplayName("Should call riot game api and return a business exception")
    void getLeagueEntriesByPuuid_echec_1() {
        // GIVEN
        when(restTemplate.exchange(ApiRiotUrls.PLAYER_LEAGUE_API_URL + "/puuidxxxxx", HttpMethod.GET, null, LeagueEntryDTO[].class)).thenThrow(new HttpClientErrorException(HttpStatusCode.valueOf(404)));

        // WHEN + THEN
        assertThatThrownBy(() -> playersService.getLeagueEntriesByPuuid("puuidxxxxx"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ERROR_BUSINESS_1.getLibelle());
    }

    @Test
    @DisplayName("Should call riot game api and return a business exception")
    void getLeagueEntriesByPuuid_echec_2() {
        // GIVEN
        when(restTemplate.exchange(ApiRiotUrls.PLAYER_LEAGUE_API_URL + "/puuidxxxxx", HttpMethod.GET, null, LeagueEntryDTO[].class)).thenThrow(new HttpClientErrorException(HttpStatusCode.valueOf(400)));

        // WHEN + THEN
        assertThatThrownBy(() -> playersService.getLeagueEntriesByPuuid("puuidxxxxx"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ERROR_BUSINESS_2.getLibelle());
    }

    @Test
    @DisplayName("Should call riot game api and return a business exception")
    void getLeagueEntriesByPuuid_echec_3() {
        // GIVEN
        when(restTemplate.exchange(ApiRiotUrls.PLAYER_LEAGUE_API_URL + "/puuidxxxxx", HttpMethod.GET, null, LeagueEntryDTO[].class)).thenThrow(new ResourceAccessException(ERROR_BUSINESS_3.getLibelle()));

        // WHEN + THEN
        assertThatThrownBy(() -> playersService.getLeagueEntriesByPuuid("puuidxxxxx"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ERROR_BUSINESS_3.getLibelle());
    }

    @Test
    @DisplayName("Should call riot game api and return a List of ChampionMasteryDTO")
    void getChampionMasteriesByPuuid_succes() {
        // GIVEN
        ChampionMasteryDTO championMasteryDTO = new ChampionMasteryDTO.Builder()
                .puuid("puuid")
                .championId(50521L)
                .lastPlayTime(123456L)
                .championPointsSinceLastLevel(10000L)
                .championPointsUntilNextLevel(10000L)
                .championLevel(368)
                .championPoints(2500000)
                .markRequiredForNextLevel(12)
                .championSeasonMilestone(16)
                .chestGranted(true)
                .build();

        ResponseEntity<ChampionMasteryDTO[]> responseEntity = ResponseEntity.ok(new ChampionMasteryDTO[]{championMasteryDTO});
        when(restTemplate.exchange(ApiRiotUrls.CHAMPION_MASTERY_API_URL + "/puuid/top", HttpMethod.GET, null, ChampionMasteryDTO[].class)).thenReturn(responseEntity);

        // WHEN
        ResponseEntity<List<ChampionMasteryDTO>> result = playersService.getChampionMasteriesByPuuid("puuid");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull().isNotEmpty().hasSize(1);
        assertThat(result.getBody()).extracting("puuid",
                "championId",
                "lastPlayTime",
                "championPointsSinceLastLevel",
                "championPointsUntilNextLevel",
                "championLevel",
                "championPoints",
                "markRequiredForNextLevel",
                "championSeasonMilestone",
                "chestGranted"
        ).containsExactly(tuple("puuid", 50521L, 123456L, 10000L, 10000L, 368, 2500000, 12, 16, true));
    }

    @Test
    @DisplayName("Should call riot game api and return a business exception")
    void getChampionMasteriesByPuuid_echec_1() {
        // GIVEN
        when(restTemplate.exchange(ApiRiotUrls.CHAMPION_MASTERY_API_URL + "/puuidxxxxx/top", HttpMethod.GET, null, ChampionMasteryDTO[].class)).thenThrow(new HttpClientErrorException(HttpStatusCode.valueOf(404)));

        // WHEN + THEN
        assertThatThrownBy(() -> playersService.getChampionMasteriesByPuuid("puuidxxxxx"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ERROR_BUSINESS_1.getLibelle());
    }

    @Test
    @DisplayName("Should call riot game api and return a business exception")
    void getChampionMasteriesByPuuid_echec_2() {
        // GIVEN
        when(restTemplate.exchange(ApiRiotUrls.CHAMPION_MASTERY_API_URL + "/puuidxxxxx/top", HttpMethod.GET, null, ChampionMasteryDTO[].class)).thenThrow(new HttpClientErrorException(HttpStatusCode.valueOf(400)));

        // WHEN + THEN
        assertThatThrownBy(() -> playersService.getChampionMasteriesByPuuid("puuidxxxxx"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ERROR_BUSINESS_2.getLibelle());
    }

    @Test
    @DisplayName("Should call riot game api and return a business exception")
    void getChampionMasteriesByPuuid_echec_3() {
        // GIVEN
        when(restTemplate.exchange(ApiRiotUrls.CHAMPION_MASTERY_API_URL + "/puuidxxxxx/top", HttpMethod.GET, null, ChampionMasteryDTO[].class)).thenThrow(new ResourceAccessException(ERROR_BUSINESS_3.getLibelle()));

        // WHEN + THEN
        assertThatThrownBy(() -> playersService.getChampionMasteriesByPuuid("puuidxxxxx"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ERROR_BUSINESS_3.getLibelle());
    }

}
