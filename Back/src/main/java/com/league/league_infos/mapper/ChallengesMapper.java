package com.league.league_infos.mapper;

import com.league.league_infos.dao.entity.ChallengesEntity;
import com.league.league_infos.dao.entity.InfoMatchEntity;
import com.league.league_infos.dto.match.ChallengesDTO;

import java.util.Objects;

public class ChallengesMapper {

    public static ChallengesEntity challengesDtoToEntity(ChallengesDTO challengesDTO, InfoMatchEntity infoMatchEntity) {
        Objects.requireNonNull(challengesDTO, "challengesDTO cannot be null");
        Objects.requireNonNull(infoMatchEntity, "infoMatchEntity cannot be null");
        Objects.requireNonNull(challengesDTO.getKda(), "challengesDTO.kda cannot be null");
        Objects.requireNonNull(challengesDTO.getGameLength(), "challengesDTO.gameLength cannot be null");

        ChallengesEntity challengesEntity = new ChallengesEntity();
        challengesEntity.setKda(challengesDTO.getKda());
        challengesEntity.setGameLength(challengesDTO.getGameLength());
        challengesEntity.setParticipantMatchEntityList(infoMatchEntity.getParticipantMatchEntityList());
        return challengesEntity;
    }

    public static ChallengesDTO challengesEntityToDTO(ChallengesEntity challengesEntity) {
        Objects.requireNonNull(challengesEntity, "challengesEntity cannot be null");
        Objects.requireNonNull(challengesEntity.getKda(), "challengesEntity.kda cannot be null");
        Objects.requireNonNull(challengesEntity.getGameLength(), "challengesEntity.gameLength cannot be null");
        return new ChallengesDTO.Builder()
                .kda(challengesEntity.getKda())
                .gameLength(challengesEntity.getGameLength())
                .build();
    }
}
