package com.league.league_infos.services.api;

import com.league.league_infos.common.exceptions.BusinessException;
import com.league.league_infos.models.constants.ApiRiotUrls;
import com.league.league_infos.models.dto.BannedChampionDTO;
import com.league.league_infos.models.dto.FeaturedGameInfoDTO;
import com.league.league_infos.models.dto.FeaturedGamesDTO;
import com.league.league_infos.models.dto.ParticipantDTO;
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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FeaturedGameServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FeaturedGameService featuredGameService;

    @Test
    @DisplayName("should get featured games from Riot game api")
    void getFeaturedGames_success() {
        // GIVEN
        BannedChampionDTO bannedChampion1 = new BannedChampionDTO.Builder().championId(10).pickTurn(1).teamId(10).build();
        BannedChampionDTO bannedChampion2 = new BannedChampionDTO.Builder().championId(20).pickTurn(2).teamId(20).build();
        BannedChampionDTO bannedChampion3 = new BannedChampionDTO.Builder().championId(20).pickTurn(3).teamId(20).build();
        BannedChampionDTO bannedChampion4 = new BannedChampionDTO.Builder().championId(-1).pickTurn(4).teamId(10).build();
        BannedChampionDTO bannedChampion5 = new BannedChampionDTO.Builder().championId(-1).pickTurn(5).teamId(10).build();
        BannedChampionDTO bannedChampion6 = new BannedChampionDTO.Builder().championId(144).pickTurn(6).teamId(20).build();
        BannedChampionDTO bannedChampion7 = new BannedChampionDTO.Builder().championId(154).pickTurn(7).teamId(20).build();
        BannedChampionDTO bannedChampion8 = new BannedChampionDTO.Builder().championId(100).pickTurn(8).teamId(10).build();
        BannedChampionDTO bannedChampion9 = new BannedChampionDTO.Builder().championId(144).pickTurn(9).teamId(10).build();
        BannedChampionDTO bannedChampion10 = new BannedChampionDTO.Builder().championId(144).pickTurn(10).teamId(20).build();

        ParticipantDTO participant1 = new ParticipantDTO.Builder()
                .bot(false)
                .puuid("puuid1")
                .profileIconId(577)
                .spell1Id(5)
                .spell2Id(7)
                .championId(200)
                .teamId(10)
                .build();

        ParticipantDTO participant2 = new ParticipantDTO.Builder()
                .bot(false)
                .puuid("puuid2")
                .profileIconId(433)
                .spell1Id(5)
                .spell2Id(7)
                .championId(125)
                .teamId(20)
                .build();

        FeaturedGameInfoDTO featuredGameInfo = new FeaturedGameInfoDTO.Builder()
                .bannedChampions(List.of(bannedChampion1, bannedChampion2, bannedChampion3, bannedChampion4, bannedChampion5, bannedChampion6, bannedChampion7, bannedChampion8,
                        bannedChampion9, bannedChampion10))
                .gameId(1444)
                .gameLength(1566)
                .gameQueueConfigId(36)
                .gameMode("solo q")
                .gameType("5v5")
                .mapId(255)
                .platformId("id platform")
                .participants(List.of(participant1, participant2))
                .build();

        FeaturedGamesDTO featuredGamesDTO = new FeaturedGamesDTO.Builder()
                .gameList(List.of(featuredGameInfo))
                .clientRefreshInterval(22)
                .build();

        ResponseEntity<FeaturedGamesDTO> responseEntity = ResponseEntity.ok(featuredGamesDTO);
        when(restTemplate.exchange(ApiRiotUrls.FEATURED_GAMES_API_URL, HttpMethod.GET, null, FeaturedGamesDTO.class)).thenReturn(responseEntity);

        // WHEN
        ResponseEntity<FeaturedGamesDTO> result = featuredGameService.getFeaturedGames();

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getClientRefreshInterval()).isEqualTo(22);
        assertThat(result.getBody().getGameList()).isNotEmpty().hasSize(1);
        assertThat(result.getBody().getGameList()).extracting("gameId", "gameLength", "gameQueueConfigId", "gameMode", "gameType", "mapId", "platformId")
                .containsExactly(tuple(1444L, 1566L, 36L, "solo q", "5v5", 255L, "id platform"));
        assertThat(result.getBody().getGameList().getFirst().getParticipants()).isNotEmpty().hasSize(2)
                .extracting("bot", "puuid", "profileIconId", "spell1Id", "spell2Id", "championId", "teamId")
                .containsExactly(
                        tuple(false, "puuid1", 577L, 5L, 7L, 200L, 10L),
                        tuple(false, "puuid2", 433L, 5L, 7L, 125L, 20L)
                );

        assertThat(result.getBody().getGameList().getFirst().getBannedChampions()).isNotEmpty().hasSize(10)
                .extracting("pickTurn", "teamId", "championId")
                .containsExactly(
                        tuple(1, 10L, 10L),
                        tuple(2, 20L, 20L),
                        tuple(3, 20L, 20L),
                        tuple(4, 10L, -1L),
                        tuple(5, 10L, -1L),
                        tuple(6, 20L, 144L),
                        tuple(7, 20L, 154L),
                        tuple(8, 10L, 100L),
                        tuple(9, 10L, 144L),
                        tuple(10, 20L, 144L)
                );

        verify(restTemplate, times(1)).exchange(ApiRiotUrls.FEATURED_GAMES_API_URL, HttpMethod.GET, null, FeaturedGamesDTO.class);
    }

    @Test
    @DisplayName("Should call riot game api and return a business exception")
    void getFeaturedGames_fail_1() {
        // GIVEN
        when(restTemplate.exchange(ApiRiotUrls.FEATURED_GAMES_API_URL, HttpMethod.GET, null, FeaturedGamesDTO.class)).thenThrow(new HttpClientErrorException(HttpStatusCode.valueOf(404)));

        // WHEN + THEN
        assertThatThrownBy(() -> featuredGameService.getFeaturedGames())
                .isInstanceOf(BusinessException.class)
                .hasMessage("Erreur lors de l'appel à l'API Riot");
    }

    @Test
    @DisplayName("Should call riot game api and return a business exception")
    void getFeaturedGames_fail_2() {
        // GIVEN
        when(restTemplate.exchange(ApiRiotUrls.FEATURED_GAMES_API_URL, HttpMethod.GET, null, FeaturedGamesDTO.class)).thenThrow(new HttpServerErrorException(HttpStatusCode.valueOf(404)));

        // WHEN + THEN
        assertThatThrownBy(() -> featuredGameService.getFeaturedGames())
                .isInstanceOf(BusinessException.class)
                .hasMessage("Erreur lors de l'appel à l'API Riot");
    }

    @Test
    @DisplayName("Should call riot game api and return a business exception")
    void getFeaturedGames_fail_3() {
        // GIVEN
        when(restTemplate.exchange(ApiRiotUrls.FEATURED_GAMES_API_URL, HttpMethod.GET, null, FeaturedGamesDTO.class)).thenThrow(new ResourceAccessException(""));

        // WHEN + THEN
        assertThatThrownBy(() -> featuredGameService.getFeaturedGames())
                .isInstanceOf(BusinessException.class)
                .hasMessage("API Riot inaccessible pour le moment");
    }
}
