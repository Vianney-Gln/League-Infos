package com.league.league_infos.dto.ia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParticipantGenerationCommentaryDTOTest {

    @Test
    @DisplayName("should build ParticipantGenerationCommentaryDTO")
    void build_success() {

        // GIVEN + WHEN
        ParticipantGenerationCommentaryDTO participantGenerationCommentaryDTO = new ParticipantGenerationCommentaryDTO.Builder()
                .puuid("puuid")
                .participantId(20)
                .assists(18)
                .deaths(3)
                .kills(11)
                .champion("Shyvana")
                .goldEarned(13225)
                .role("mid")
                .build();

        // THEN
        assertThat(participantGenerationCommentaryDTO).isNotNull()
                .extracting("puuid", "participantId", "assists", "deaths", "kills", "champion", "goldEarned", "role")
                .containsExactly("puuid", 20, 18, 3, 11, "Shyvana", 13225, "mid");
    }
}
