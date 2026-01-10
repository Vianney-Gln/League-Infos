package com.league.league_infos.mapper;

import com.league.league_infos.dao.entity.InfoMatchEntity;
import com.league.league_infos.dto.match.ChallengesDTO;
import com.league.league_infos.dto.match.InfoMatchDTO;
import com.league.league_infos.dto.match.MatchDTO;
import com.league.league_infos.dto.match.MetadataDTO;
import com.league.league_infos.dto.match.ParticipantMatchDTO;
import com.league.league_infos.dto.match.TeamDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;

class InfoMatchMapperTest {

    @Test
    @DisplayName("Should map correctly")
    void infoMatchdtoToEntity_success() {
        // GIVEN
        MetadataDTO metaDataDTO = new MetadataDTO.Builder().dataVersion("version").matchId("100").build();
        TeamDTO teamDTO = new TeamDTO.Builder().win(true).teamId(10).build();
        ParticipantMatchDTO participantMatchDTO = new ParticipantMatchDTO.Builder()
                .participantId(135)
                .assists(12)
                .championId(98)
                .deaths(5)
                .championName("champ name")
                .championTransform(0)
                .champLevel(18)
                .doubleKills(2)
                .tripleKills(0)
                .quadraKills(1)
                .pentaKills(0)
                .goldEarned(14000)
                .kills(10)
                .lane("top")
                .role("NONE")
                .neutralMinionsKilled(104)
                .totalMinionsKilled(125)
                .puuid("puuid")
                .profileIcon(14)
                .teamId(10)
                .teamPosition("")
                .summonerName("sum name")
                .summoner1Id(10)
                .summoner2Id(11)
                .win(true)
                .item0(50)
                .item1(51)
                .item2(52)
                .item3(53)
                .item4(54)
                .item5(55)
                .item6(56)
                .challenges(new ChallengesDTO.Builder().gameLength(1330F).kda(10F).build())
                .build();

        InfoMatchDTO infoMatchDTO = new InfoMatchDTO.Builder()
                .endOfGameResult("end of game")
                .gameCreation(1000L)
                .gameDuration(50000L)
                .gameId(56565656L)
                .gameEndTimestamp(478787878L)
                .gameVersion("version")
                .gameMode("CLASSIC")
                .gameType("RANKED")
                .mapId(50)
                .queueId(420)
                .gameName("game name")
                .participants(List.of(participantMatchDTO))
                .teams(List.of(teamDTO))
                .build();

        MatchDTO matchDTO = new MatchDTO.Builder().info(infoMatchDTO).metadata(metaDataDTO).build();

        // WHEN
        InfoMatchEntity result = InfoMatchMapper.infoMatchdtoToEntity(matchDTO);

        // THEN
        assertThat(result).isNotNull().extracting("endOfGameResult",
                        "gameCreation",
                        "gameDuration",
                        "gameMode",
                        "gameType",
                        "gameEndTimestamp",
                        "gameVersion",
                        "queueId",
                        "mapId")
                .containsExactly("end of game",
                        1000L,
                        50000L,
                        "CLASSIC",
                        "RANKED",
                        478787878L,
                        "version",
                        420,
                        50);

        assertThat(result.getParticipantMatchEntityList()).isNotEmpty().hasSize(1)
                .extracting("idParticipant",
                        "assists",
                        "championId",
                        "deaths",
                        "championName",
                        "championTransform",
                        "champLevel",
                        "doubleKills",
                        "tripleKills",
                        "quadraKills",
                        "pentaKills",
                        "goldEarned",
                        "kills",
                        "lane",
                        "role",
                        "neutralMinionsKilled",
                        "totalMinionsKilled",
                        "puuid",
                        "profileIcon",
                        "teamId",
                        "teamPosition",
                        "summonerName",
                        "summoner1Id",
                        "summoner2Id",
                        "win",
                        "item0",
                        "item1",
                        "item2",
                        "item3",
                        "item4",
                        "item5",
                        "item6")
                .containsExactly(tuple(135L, 12, 98, 5, "champ name", 0, 18, 2, 0, 1, 0, 14000, 10, "top", "NONE", 104, 125, "puuid", 14, 10, "", "sum name", 10, 11, true, 50,
                        51, 52, 53, 54, 55, 56));

        assertThat(result.getParticipantMatchEntityList().getFirst().getMetaDataEntity()).isNotNull()
                .extracting("dataVersion", "matchId")
                .containsExactly("version", "100");


        assertThat(result.getTeamEntity()).isNotNull();
    }

    @Test
    @DisplayName("Should throw an exception if matchDTO is null")
    void infoMatchdtoToEntity_fail() {

        // WHEN + THEN
        assertThatThrownBy(() -> InfoMatchMapper.infoMatchdtoToEntity(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("matchDTO cannot be null");
    }
}
