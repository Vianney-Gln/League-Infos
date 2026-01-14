package com.league.league_infos.services.business;

import com.league.league_infos.common.utils.CurrentLocalDateTime;
import com.league.league_infos.dto.AccountDTO;
import com.league.league_infos.dto.match.ChallengesDTO;
import com.league.league_infos.dto.match.InfoMatchDTO;
import com.league.league_infos.dto.match.MatchDTO;
import com.league.league_infos.dto.match.MetadataDTO;
import com.league.league_infos.dto.match.ParticipantMatchDTO;
import com.league.league_infos.dto.match.TeamDTO;
import com.league.league_infos.services.api.HistoryGamesService;
import com.league.league_infos.services.riot.RiotAccountService;
import com.league.league_infos.services.spi.HistoryPersistence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchDataProviderTest {

    @Mock
    private HistoryGamesService historyGamesService;

    @Mock
    private HistoryPersistence historyPersistence;

    @Mock
    private CurrentLocalDateTime currentLocalDateTime;

    @Mock
    private RiotAccountService riotAccountService;

    @InjectMocks
    private MatchDataProvider matchDataProvider;

    @Captor
    private ArgumentCaptor<List<MatchDTO>> listMatchArgumentCaptor;

    @Test
    @DisplayName("Should call historyPersistence.getMatchHistoryByGameIds and findAllMatchByPuuidAndQueue " +
            "do not call historyGamesService.getMatchHistory" +
            "do not call historyGamesService.getHistoryIds" +
            "do not call riotAccountService.getAccountByPuuid" +
            "do not call historyPersistence.persistAndRefreshFromRiotMatchHistory" +
            "case lastRefreshFromRiot < 12h"
    )
    void getMatchsHistory_success_1() {
        // GIVEN
        MatchDTO matchDTO = new MatchDTO.Builder()
                .info(new InfoMatchDTO.Builder()
                        .lastRefreshFromRiot(LocalDateTime.of(2025, 10, 9, 20, 13, 0))
                        .participants(List.of(new ParticipantMatchDTO.Builder()
                                .puuid("puuid")
                                .build()))
                        .build())
                .build();

        when(historyPersistence.findAllMatchByPuuidAndQueue(anyString(), any(Integer.class))).thenReturn(List.of(matchDTO));
        when(currentLocalDateTime.getCurrentLocalDateTime()).thenReturn(LocalDateTime.of(2025, 10, 10, 0, 0, 0));

        // WHEN
        List<MatchDTO> result = matchDataProvider.getMatchsHistory("puuid", 420);

        // THEN
        verify(historyPersistence, times(2)).findAllMatchByPuuidAndQueue("puuid", 420);
        verify(historyPersistence, never()).persistAndRefreshFromRiotMatchHistory(any());
        verify(historyGamesService, never()).getMatchHistory(any());
        verify(historyGamesService, never()).getHistoryIds(any(), any());
        verify(riotAccountService, never()).getAccountByPuuid(any());
        assertThat(result).isNotEmpty().hasSize(1);
    }

    @Test
    @DisplayName("Should call historyGamesService.getHistoryIds, historyPersistence.persistAndRefreshFromRiotMatchHistory, historyPersistence.getMatchHistoryByGameIds, " +
            "findAllMatchByPuuidAndQueue historyGamesService.getMatchHistory, accountDTO.getGameName() and get the pseudo from the current participant case lastRefreshFromRiot >" +
            " 12h")
    void getMatchsHistory_success_2() {

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
                        .lastRefreshFromRiot(LocalDateTime.of(2025, 10, 7, 23, 0, 0))
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
                                        .pseudo("pseudo")
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


        AccountDTO account = new AccountDTO.Builder()
                .gameName("pseudo")
                .build();

        when(historyGamesService.getHistoryIds(anyString(), any(Integer.class))).thenReturn(List.of("1", "2"));
        when(historyPersistence.findAllMatchByPuuidAndQueue(anyString(), any(Integer.class))).thenReturn(List.of(matchDTO));
        when(currentLocalDateTime.getCurrentLocalDateTime()).thenReturn(LocalDateTime.of(2025, 10, 10, 0, 0, 0));
        when(historyGamesService.getMatchHistory(anyList())).thenReturn(List.of(matchDTO));
        when(riotAccountService.getAccountByPuuid(anyString())).thenReturn(account);

        // WHEN
        List<MatchDTO> result = matchDataProvider.getMatchsHistory("puuid", 420);

        // THEN
        verify(historyPersistence, times(2)).findAllMatchByPuuidAndQueue("puuid", 420);
        verify(historyGamesService, times(1)).getMatchHistory(List.of("1", "2"));
        verify(historyGamesService, times(1)).getHistoryIds("puuid", 420);
        verify(riotAccountService, times(1)).getAccountByPuuid("puuid");
        assertThat(result).isNotEmpty().hasSize(1);

        verify(historyPersistence, times(1)).persistAndRefreshFromRiotMatchHistory(listMatchArgumentCaptor.capture());

        assertThat(listMatchArgumentCaptor.getValue()).isNotEmpty().hasSize(1);
        assertThat(listMatchArgumentCaptor.getValue().getFirst().getMetadata()).isNotNull().extracting("matchId", "dataVersion")
                .containsExactly("200", "dataVersion");

        assertThat(listMatchArgumentCaptor.getValue().getFirst().getInfo()).isNotNull().extracting("endOfGameResult",
                        "gameCreation",
                        "gameDuration",
                        "gameMode",
                        "gameType",
                        "gameEndTimestamp",
                        "gameVersion",
                        "queueId",
                        "lastRefreshFromRiot",
                        "mapId")
                .containsExactly("end of game result",
                        326L,
                        300L,
                        "classic",
                        "Ranked",
                        326L,
                        "game version",
                        420,
                        LocalDateTime.of(2025, 10, 7, 23, 0, 0),
                        10);

        assertThat(listMatchArgumentCaptor.getValue().getFirst().getInfo().getTeams()).isNotEmpty().hasSize(2).extracting("win", "teamId")
                .containsExactly(
                        tuple(true, 50),
                        tuple(false, 60)
                );

        assertThat(listMatchArgumentCaptor.getValue().getFirst().getInfo().getParticipants()).isNotEmpty().hasSize(2).extracting("assists",
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
                "pseudo",
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
                tuple(13, 5, 20, 125, "Shyvana", 0, 18, 2, 18566, 135, 136, 95, 77, 122, 123, 126, "JUNGLE", 104, 1, "puuid", "pseudo", 13, 1, 0, "", 27, 28, "", 50, 125, 0, true),
                tuple(5, 10, 3, 98, "Garen", 0, 17, 0, 14056, 135, 136, 94, 75, 100, null, null, "TOP", 0, 0, "puuid1", null, 36, 2, 0, "", 27, 45, "", 60, 185, 0, false)
        );
    }

    @Test
    @DisplayName("Should return an empty list if listGameids is null")
    void getMatchsHistory_success_3() {

        // GIVEN
        when(historyGamesService.getHistoryIds(anyString(), any(Integer.class))).thenReturn(null);

        // WHEN
        List<MatchDTO> result = matchDataProvider.getMatchsHistory("puuid", 420);

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return an empty list if listGameids is empty")
    void getMatchsHistory_success_4() {

        // GIVEN
        when(historyGamesService.getHistoryIds(anyString(), any(Integer.class))).thenReturn(Collections.emptyList());

        // WHEN
        List<MatchDTO> result = matchDataProvider.getMatchsHistory("puuid", 420);

        // THEN
        assertThat(result).isEmpty();
    }
}
