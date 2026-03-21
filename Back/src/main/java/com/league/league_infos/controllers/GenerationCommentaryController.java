package com.league.league_infos.controllers;

import com.league.league_infos.dto.ia.OpenAiResponseDTO;
import com.league.league_infos.services.handler.CommentaryHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerationCommentaryController {

    private final CommentaryHandler commentaryHandler;

    public GenerationCommentaryController(CommentaryHandler commentaryHandler) {
        this.commentaryHandler = commentaryHandler;
    }

    @GetMapping("matchCommentary/{matchId}")
    public ResponseEntity<OpenAiResponseDTO> getCommentary(@PathVariable String matchId, @RequestParam Long gameId, @RequestParam String puuid, @RequestParam String pseudo) throws Exception {
        return ResponseEntity.status(200).body(commentaryHandler.getCommentaryFromAGameByPlayer(gameId, puuid, matchId, pseudo));
    }
}
