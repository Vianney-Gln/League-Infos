package com.league.league_infos.dto.ia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GenerationCommentaryDTOTest {

    @Test
    @DisplayName("should build GenerationCommentaryDTO")
    void build_success() {

        // GIVEN + WHEN
        GenerationCommentaryDTO generationCommentaryDTO = new GenerationCommentaryDTO.Builder()
                .eventsMatchDto(List.of(new EventMatchDTO.Builder()
                        .buildingType("TOWER_BUILDING")
                        .build()))
                .contextGenerationCommentaryDTO(new ContextGenerationCommentaryDTO.Builder()
                        .puuidFocusedPlayer("puuid focus")
                        .build())
                .build();

        // THEN
        assertThat(generationCommentaryDTO).isNotNull();
        assertThat(generationCommentaryDTO.getContextGenerationCommentaryDTO()).isNotNull();
        assertThat(generationCommentaryDTO.getContextGenerationCommentaryDTO().getPuuidFocusedPlayer()).isEqualTo("puuid focus");
        assertThat(generationCommentaryDTO.getEventsMatchDto())
                .isNotEmpty()
                .hasSize(1)
                .extracting("buildingType")
                .containsExactly("TOWER_BUILDING");
    }
}
