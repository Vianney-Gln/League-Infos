package com.league.league_infos.dto.ia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ContextGenerationCommentaryDTOTest {

    @Test
    @DisplayName("should build ContextGenerationCommentaryDTO")
    void build_success() {

        // GIVEN + WHEN
        ContextGenerationCommentaryDTO contextGenerationCommentaryDTO = new ContextGenerationCommentaryDTO.Builder()
                .puuidFocusedPlayer("puuid")
                .pseudoFocusedPlayer("pseudo")
                .participantRequestCommentaries(List.of(new ParticipantGenerationCommentaryDTO.Builder()
                        .puuid("puuid participant")
                        .build())
                )
                .build();

        // THEN
        assertThat(contextGenerationCommentaryDTO).isNotNull()
                .extracting("puuidFocusedPlayer", "pseudoFocusedPlayer")
                .containsExactly("puuid", "pseudo");

        assertThat(contextGenerationCommentaryDTO.getParticipantRequestCommentaries()).isNotEmpty().hasSize(1)
                .extracting("puuid")
                .containsExactly("puuid participant");
    }
}
