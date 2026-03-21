package com.league.league_infos.services.handler;

import com.league.league_infos.dto.ia.CommentaryMatchDTO;
import com.league.league_infos.dto.ia.GenerationCommentaryDTO;
import com.league.league_infos.dto.ia.OpenAiResponseDTO;
import com.league.league_infos.mapper.CommentaryMatchMapper;
import com.league.league_infos.services.api.GenerationCommentaryService;
import com.league.league_infos.services.api.IAService;
import com.league.league_infos.services.spi.CommentaryMatchPersistence;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentaryHandler {

    private final IAService iAService;
    private final GenerationCommentaryService generationCommentaryService;
    private final CommentaryMatchPersistence commentaryMatchPersistence;

    public CommentaryHandler(IAService iAService, GenerationCommentaryService generationCommentaryService, CommentaryMatchPersistence commentaryMatchPersistence) {
        this.iAService = iAService;
        this.generationCommentaryService = generationCommentaryService;
        this.commentaryMatchPersistence = commentaryMatchPersistence;
    }

    public CommentaryMatchDTO getCommentaryFromAGameByPlayer(Long gameId, String puuid, String matchId, String pseudo) throws Exception {
        Optional<CommentaryMatchDTO> commentaryMatchDTO = commentaryMatchPersistence.getCommentaryByGameIdAndPuuid(gameId, puuid);
        return commentaryMatchDTO.isPresent() ? commentaryMatchDTO.get() : generateAndPersistCommentary(gameId, puuid, matchId, pseudo);
    }

    private CommentaryMatchDTO generateAndPersistCommentary(Long gameId, String puuid, String matchId, String pseudo) throws Exception {
        GenerationCommentaryDTO generationCommentaryDTO = generationCommentaryService.getGenerationCommentaryDTO(gameId, puuid, matchId, pseudo);
        OpenAiResponseDTO openAiResponseDTO = iAService.generateCommentaries(generationCommentaryDTO);
        return commentaryMatchPersistence.persist(CommentaryMatchMapper.openAiResponseDTOToCommentaryMatchDTO(openAiResponseDTO), gameId, puuid);
    }
}
