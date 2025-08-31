package com.league.league_infos.mapper;

import com.league.league_infos.dao.entity.InfoMatchEntity;
import com.league.league_infos.dao.entity.TeamEntity;
import com.league.league_infos.dto.match.TeamDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamMapperTest {

    @Test
    @DisplayName("Must map correctly")
    void teamDtoToEntity_success() {
        // GIVEN
        TeamDTO teamDTO = new TeamDTO.Builder()
                .teamId(10)
                .win(true)
                .build();

        // WHEN
        TeamEntity result = TeamMapper.teamDtoToEntity(teamDTO, new InfoMatchEntity());

        // THEN
        assertThat(result).isNotNull().extracting("teamIdNumero", "win")
                .containsExactly(10, true);
        assertThat(result.getInfoMatchEntity()).isNotNull();
    }

    @Test
    @DisplayName("Must map correctly")
    void teamEntityToDTO_success() {
        // GIVEN
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setTeamIdNumero(10);
        teamEntity.setWin(true);

        // WHEN
        TeamDTO result = TeamMapper.teamEntityToDTO(teamEntity);

        // THEN
        assertThat(result).isNotNull().extracting("teamId", "win")
                .containsExactly(10, true);
    }
}
