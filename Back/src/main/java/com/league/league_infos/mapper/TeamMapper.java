package com.league.league_infos.mapper;

import com.league.league_infos.dao.entity.InfoMatchEntity;
import com.league.league_infos.dao.entity.TeamEntity;
import com.league.league_infos.dto.match.TeamDTO;

public class TeamMapper {
    public static TeamEntity teamDtoToEntity(TeamDTO teamDTO, InfoMatchEntity infoMatchEntity) {
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setTeamIdNumero(teamDTO.getTeamId());
        teamEntity.setWin(teamDTO.getWin());
        teamEntity.setInfoMatchEntity(infoMatchEntity);
        return teamEntity;
    }

    public static TeamDTO teamEntityToDTO(TeamEntity teamEntity) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamId(teamEntity.getTeamIdNumero());
        teamDTO.setWin(teamEntity.isWin());
        return teamDTO;
    }
}
