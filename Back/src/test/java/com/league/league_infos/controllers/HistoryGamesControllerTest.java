package com.league.league_infos.controllers;

import com.league.league_infos.dto.match.ChallengesDTO;
import com.league.league_infos.dto.match.InfoMatchDTO;
import com.league.league_infos.dto.match.MatchDTO;
import com.league.league_infos.dto.match.MetadataDTO;
import com.league.league_infos.dto.match.ParticipantMatchDTO;
import com.league.league_infos.dto.match.TeamDTO;
import com.league.league_infos.services.business.MatchDataProvider;
import com.league.league_infos.services.spi.HistoryPersistence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HistoryGamesController.class)
class HistoryGamesControllerTest {

    @MockitoBean
    private MatchDataProvider matchDataProvider;

    @MockitoBean
    private HistoryPersistence historyPersistence;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return a list of match history")
    void getGamesHistory_success_1() throws Exception {
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
                                        .build()))
                        .build())
                .build();
        when(matchDataProvider.getMatchsHistory(anyString(), any(Integer.class))).thenReturn(List.of(matchDTO));

        // WHEN
        mockMvc.perform(get("/games-history/puuid")
                        .param("queue", "420")
                ).andExpect(status().isOk())
                .andExpect(content().json("""
                        [{"metadata":{
                            "dataVersion":"dataVersion",
                            "matchId":"200",
                            "participants":["p1","p2"]},
                            "info":{
                                "endOfGameResult":"end of game result",
                                "gameMode":"classic",
                                "gameName":"game name",
                                "gameType":"Ranked",
                                "gameVersion":"game version"
                                ,"gameCreation":326,
                                "gameDuration":300,
                                "gameEndTimestamp":326,
                                "gameId":200,
                                "mapId":10,
                                "queueId":420,
                                "participants":[
                                    {"participantId":1,
                                    "profileIcon":13,
                                    "assists":13,
                                    "kills":20,
                                    "deaths":5,
                                    "champLevel":18,
                                    "championId":125,
                                    "goldEarned":18566,
                                    "item0":135,
                                    "item1":136,
                                    "item2":95,
                                    "item3":77,
                                    "item4":122,
                                    "item5":123,
                                    "item6":126,
                                    "championTransform":0,
                                    "pentaKills":1,
                                    "quadraKills":0,
                                    "tripleKills":0,
                                    "doubleKills":2,
                                    "summoner1Id":27,
                                    "summoner2Id":28,
                                    "teamId":50,
                                    "totalMinionsKilled":125,
                                    "neutralMinionsKilled":104,
                                    "championName":"Shyvana",
                                    "summonerName":"",
                                    "lane":"JUNGLE",
                                    "role":"",
                                    "puuid":"puuid",
                                    "win":true,
                                    "challenges":{"gameLength":326.0,"kda":3.0}
                                    },
                                    {"participantId":2,
                                    "profileIcon":36,
                                    "assists":5,
                                    "kills":3,
                                    "deaths":10,
                                    "champLevel":17,
                                    "championId":98,
                                    "goldEarned":14056,
                                    "item0":135,
                                    "item1":136,
                                    "item2":94,
                                    "item3":75,
                                    "item4":100,
                                    "item5":null,
                                    "item6":null,
                                    "championTransform":0,
                                    "pentaKills":0,
                                    "quadraKills":0,
                                    "tripleKills":0,
                                    "doubleKills":0,
                                    "summoner1Id":27,
                                    "summoner2Id":45,
                                    "teamId":60,
                                    "totalMinionsKilled":185,
                                    "neutralMinionsKilled":0,
                                    "championName":"Garen",
                                    "summonerName":"",
                                    "lane":"TOP",
                                    "role":"",
                                    "puuid":"puuid1",
                                    "win":false,
                                    "challenges":{"gameLength":326.0,"kda":3.0}
                                    }
                                    ],
                                    "teams":[
                                        {"teamId":50,"win":true},
                                        {"teamId":60,"win":false}
                                        ]
                                    }}]
                        """));

        // THEN
        verify(matchDataProvider, times(1)).getMatchsHistory("puuid", 420);
    }

    @Test
    @DisplayName("Should return a list of match history before the creation date")
    void getGamesHistoryBeforecreationDate_success_1() throws Exception {
        // GIVEN
        MatchDTO matchDTO = new MatchDTO.Builder()
                .metadata(new MetadataDTO.Builder()
                        .matchId("200")
                        .dataVersion("dataVersion")
                        .participants(List.of("p1", "p2"))
                        .build())
                .info(new InfoMatchDTO.Builder()
                        .gameId(200L)
                        .gameCreation(175125125125L)
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
                                        .build()))
                        .build())
                .build();
        when(historyPersistence.findAllMatchByPuuidAndQueueBeforeGivenDate(anyString(), anyLong(), any(Integer.class))).thenReturn(List.of(matchDTO));

        // WHEN
        mockMvc.perform(get("/games-history-before-creation-date/puuid")
                        .param("queue", "420")
                        .param("gameCreation", String.valueOf(175125125125L))
                ).andExpect(status().isOk())
                .andExpect(content().json("""
                        [{"metadata":{
                            "dataVersion":"dataVersion",
                            "matchId":"200",
                            "participants":["p1","p2"]},
                            "info":{
                                "endOfGameResult":"end of game result",
                                "gameMode":"classic",
                                "gameName":"game name",
                                "gameType":"Ranked",
                                "gameVersion":"game version"
                                ,"gameCreation":175125125125,
                                "gameDuration":300,
                                "gameEndTimestamp":326,
                                "gameId":200,
                                "mapId":10,
                                "queueId":420,
                                "participants":[
                                    {"participantId":1,
                                    "profileIcon":13,
                                    "assists":13,
                                    "kills":20,
                                    "deaths":5,
                                    "champLevel":18,
                                    "championId":125,
                                    "goldEarned":18566,
                                    "item0":135,
                                    "item1":136,
                                    "item2":95,
                                    "item3":77,
                                    "item4":122,
                                    "item5":123,
                                    "item6":126,
                                    "championTransform":0,
                                    "pentaKills":1,
                                    "quadraKills":0,
                                    "tripleKills":0,
                                    "doubleKills":2,
                                    "summoner1Id":27,
                                    "summoner2Id":28,
                                    "teamId":50,
                                    "totalMinionsKilled":125,
                                    "neutralMinionsKilled":104,
                                    "championName":"Shyvana",
                                    "summonerName":"",
                                    "lane":"JUNGLE",
                                    "role":"",
                                    "puuid":"puuid",
                                    "win":true,
                                    "challenges":{"gameLength":326.0,"kda":3.0}
                                    },
                                    {"participantId":2,
                                    "profileIcon":36,
                                    "assists":5,
                                    "kills":3,
                                    "deaths":10,
                                    "champLevel":17,
                                    "championId":98,
                                    "goldEarned":14056,
                                    "item0":135,
                                    "item1":136,
                                    "item2":94,
                                    "item3":75,
                                    "item4":100,
                                    "item5":null,
                                    "item6":null,
                                    "championTransform":0,
                                    "pentaKills":0,
                                    "quadraKills":0,
                                    "tripleKills":0,
                                    "doubleKills":0,
                                    "summoner1Id":27,
                                    "summoner2Id":45,
                                    "teamId":60,
                                    "totalMinionsKilled":185,
                                    "neutralMinionsKilled":0,
                                    "championName":"Garen",
                                    "summonerName":"",
                                    "lane":"TOP",
                                    "role":"",
                                    "puuid":"puuid1",
                                    "win":false,
                                    "challenges":{"gameLength":326.0,"kda":3.0}
                                    }
                                    ],
                                    "teams":[
                                        {"teamId":50,"win":true},
                                        {"teamId":60,"win":false}
                                        ]
                                    }}]
                        """));

        // THEN
        verify(historyPersistence, times(1)).findAllMatchByPuuidAndQueueBeforeGivenDate("puuid", 175125125125L, 420);
    }
}
