package com.league.league_infos.controllers;

import com.league.league_infos.models.dto.match.MatchDTO;
import com.league.league_infos.services.api.HistoryGamesService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class HistoryGamesController {

    private final HistoryGamesService historyGamesService;

    @Autowired
    public HistoryGamesController(HistoryGamesService historyGamesService) {
        this.historyGamesService = historyGamesService;
    }

    @GetMapping("games-history/{puuid}")
    public ResponseEntity<List<MatchDTO>> getGamesHistory(@PathVariable String puuid, @Nullable @RequestParam Integer queue) {
        List<String> listGamesIds = historyGamesService.getHistoryIds(puuid, queue);

        if (listGamesIds != null) {
            List<MatchDTO> listMatchHistory = listGamesIds.stream().map(historyGamesService::getMatchHistory).toList();
            return ResponseEntity.status(200).body(listMatchHistory);
        }
        return ResponseEntity.status(204).body(Collections.emptyList());
    }
}
