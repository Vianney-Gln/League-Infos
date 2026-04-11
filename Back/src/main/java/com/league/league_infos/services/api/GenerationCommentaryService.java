package com.league.league_infos.services.api;

import com.league.league_infos.dto.ia.GenerationCommentaryDTO;

public interface GenerationCommentaryService {
    GenerationCommentaryDTO getGenerationCommentaryDTO(Long gameId, String puuid, String matchId, String pseudo) throws Exception;
}
