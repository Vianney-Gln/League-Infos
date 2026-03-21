package com.league.league_infos.dto.ia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommentaryMatchDTOTest {

    @Test
    @DisplayName("should build CommentaryMatchDTOT")
    void build_success() {
        // GIVEN + WHEN
        CommentaryMatchDTO commentaryMatchDTO = new CommentaryMatchDTO.Builder()
                .id("chatcmpl-D4OSM6AZyMkE6cvFVjopelXU6dRt0")
                .object("chat.completion")
                .created(123456L)
                .model("gpt-4.1-mini-2025-04-14")
                .serviceTier("default")
                .fingerPrint("fp_38699cb0fe")
                .content("content")
                .build();

        // THEN
        assertThat(commentaryMatchDTO).isNotNull().extracting("id",
                        "object",
                        "model",
                        "content",
                        "serviceTier",
                        "fingerPrint")
                .containsExactly("chatcmpl-D4OSM6AZyMkE6cvFVjopelXU6dRt0",
                        "chat.completion",
                        "gpt-4.1-mini-2025-04-14",
                        "content",
                        "default",
                        "fp_38699cb0fe");
        
    }
}
