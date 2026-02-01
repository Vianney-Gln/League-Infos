package com.league.league_infos.services.handler;

import com.league.league_infos.dto.ia.GenerationCommentaryDTO;
import com.league.league_infos.dto.ia.OpenAiResponseDTO;
import com.league.league_infos.services.api.GenerationCommentaryService;
import com.league.league_infos.services.api.IAService;
import org.springframework.stereotype.Service;

@Service
public class CommentaryHandler {

    private final IAService iAService;
    private final GenerationCommentaryService generationCommentaryService;

    public CommentaryHandler(IAService iAService, GenerationCommentaryService generationCommentaryService) {
        this.iAService = iAService;
        this.generationCommentaryService = generationCommentaryService;
    }

    public OpenAiResponseDTO getCommentaryFromAGameByPlayer(Long gameId, String puuid, String matchId, String pseudo) throws Exception {
        // Vérifier en Bdd si pour cette game et ce joueur, un commentaire existe déjà

        // s'il n'existe pas --> appel à openAi pour le générer et le persister.

        // s'il existe, simplement le retourner

        //  Dans un premier temps juste appeler openAi pour tester:
        GenerationCommentaryDTO generationCommentaryDTO = generationCommentaryService.getGenerationCommentaryDTO(gameId, puuid, matchId, pseudo);
        return iAService.generateCommentaries(generationCommentaryDTO);
    }
}
