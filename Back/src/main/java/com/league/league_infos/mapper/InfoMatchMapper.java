package com.league.league_infos.mapper;

import com.league.league_infos.dao.entity.InfoMatchEntity;
import com.league.league_infos.dto.match.InfoMatchDTO;
import com.league.league_infos.dto.match.MatchDTO;

import java.util.Objects;

public class InfoMatchMapper {

    public static InfoMatchEntity infoMatchdtoToEntity(MatchDTO matchDTO) {
        Objects.requireNonNull(matchDTO, "matchDTO cannot be null");

        InfoMatchEntity infoMatchEntity = new InfoMatchEntity();
        infoMatchEntity.setEndOfGameResult(matchDTO.getInfo().getEndOfGameResult());
        infoMatchEntity.setGameCreation(matchDTO.getInfo().getGameCreation());
        infoMatchEntity.setGameDuration(matchDTO.getInfo().getGameDuration());
        infoMatchEntity.setGameId(matchDTO.getInfo().getGameId());
        infoMatchEntity.setGameEndTimestamp(matchDTO.getInfo().getGameEndTimestamp());
        infoMatchEntity.setGameVersion(matchDTO.getInfo().getGameVersion());
        infoMatchEntity.setGameMode(matchDTO.getInfo().getGameMode());
        infoMatchEntity.setGameType(matchDTO.getInfo().getGameType());
        infoMatchEntity.setMapId(matchDTO.getInfo().getMapId());
        infoMatchEntity.setQueueId(matchDTO.getInfo().getQueueId());
        infoMatchEntity.setGameName(matchDTO.getInfo().getGameName());
        infoMatchEntity.setParticipantMatchEntityList(matchDTO.getInfo().getParticipants()
                .stream()
                .map(participant -> ParticipantMatchMapper.participantMatchDtoToEntity(participant, infoMatchEntity, matchDTO.getMetadata(), matchDTO.getInfo().getGameId())).toList());
        infoMatchEntity.setTeamEntity(matchDTO.getInfo().getTeams().stream().map(team -> TeamMapper.teamDtoToEntity(team, infoMatchEntity)).toList());

        return infoMatchEntity;
    }

    public static InfoMatchDTO infoMatchEntityToDTO(InfoMatchEntity infoMatchEntity) {
        Objects.requireNonNull(infoMatchEntity, "infoMatchEntity cannot be null");
        InfoMatchDTO infoMatchDTO = new InfoMatchDTO();
        infoMatchDTO.setEndOfGameResult(infoMatchEntity.getEndOfGameResult());
        infoMatchDTO.setGameCreation(infoMatchEntity.getGameCreation());
        infoMatchDTO.setGameDuration(infoMatchEntity.getGameDuration());
        infoMatchDTO.setGameMode(infoMatchEntity.getGameMode());
        infoMatchDTO.setGameType(infoMatchEntity.getGameType());
        infoMatchDTO.setGameEndTimestamp(infoMatchEntity.getGameEndTimestamp());
        infoMatchDTO.setGameVersion(infoMatchEntity.getGameVersion());
        infoMatchDTO.setQueueId(infoMatchEntity.getQueueId());
        infoMatchDTO.setMapId(infoMatchEntity.getMapId());
        if (infoMatchEntity.getParticipantMatchEntityList() != null) {
            infoMatchDTO.setParticipants(infoMatchEntity.getParticipantMatchEntityList().stream().map(ParticipantMatchMapper::participantMatchEntityToDTO).toList());
        }
        infoMatchDTO.setTeams(infoMatchEntity.getTeamEntity().stream().map(TeamMapper::teamEntityToDTO).toList());
        infoMatchDTO.setCreationDate(infoMatchEntity.getCreationDate());
        infoMatchDTO.setLastRefreshFromRiot(infoMatchEntity.getLastRefreshFromRiot());
        return infoMatchDTO;
    }
}
