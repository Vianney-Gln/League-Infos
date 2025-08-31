package com.league.league_infos.mapper;

import com.league.league_infos.dao.entity.ChallengesEntity;
import com.league.league_infos.dao.entity.InfoMatchEntity;
import com.league.league_infos.dao.entity.MetaDataEntity;
import com.league.league_infos.dao.entity.ParticipantMatchEntity;
import com.league.league_infos.dao.entity.TeamEntity;
import com.league.league_infos.dto.match.MatchDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MatchMapperTest {

    @Test
    @DisplayName("Must map correctly")
    void infoMatchEntityToMatchDTO_success() {
        // GIVEN
        InfoMatchEntity infoMatchEntity = new InfoMatchEntity();
        ParticipantMatchEntity participantMatchEntity = new ParticipantMatchEntity();

        MetaDataEntity metaDataEntity = new MetaDataEntity();
        metaDataEntity.setMatchId("100");
        metaDataEntity.setDataVersion("version");

        ChallengesEntity challengesEntity = new ChallengesEntity();
        challengesEntity.setKda(12.5F);
        challengesEntity.setGameLength(1244.5F);

        participantMatchEntity.setMetaDataEntity(metaDataEntity);
        participantMatchEntity.setIdParticipant(18L);
        participantMatchEntity.setChallengesEntity(challengesEntity);

        infoMatchEntity.setParticipantMatchEntityList(List.of(participantMatchEntity));

        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setWin(true);
        teamEntity.setTeamIdNumero(10);
        teamEntity.setIdTeam(10L);

        infoMatchEntity.setTeamEntity(List.of(teamEntity));

        infoMatchEntity.setEndOfGameResult("result");
        infoMatchEntity.setGameCreation(2055L);
        infoMatchEntity.setGameDuration(5000L);
        infoMatchEntity.setGameMode("CLASSIC");
        infoMatchEntity.setGameType("RANKED");
        infoMatchEntity.setGameEndTimestamp(5600L);
        infoMatchEntity.setGameVersion("version");
        infoMatchEntity.setQueueId(420);
        infoMatchEntity.setMapId(35);

        // WHEN
        MatchDTO result = MatchMapper.infoMatchEntityToMatchDTO(infoMatchEntity);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getMetadata())
                .isNotNull()
                .extracting("matchId", "dataVersion")
                .containsExactly("100", "version");

        assertThat(result.getInfo())
                .isNotNull()
                .extracting("endOfGameResult",
                        "gameCreation",
                        "gameDuration",
                        "gameMode",
                        "gameType",
                        "gameEndTimestamp",
                        "gameVersion",
                        "queueId",
                        "mapId")
                .containsExactly("result", 2055L, 5000L, "CLASSIC", "RANKED", 5600L, "version", 420, 35);

        assertThat(result.getInfo().getParticipants()).isNotEmpty().hasSize(1);
        assertThat(result.getInfo().getTeams()).isNotEmpty().hasSize(1);
    }

}
