package com.league.league_infos.dto.ia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PositionEventDTOTest {

    @Test
    @DisplayName("should build PositionEventDTO")
    void build_success() {
        // WHEN
        PositionEventDTO positionEventDTO = new PositionEventDTO.Builder()
                .x(1000)
                .y(800)
                .build();

        // THEN
        assertThat(positionEventDTO).isNotNull()
                .extracting("x", "y")
                .containsExactly(1000, 800);
    }
}
