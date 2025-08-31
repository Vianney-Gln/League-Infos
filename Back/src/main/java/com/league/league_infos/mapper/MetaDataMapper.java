package com.league.league_infos.mapper;

import com.league.league_infos.dao.entity.MetaDataEntity;
import com.league.league_infos.dto.match.MetadataDTO;

import java.util.Objects;

public class MetaDataMapper {
    public static MetaDataEntity metaDatadtoToEntity(MetadataDTO metadataDTO, Long gameId) {
        Objects.requireNonNull(metadataDTO, "metadataDTO cannot be null");
        Objects.requireNonNull(gameId, "gameId cannot be null");
        Objects.requireNonNull(metadataDTO.getMatchId(), "metadataDTO.matchId cannot be null");
        Objects.requireNonNull(metadataDTO.getDataVersion(), "metadataDTO.dataVersion cannot be null");

        MetaDataEntity metaDataEntity = new MetaDataEntity();
        metaDataEntity.setDataVersion(metadataDTO.getDataVersion());
        metaDataEntity.setMatchId(metadataDTO.getMatchId());
        metaDataEntity.setGameId(gameId);
        return metaDataEntity;
    }

    public static MetadataDTO metaDataEntityToDTO(MetaDataEntity metaDataEntity) {
        MetadataDTO metadataDTO = new MetadataDTO();
        metadataDTO.setDataVersion(metaDataEntity.getDataVersion());
        metadataDTO.setMatchId(metaDataEntity.getMatchId());
        return metadataDTO;
    }
}
