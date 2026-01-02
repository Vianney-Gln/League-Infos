package com.league.league_infos.mapper;

import com.league.league_infos.dao.entity.InfoMatchEntity;
import com.league.league_infos.dto.match.MatchDTO;

public class MatchMapper {

    public static MatchDTO infoMatchEntityToMatchDTO(InfoMatchEntity infoMatchEntity) {
        MatchDTO matchDTO = new MatchDTO();
        if (infoMatchEntity == null) {
            return null;
        }

        if (infoMatchEntity.getParticipantMatchEntityList() != null &&
                infoMatchEntity.getParticipantMatchEntityList().getFirst() != null &&
                infoMatchEntity.getParticipantMatchEntityList().getFirst().getMetaDataEntity() != null
        ) {
            matchDTO.setMetadata(MetaDataMapper.metaDataEntityToDTO(infoMatchEntity.getParticipantMatchEntityList().getFirst().getMetaDataEntity()));
        }

        matchDTO.setInfo(InfoMatchMapper.infoMatchEntityToDTO(infoMatchEntity));
        return matchDTO;
    }
}
