package com.league.league_infos.dto.ia;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EventMatchDTOTest {

    @Test
    @DisplayName("should build EventMatchDTO")
    void build_success() {

        // GIVEN + WHEN
        EventMatchDTO eventMatchDTO = new EventMatchDTO.Builder()
                .buildingType("tower")
                .monsterType("monster type")
                .monsterSubType("monster sub")
                .dragonType("fire drake")
                .team("10")
                .assistingParticipantIds(List.of(1, 2, 3))
                .laneType("bot lane")
                .type("Kill")
                .position(new PositionEventDTO.Builder()
                        .x(125)
                        .y(200)
                        .build())
                .timestamp(75022456)
                .victimId(3)
                .killerId(1)
                .towerType("inner tower")
                .time("time")
                .build();

        // THEN
        assertThat(eventMatchDTO).isNotNull()
                .extracting("buildingType", "monsterType", "monsterSubType", "dragonType", "team", "laneType", "type",
                        "timestamp", "victimId", "killerId", "towerType", "time")
                .containsExactly("tower",
                        "monster type",
                        "monster sub",
                        "fire drake",
                        "10",
                        "bot lane",
                        "Kill",
                        75022456L,
                        3,
                        1,
                        "inner tower",
                        "time");

        assertThat(eventMatchDTO.getPosition()).isNotNull()
                .extracting("x", "Y")
                .containsExactly(125, 200);

        assertThat(eventMatchDTO.getAssistingParticipantIds()).isNotEmpty()
                .hasSize(3)
                .containsExactly(1, 2, 3);
    }
}
