package com.league.league_infos.mapper;

import com.league.league_infos.dao.entity.MetaDataEntity;
import com.league.league_infos.dto.match.MetadataDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MetaDataMapperTest {

    @Test
    @DisplayName("Should map correctly")
    void metaDatadtoToEntity_success() {
        // GIVEN
        MetadataDTO metadataDTO = new MetadataDTO.Builder()
                .dataVersion("version")
                .matchId("100")
                .build();

        // WHEN
        MetaDataEntity result = MetaDataMapper.metaDatadtoToEntity(metadataDTO, 100L);

        // THEN
        assertThat(result).isNotNull().extracting("dataVersion", "matchId", "gameId")
                .containsExactly("version", "100", 100L);
    }

    @Test
    @DisplayName("Should throw an exception if metadataDTO is null")
    void metaDatadtoToEntity_fail_1() {
        // GIVEN + WHEN + THEN
        assertThatThrownBy(() -> MetaDataMapper.metaDatadtoToEntity(null, 100L))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("metadataDTO cannot be null");
    }

    @Test
    @DisplayName("Should throw an exception if gameId is null")
    void metaDatadtoToEntity_fail_2() {
        // GIVEN
        MetadataDTO metadataDTO = new MetadataDTO.Builder()
                .dataVersion("version")
                .matchId("100")
                .build();

        // WHEN + THEN
        assertThatThrownBy(() -> MetaDataMapper.metaDatadtoToEntity(metadataDTO, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("gameId cannot be null");
    }

    @Test
    @DisplayName("Should throw an exception if metadataDTO.matchId is null")
    void metaDatadtoToEntity_fail_3() {
        // GIVEN
        MetadataDTO metadataDTO = new MetadataDTO.Builder()
                .dataVersion("version")
                .build();

        // WHEN + THEN
        assertThatThrownBy(() -> MetaDataMapper.metaDatadtoToEntity(metadataDTO, 100L))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("metadataDTO.matchId cannot be null");
    }

    @Test
    @DisplayName("Should throw an exception if metadataDTO.dataVersion is null")
    void metaDatadtoToEntity_fail_4() {
        // GIVEN
        MetadataDTO metadataDTO = new MetadataDTO.Builder()
                .matchId("100")
                .build();

        // WHEN + THEN
        assertThatThrownBy(() -> MetaDataMapper.metaDatadtoToEntity(metadataDTO, 100L))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("metadataDTO.dataVersion cannot be null");
    }

    @Test
    void metaDataEntityToDTO_sucess() {
        // GIVEN
        MetaDataEntity metaDataEntity = new MetaDataEntity();
        metaDataEntity.setMatchId("125");
        metaDataEntity.setDataVersion("version");

        // WHEN
        MetadataDTO result = MetaDataMapper.metaDataEntityToDTO(metaDataEntity);

        // THEN
        assertThat(result).isNotNull().extracting("matchId", "dataVersion")
                .containsExactly("125", "version");
    }
}
