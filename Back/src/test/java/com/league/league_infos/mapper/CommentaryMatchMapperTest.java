package com.league.league_infos.mapper;

import com.league.league_infos.dao.entity.CommentaryMatchEntity;
import com.league.league_infos.dto.ia.CommentaryMatchDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommentaryMatchMapperTest {

    @Test
    @DisplayName("should return null if input is null")
    void commentaryMatchEntityToCommentaryMatchDTO_success_1() {
        // GIVEN WHEN THEN
        assertThat(CommentaryMatchMapper.commentaryMatchEntityToCommentaryMatchDTO(null)).isNull();
    }

    @Test
    @DisplayName("should return map a commentaryMatchEntity into CommentaryMatchDTO successfully")
    void commentaryMatchEntityToCommentaryMatchDTO_success_2() {
        // GIVEN
        CommentaryMatchEntity commentaryMatchEntity = new CommentaryMatchEntity();
        commentaryMatchEntity.setCommentaryMatchId("10");
        commentaryMatchEntity.setObject("object");
        commentaryMatchEntity.setCreatedDate(177566887L);
        commentaryMatchEntity.setModel("model");
        commentaryMatchEntity.setContent("content");
        commentaryMatchEntity.setServiceTier("service tier");
        commentaryMatchEntity.setFingerPrint("fingerprint");

        // WHEN
        CommentaryMatchDTO result = CommentaryMatchMapper.commentaryMatchEntityToCommentaryMatchDTO(commentaryMatchEntity);

        // THEN
        assertThat(result).isNotNull()
                .extracting("id", "object", "created", "model", "content", "serviceTier", "fingerPrint")
                .containsExactly("10", "object", 177566887L, "model", "content", "service tier", "fingerprint");
    }

    @Test
    @DisplayName("should return null if input is null")
    void commentaryMatchDTOToCommentaryMatchEntity_success_1() {
        // GIVEN WHEN THEN
        assertThat(CommentaryMatchMapper.commentaryMatchDTOToCommentaryMatchEntity(null)).isNull();
    }

    @Test
    @DisplayName("should return map a CommentaryMatchDTO into commentaryMatchEntity successfully")
    void commentaryMatchDTOToCommentaryMatchEntity_success_2() {
        // GIVEN
        CommentaryMatchDTO commentaryMatchDTO = new CommentaryMatchDTO.Builder()
                .id("10")
                .created(1762005666L)
                .model("model")
                .content("content")
                .object("object")
                .serviceTier("serviceTier")
                .fingerPrint("fingerPrint")
                .build();

        // WHEN
        CommentaryMatchEntity result = CommentaryMatchMapper.commentaryMatchDTOToCommentaryMatchEntity(commentaryMatchDTO);

        // THEN
        assertThat(result).isNotNull()
                .extracting("commentaryMatchId", "object", "createdDate", "model", "content", "serviceTier", "fingerPrint")
                .containsExactly("10",
                        "object",
                        1762005666L,
                        "model",
                        "content",
                        "serviceTier",
                        "fingerPrint");
    }
}
