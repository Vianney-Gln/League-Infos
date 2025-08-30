package com.league.league_infos.mapper;

import com.league.league_infos.dao.entity.ChallengesEntity;
import com.league.league_infos.dao.entity.InfoMatchEntity;
import com.league.league_infos.dao.entity.ParticipantMatchEntity;
import com.league.league_infos.dto.match.ChallengesDTO;
import com.league.league_infos.dto.match.MetadataDTO;
import com.league.league_infos.dto.match.ParticipantMatchDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParticipantMatchMapperTest {

    @Test
    @DisplayName("Must map correctly")
    void participantMatchDtoToEntity_success() {
        // GIVEN
        Long gameId = 105L;
        InfoMatchEntity infoMatchEntity = new InfoMatchEntity();
        MetadataDTO metadataDTO = new MetadataDTO.Builder().matchId("100").dataVersion("version").build();

        infoMatchEntity.setParticipantMatchEntityList(List.of(new ParticipantMatchEntity()));

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

        // WHEN
        ParticipantMatchEntity result = ParticipantMatchMapper.participantMatchDtoToEntity(participantMatchDTO, infoMatchEntity, metadataDTO, gameId);

        // THEN
        assertThat(result).isNotNull()
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
                .containsExactly(135L,
                        12,
                        98,
                        5,
                        "champ name",
                        0,
                        18,
                        2,
                        0,
                        1,
                        0,
                        14000,
                        10,
                        "top",
                        "NONE",
                        104,
                        125,
                        "puuid",
                        14,
                        10,
                        "",
                        "sum name",
                        10,
                        11,
                        true,
                        50,
                        51,
                        52,
                        53,
                        54,
                        55,
                        56);

        assertThat(result.getChallengesEntity()).isNotNull();
    }

    @Test
    @DisplayName("Must throw an exception if participantMatchDTO is null")
    void participantMatchDtoToEntity_fail_1() {
        // GIVEN
        Long gameId = 105L;
        InfoMatchEntity infoMatchEntity = new InfoMatchEntity();
        MetadataDTO metadataDTO = new MetadataDTO.Builder().matchId("100").dataVersion("version").build();

        infoMatchEntity.setParticipantMatchEntityList(List.of(new ParticipantMatchEntity()));

        // WHEN + THEN
        assertThatThrownBy(() -> ParticipantMatchMapper.participantMatchDtoToEntity(null, infoMatchEntity, metadataDTO, gameId))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("participantMatchDTO cannot be null");
    }

    @Test
    @DisplayName("Must throw an exception if participantMatchDTO.participantId is null")
    void participantMatchDtoToEntity_fail_2() {
        // GIVEN
        Long gameId = 105L;
        InfoMatchEntity infoMatchEntity = new InfoMatchEntity();
        MetadataDTO metadataDTO = new MetadataDTO.Builder().matchId("100").dataVersion("version").build();

        infoMatchEntity.setParticipantMatchEntityList(List.of(new ParticipantMatchEntity()));

        ParticipantMatchDTO participantMatchDTO = new ParticipantMatchDTO.Builder()
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

        // WHEN + THEN
        assertThatThrownBy(() -> ParticipantMatchMapper.participantMatchDtoToEntity(participantMatchDTO, infoMatchEntity, metadataDTO, gameId))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("participantMatchDTO.participantId cannot be null");
    }

    @Test
    @DisplayName("Must throw an exception if metadataDTO is null")
    void participantMatchDtoToEntity_fail_3() {
        // GIVEN
        Long gameId = 105L;
        InfoMatchEntity infoMatchEntity = new InfoMatchEntity();

        infoMatchEntity.setParticipantMatchEntityList(List.of(new ParticipantMatchEntity()));

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

        // WHEN + THEN
        assertThatThrownBy(() -> ParticipantMatchMapper.participantMatchDtoToEntity(participantMatchDTO, infoMatchEntity, null, gameId))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("metadataDTO cannot be null");
    }

    @Test
    @DisplayName("Must throw an exception if gameId is null")
    void participantMatchDtoToEntity_fail_4() {
        // GIVEN
        InfoMatchEntity infoMatchEntity = new InfoMatchEntity();
        MetadataDTO metadataDTO = new MetadataDTO.Builder().matchId("100").dataVersion("version").build();

        infoMatchEntity.setParticipantMatchEntityList(List.of(new ParticipantMatchEntity()));

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

        // WHEN + THEN
        assertThatThrownBy(() -> ParticipantMatchMapper.participantMatchDtoToEntity(participantMatchDTO, infoMatchEntity, metadataDTO, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("gameId cannot be null");
    }

    @Test
    @DisplayName("Must map correctly")
    void participantMatchEntityToDTO_success() {
        ParticipantMatchEntity participantMatchEntity = new ParticipantMatchEntity();
        participantMatchEntity.setIdParticipant(135L);
        participantMatchEntity.setAssists(12);
        participantMatchEntity.setChampionId(98);
        participantMatchEntity.setDeaths(5);
        participantMatchEntity.setChampionName("champ name");
        participantMatchEntity.setChampionTransform(0);
        participantMatchEntity.setChampLevel(18);
        participantMatchEntity.setDoubleKills(2);
        participantMatchEntity.setTripleKills(0);
        participantMatchEntity.setQuadraKills(1);
        participantMatchEntity.setPentaKills(0);
        participantMatchEntity.setGoldEarned(14000);
        participantMatchEntity.setKills(10);
        participantMatchEntity.setLane("top");
        participantMatchEntity.setRole("NONE");
        participantMatchEntity.setNeutralMinionsKilled(104);
        participantMatchEntity.setTotalMinionsKilled(125);
        participantMatchEntity.setPuuid("puuid");
        participantMatchEntity.setProfileIcon(14);
        participantMatchEntity.setTeamId(10);
        participantMatchEntity.setTeamPosition("");
        participantMatchEntity.setSummonerName("sum name");
        participantMatchEntity.setSummoner1Id(10);
        participantMatchEntity.setSummoner2Id(11);
        participantMatchEntity.setWin(true);
        participantMatchEntity.setItem0(50);
        participantMatchEntity.setItem1(51);
        participantMatchEntity.setItem2(52);
        participantMatchEntity.setItem3(53);
        participantMatchEntity.setItem4(54);
        participantMatchEntity.setItem5(55);
        participantMatchEntity.setItem6(56);

        ChallengesEntity challengesEntity = new ChallengesEntity();
        challengesEntity.setGameLength(1330F);
        challengesEntity.setKda(13F);
        participantMatchEntity.setChallengesEntity(challengesEntity);

        // WHEN
        ParticipantMatchDTO result = ParticipantMatchMapper.participantMatchEntityToDTO(participantMatchEntity);

        // THEN
        assertThat(result).isNotNull()
                .extracting("ParticipantId",
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
                .containsExactly(135,
                        12,
                        98,
                        5,
                        "champ name",
                        0,
                        18,
                        2,
                        0,
                        1,
                        0,
                        14000,
                        10,
                        "top",
                        "NONE",
                        104,
                        125,
                        "puuid",
                        14,
                        10,
                        "",
                        "sum name",
                        10,
                        11,
                        true,
                        50,
                        51,
                        52,
                        53,
                        54,
                        55,
                        56);
    }

    @Test
    @DisplayName("Must throw an exception if participantMatchEntity is null")
    void participantMatchEntityToDTO_fail_1() {

        // WHEN + WHEN
        assertThatThrownBy(() -> ParticipantMatchMapper.participantMatchEntityToDTO(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("participantMatchEntity cannot be null");

    }

    @Test
    @DisplayName("Must throw an exception if participantMatchEntity.participantId is null")
    void participantMatchEntityToDTO_fail_2() {
        // GIVEN
        ParticipantMatchEntity participantMatchEntity = new ParticipantMatchEntity();
        participantMatchEntity.setAssists(12);
        participantMatchEntity.setChampionId(98);
        participantMatchEntity.setDeaths(5);
        participantMatchEntity.setChampionName("champ name");
        participantMatchEntity.setChampionTransform(0);
        participantMatchEntity.setChampLevel(18);
        participantMatchEntity.setDoubleKills(2);
        participantMatchEntity.setTripleKills(0);
        participantMatchEntity.setQuadraKills(1);
        participantMatchEntity.setPentaKills(0);
        participantMatchEntity.setGoldEarned(14000);
        participantMatchEntity.setKills(10);
        participantMatchEntity.setLane("top");
        participantMatchEntity.setRole("NONE");
        participantMatchEntity.setNeutralMinionsKilled(104);
        participantMatchEntity.setTotalMinionsKilled(125);
        participantMatchEntity.setPuuid("puuid");
        participantMatchEntity.setProfileIcon(14);
        participantMatchEntity.setTeamId(10);
        participantMatchEntity.setTeamPosition("");
        participantMatchEntity.setSummonerName("sum name");
        participantMatchEntity.setSummoner1Id(10);
        participantMatchEntity.setSummoner2Id(11);
        participantMatchEntity.setWin(true);
        participantMatchEntity.setItem0(50);
        participantMatchEntity.setItem1(51);
        participantMatchEntity.setItem2(52);
        participantMatchEntity.setItem3(53);
        participantMatchEntity.setItem4(54);
        participantMatchEntity.setItem5(55);
        participantMatchEntity.setItem6(56);

        ChallengesEntity challengesEntity = new ChallengesEntity();
        challengesEntity.setGameLength(1330F);
        challengesEntity.setKda(13F);
        participantMatchEntity.setChallengesEntity(challengesEntity);

        // WHEN + WHEN
        assertThatThrownBy(() -> ParticipantMatchMapper.participantMatchEntityToDTO(participantMatchEntity))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("participantMatchEntity.participantId cannot be null");
    }

}
