package com.league.league_infos.controllers;

import com.league.league_infos.dto.ia.GenerationCommentaryDTO;
import com.league.league_infos.services.api.GenerationCommentaryService;
import com.league.league_infos.services.handler.CommentaryHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class fakeController {

    private final GenerationCommentaryService generationCommentaryService;
    private final CommentaryHandler commentaryHandler;

    public fakeController(GenerationCommentaryService generationCommentaryService, CommentaryHandler commentaryHandler) {
        this.generationCommentaryService = generationCommentaryService;
        this.commentaryHandler = commentaryHandler;
    }

    @GetMapping("matchEvents/{matchId}")
    public ResponseEntity<GenerationCommentaryDTO> test(@PathVariable String matchId, @RequestParam Long gameId, @RequestParam String puuid, @RequestParam String pseudo) throws Exception {
        return ResponseEntity.status(200).body(generationCommentaryService.getGenerationCommentaryDTO(gameId, puuid, matchId, pseudo));
    }

    @GetMapping("matchCommentary/{matchId}")
    public ResponseEntity<String> getCommentary(@PathVariable String matchId, @RequestParam Long gameId, @RequestParam String puuid, @RequestParam String pseudo) throws Exception {
        return ResponseEntity.status(200).body(commentaryHandler.getCommentaryFromAGameByPlayer(gameId, puuid, matchId, pseudo));
    }
}
