package com.league.league_infos.services.riot;

import com.league.league_infos.common.constants.ApiRiotUrls;
import com.league.league_infos.common.exceptions.BusinessException;
import com.league.league_infos.dto.match.ChallengesDTO;
import com.league.league_infos.dto.match.InfoMatchDTO;
import com.league.league_infos.dto.match.MatchDTO;
import com.league.league_infos.dto.match.MetadataDTO;
import com.league.league_infos.dto.match.ParticipantMatchDTO;
import com.league.league_infos.dto.match.TeamDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.league.league_infos.common.constants.ErrorMessagesEnum.ERROR_BUSINESS_1;
import static com.league.league_infos.common.constants.ErrorMessagesEnum.ERROR_BUSINESS_2;
import static com.league.league_infos.common.constants.ErrorMessagesEnum.ERROR_BUSINESS_3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RiotHistoryGamesServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RiotHistoryGamesService riotHistoryGamesService;

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(riotHistoryGamesService, "MAX_COUNT_MATCH_HISTORY", 3);
    }

    @Test
    @DisplayName("Should return a list of match ids, case queue not null")
    void getHistoryIds_succes_1() {
        // GIVEN
        when(restTemplate.exchange(
                ApiRiotUrls.HISTORY_GAMES_IDS_API_URL + "/puuid/ids?start=0&count=3&queue=420",
                HttpMethod.GET,
                null,
                String[].class))
                .thenReturn(ResponseEntity.ok(new String[]{"1", "2", "3"}));

        // WHEN
        List<String> result = riotHistoryGamesService.getHistoryIds("puuid", 420);

        // THEN
        assertThat(result).isNotEmpty().hasSize(3).containsExactly("1", "2", "3");
    }

    @Test
    @DisplayName("Should return a list of match ids, case queue null")
    void getHistoryIds_success_2() {
        // GIVEN
        when(restTemplate.exchange(
                ApiRiotUrls.HISTORY_GAMES_IDS_API_URL + "/puuid/ids?start=0&count=3",
                HttpMethod.GET,
                null,
                String[].class))
                .thenReturn(ResponseEntity.ok(new String[]{"1", "2", "3"}));

        // WHEN
        List<String> result = riotHistoryGamesService.getHistoryIds("puuid", null);

        // THEN
        assertThat(result).isNotEmpty().hasSize(3).containsExactly("1", "2", "3");
    }

    @Test
    @DisplayName("Should call riot game api and return a business exception")
    void getHistoryIds_echec_1() {
        // GIVEN
        when(restTemplate.exchange(ApiRiotUrls.HISTORY_GAMES_IDS_API_URL + "/puuid/ids?start=0&count=3",
                HttpMethod.GET,
                null,
                String[].class))
                .thenThrow(new HttpClientErrorException(HttpStatusCode.valueOf(404)));

        // WHEN + THEN
        assertThatThrownBy(() -> riotHistoryGamesService.getHistoryIds("puuid", null))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ERROR_BUSINESS_1.getLibelle());
    }

    @Test
    @DisplayName("Should call riot game api and return a business exception")
    void getHistoryIds_echec_2() {
        // GIVEN
        when(restTemplate.exchange(ApiRiotUrls.HISTORY_GAMES_IDS_API_URL + "/puuid/ids?start=0&count=3",
                HttpMethod.GET,
                null,
                String[].class))
                .thenThrow(new HttpServerErrorException(HttpStatusCode.valueOf(404)));

        // WHEN + THEN
        assertThatThrownBy(() -> riotHistoryGamesService.getHistoryIds("puuid", null))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ERROR_BUSINESS_1.getLibelle());
    }

    @Test
    @DisplayName("Should call riot game api and return a business exception")
    void getHistoryIds_echec_3() {
        // GIVEN
        when(restTemplate.exchange(ApiRiotUrls.HISTORY_GAMES_IDS_API_URL + "/puuid/ids?start=0&count=3",
                HttpMethod.GET,
                null,
                String[].class))
                .thenThrow(new ResourceAccessException(""));

        // WHEN + THEN
        assertThatThrownBy(() -> riotHistoryGamesService.getHistoryIds("puuid", null))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ERROR_BUSINESS_3.getLibelle());
    }

    @Test
    @DisplayName("Should call riot game api and return a business exception")
    void getHistoryIds_echec_4() {
        // GIVEN
        when(restTemplate.exchange(ApiRiotUrls.HISTORY_GAMES_IDS_API_URL + "/puuid/ids?start=0&count=3",
                HttpMethod.GET,
                null,
                String[].class))
                .thenThrow(new HttpServerErrorException(HttpStatusCode.valueOf(400)));

        // WHEN + THEN
        assertThatThrownBy(() -> riotHistoryGamesService.getHistoryIds("puuid", null))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ERROR_BUSINESS_2.getLibelle());
    }

    @Test
    @DisplayName("Should call riot game api and return a business exception")
    void getMatchHistory_echec_1() {
        // GIVEN
        when(restTemplate.exchange(ApiRiotUrls.HISTORY_GAMES_URL + "/id",
                HttpMethod.GET,
                null,
                MatchDTO.class))
                .thenThrow(new HttpClientErrorException(HttpStatusCode.valueOf(404)));

        // WHEN + THEN
        assertThatThrownBy(() -> riotHistoryGamesService.getMatchHistory("id"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ERROR_BUSINESS_1.getLibelle());
    }

    @Test
    @DisplayName("Should call riot game api and return a business exception")
    void getMatchHistory_echec_2() {
        // GIVEN
        when(restTemplate.exchange(ApiRiotUrls.HISTORY_GAMES_URL + "/id",
                HttpMethod.GET,
                null,
                MatchDTO.class))
                .thenThrow(new HttpServerErrorException(HttpStatusCode.valueOf(404)));

        // WHEN + THEN
        assertThatThrownBy(() -> riotHistoryGamesService.getMatchHistory("id"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ERROR_BUSINESS_1.getLibelle());
    }

    @Test
    @DisplayName("Should call riot game api and return a business exception")
    void getMatchHistory_echec_3() {
        // GIVEN
        when(restTemplate.exchange(ApiRiotUrls.HISTORY_GAMES_URL + "/id",
                HttpMethod.GET,
                null,
                MatchDTO.class))
                .thenThrow(new ResourceAccessException(""));

        // WHEN + THEN
        assertThatThrownBy(() -> riotHistoryGamesService.getMatchHistory("id"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ERROR_BUSINESS_3.getLibelle());
    }

    @Test
    @DisplayName("Should call riot game api and return a business exception")
    void getMatchHistory_echec_4() {
        // GIVEN
        when(restTemplate.exchange(ApiRiotUrls.HISTORY_GAMES_URL + "/id",
                HttpMethod.GET,
                null,
                MatchDTO.class))
                .thenThrow(new HttpServerErrorException(HttpStatusCode.valueOf(400)));

        // WHEN + THEN
        assertThatThrownBy(() -> riotHistoryGamesService.getMatchHistory("id"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ERROR_BUSINESS_2.getLibelle());
    }

    @Test
    @DisplayName("Should call riot game api and return a MatchDTO")
    void getMatchHistory_success_1() {
        // GIVEN
        MatchDTO matchDTO = new MatchDTO.Builder()
                .metadata(new MetadataDTO.Builder()
                        .matchId("200")
                        .dataVersion("dataVersion")
                        .participants(List.of("p1", "p2"))
                        .build())
                .info(new InfoMatchDTO.Builder()
                        .gameId(200L)
                        .gameCreation(326L)
                        .gameDuration(300L)
                        .endOfGameResult("end of game result")
                        .gameEndTimestamp(326L)
                        .gameMode("classic")
                        .gameName("game name")
                        .gameType("Ranked")
                        .gameVersion("game version")
                        .mapId(10)
                        .queueId(420)
                        .participants(List.of(
                                new ParticipantMatchDTO.Builder()
                                        .assists(13)
                                        .deaths(5)
                                        .kills(20)
                                        .championId(125)
                                        .championName("Shyvana")
                                        .championTransform(0)
                                        .champLevel(18)
                                        .doubleKills(2)
                                        .goldEarned(18566)
                                        .item0(135)
                                        .item1(136)
                                        .item2(95)
                                        .item3(77)
                                        .item4(122)
                                        .item5(123)
                                        .item6(126)
                                        .lane("JUNGLE")
                                        .neutralMinionsKilled(104)
                                        .pentaKills(1)
                                        .puuid("puuid")
                                        .profileIcon(13)
                                        .participantId(1)
                                        .quadraKills(0)
                                        .role("")
                                        .summoner1Id(27)
                                        .summoner2Id(28)
                                        .summonerName("")
                                        .teamId(50)
                                        .totalMinionsKilled(125)
                                        .tripleKills(0)
                                        .win(true)
                                        .challenges(new ChallengesDTO.Builder()
                                                .gameLength(326.0F)
                                                .kda(3.0F)
                                                .build())
                                        .build(),
                                new ParticipantMatchDTO.Builder()
                                        .assists(5)
                                        .deaths(10)
                                        .kills(3)
                                        .championId(98)
                                        .championName("Garen")
                                        .championTransform(0)
                                        .champLevel(17)
                                        .doubleKills(0)
                                        .goldEarned(14056)
                                        .item0(135)
                                        .item1(136)
                                        .item2(94)
                                        .item3(75)
                                        .item4(100)
                                        .lane("TOP")
                                        .neutralMinionsKilled(0)
                                        .pentaKills(0)
                                        .puuid("puuid1")
                                        .profileIcon(36)
                                        .participantId(2)
                                        .quadraKills(0)
                                        .role("")
                                        .summoner1Id(27)
                                        .summoner2Id(45)
                                        .summonerName("")
                                        .teamId(60)
                                        .totalMinionsKilled(185)
                                        .tripleKills(0)
                                        .win(false)
                                        .challenges(new ChallengesDTO.Builder()
                                                .gameLength(326.0F)
                                                .kda(3.0F)
                                                .build())
                                        .build()
                        ))
                        .teams(List.of(new TeamDTO.Builder()
                                                .win(true)
                                                .teamId(50)
                                                .build(),
                                        new TeamDTO.Builder()
                                                .win(false)
                                                .teamId(60)
                                                .build()
                                )
                        ).build())
                .build();

        ResponseEntity<MatchDTO> response = ResponseEntity.ok(matchDTO);
        when(restTemplate.exchange(ApiRiotUrls.HISTORY_GAMES_URL + "/id",
                HttpMethod.GET,
                null,
                MatchDTO.class))
                .thenReturn(response);

        // WHEN
        MatchDTO result = riotHistoryGamesService.getMatchHistory("id");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getMetadata()).isNotNull()
                .extracting("matchId", "dataVersion")
                .containsExactly("200", "dataVersion");
        assertThat(result.getMetadata().getParticipants()).containsExactly("p1", "p2");

        assertThat(result.getInfo()).isNotNull()
                .extracting("gameId",
                        "gameCreation",
                        "gameDuration",
                        "endOfGameResult",
                        "gameEndTimestamp",
                        "gameMode",
                        "gameName",
                        "gameType",
                        "gameVersion",
                        "mapId",
                        "queueId")
                .containsExactly(200L,
                        326L,
                        300L,
                        "end of game result",
                        326L,
                        "classic",
                        "game name",
                        "Ranked",
                        "game version",
                        10,
                        420);

        assertThat(result.getInfo().getParticipants()).isNotEmpty().hasSize(2)
                .extracting("assists",
                        "deaths",
                        "kills",
                        "championId",
                        "championName",
                        "championTransform",
                        "champLevel",
                        "doubleKills",
                        "goldEarned",
                        "item0",
                        "item1",
                        "item2",
                        "item3",
                        "item4",
                        "item5",
                        "item6",
                        "lane",
                        "neutralMinionsKilled",
                        "pentaKills",
                        "puuid",
                        "profileIcon",
                        "participantId",
                        "quadraKills",
                        "role",
                        "summoner1Id",
                        "summoner2Id",
                        "summonerName",
                        "teamId",
                        "totalMinionsKilled",
                        "tripleKills",
                        "win"
                ).containsExactly(
                        tuple(13, 5, 20, 125, "Shyvana", 0, 18, 2, 18566, 135, 136, 95, 77, 122, 123, 126, "JUNGLE", 104, 1, "puuid", 13, 1, 0, "", 27, 28, "", 50, 125, 0, true),
                        tuple(5, 10, 3, 98, "Garen", 0, 17, 0, 14056, 135, 136, 94, 75, 100, null, null, "TOP", 0, 0, "puuid1", 36, 2, 0, "", 27, 45, "", 60, 185, 0, false)
                );

        assertThat(result.getInfo().getParticipants().getFirst().getChallenges()).isNotNull()
                .extracting("gameLength", "kda")
                .containsExactly(326.0f, 3.0f);

        assertThat(result.getInfo().getParticipants().getLast().getChallenges()).isNotNull()
                .extracting("gameLength", "kda")
                .containsExactly(326.0f, 3.0f);

        assertThat(result.getInfo().getTeams()).isNotEmpty().hasSize(2)
                .extracting("win", "teamId")
                .containsExactly(
                        tuple(true, 50),
                        tuple(false, 60)
                );
    }
}
