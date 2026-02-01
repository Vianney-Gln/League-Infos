package com.league.league_infos.services.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.league.league_infos.dto.ia.GenerationCommentaryDTO;

public interface IAService {
    String generateCommentaries(GenerationCommentaryDTO generationCommentaryDTO) throws JsonProcessingException;
}
