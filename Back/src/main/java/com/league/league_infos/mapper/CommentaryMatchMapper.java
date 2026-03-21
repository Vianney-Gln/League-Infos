package com.league.league_infos.mapper;

import com.league.league_infos.dao.entity.CommentaryMatchEntity;
import com.league.league_infos.dto.ia.CommentaryMatchDTO;
import com.league.league_infos.dto.ia.OpenAiResponseDTO;

public class CommentaryMatchMapper {

    public static CommentaryMatchDTO commentaryMatchEntityToCommentaryMatchDTO(CommentaryMatchEntity commentaryMatchEntity) {

        if (commentaryMatchEntity == null) {
            return null;
        }

        return new CommentaryMatchDTO.Builder()
                .id(commentaryMatchEntity.getCommentaryMatchId())
                .object(commentaryMatchEntity.getObject())
                .created(commentaryMatchEntity.getCreatedDate())
                .model(commentaryMatchEntity.getModel())
                .content(commentaryMatchEntity.getContent())
                .fingerPrint(commentaryMatchEntity.getFingerPrint())
                .serviceTier(commentaryMatchEntity.getServiceTier())
                .build();
    }

    public static CommentaryMatchEntity commentaryMatchDTOToCommentaryMatchEntity(CommentaryMatchDTO commentaryMatchDTO) {

        if (commentaryMatchDTO == null) {
            return null;
        }

        CommentaryMatchEntity commentaryMatchEntity = new CommentaryMatchEntity();
        commentaryMatchEntity.setCommentaryMatchId(commentaryMatchDTO.getId());
        commentaryMatchEntity.setObject(commentaryMatchDTO.getObject());
        commentaryMatchEntity.setCreatedDate(commentaryMatchDTO.getCreated());
        commentaryMatchEntity.setContent(commentaryMatchDTO.getContent());
        commentaryMatchEntity.setModel(commentaryMatchDTO.getModel());
        commentaryMatchEntity.setServiceTier(commentaryMatchDTO.getServiceTier());
        commentaryMatchEntity.setFingerPrint(commentaryMatchDTO.getFingerPrint());
        return commentaryMatchEntity;
    }

    public static CommentaryMatchDTO openAiResponseDTOToCommentaryMatchDTO(OpenAiResponseDTO openAiResponseDTO) {

        if (openAiResponseDTO == null) {
            return null;
        }

        String content = null;

        if (!openAiResponseDTO.getChoices().isEmpty() && openAiResponseDTO.getChoices().getFirst() != null && openAiResponseDTO.getChoices().getFirst().getMessage() != null) {
            content = openAiResponseDTO.getChoices().getFirst().getMessage().getContent();
        }

        return new CommentaryMatchDTO.Builder()
                .id(openAiResponseDTO.getId())
                .object(openAiResponseDTO.getObject())
                .created(openAiResponseDTO.getCreated())
                .model(openAiResponseDTO.getModel())
                .content(content)
                .fingerPrint(openAiResponseDTO.getFingerPrint())
                .serviceTier(openAiResponseDTO.getServiceTier())
                .build();
    }
}
