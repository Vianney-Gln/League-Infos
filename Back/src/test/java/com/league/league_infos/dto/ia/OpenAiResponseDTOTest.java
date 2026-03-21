package com.league.league_infos.dto.ia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;


class OpenAiResponseDTOTest {

    @Test
    @DisplayName("should build OpenAiResponseDTO")
    void build_success() {
        // GIVEN + WHEN
        OpenAiResponseDTO openAiResponseDTO = new OpenAiResponseDTO.Builder()
                .id("chatcmpl-D4OSM6AZyMkE6cvFVjopelXU6dRt0")
                .object("chat.completion")
                .created(123456L)
                .model("gpt-4.1-mini-2025-04-14")
                .serviceTier("default")
                .fingerPrint("fp_38699cb0fe")
                .choices(List.of(new ChoiceDTO.Builder()
                        .index(0)
                        .message(new MessageDTO.Builder()
                                .content("Pun1sher Reborn, incarnant Cassiopeia, a livré une performance décisive au cœur de la midlane...")
                                .role("assistant")
                                .build())
                        .build()
                ))
                .build();

        // THEN
        assertThat(openAiResponseDTO).isNotNull().extracting("id",
                        "object",
                        "model",
                        "serviceTier",
                        "fingerPrint")
                .containsExactly("chatcmpl-D4OSM6AZyMkE6cvFVjopelXU6dRt0",
                        "chat.completion",
                        "gpt-4.1-mini-2025-04-14",
                        "default",
                        "fp_38699cb0fe");

        assertThat(openAiResponseDTO.getChoices())
                .isNotEmpty()
                .extracting(
                        "index",
                        "message.role",
                        "message.content"
                )
                .containsExactly(
                        tuple(
                                0,
                                "assistant",
                                "Pun1sher Reborn, incarnant Cassiopeia, a livré une performance décisive au cœur de la midlane...")
                );
    }
}
