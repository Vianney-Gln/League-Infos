package com.league.league_infos.services.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.league.league_infos.dto.ia.GenerationCommentaryDTO;
import com.league.league_infos.dto.ia.OpenAiResponseDTO;

public interface IAService {
    OpenAiResponseDTO generateCommentaries(GenerationCommentaryDTO generationCommentaryDTO) throws JsonProcessingException;
}
