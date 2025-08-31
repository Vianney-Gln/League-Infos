package com.league.league_infos.controllers;

import com.league.league_infos.dto.match.MatchDTO;
import com.league.league_infos.services.business.MatchDataProvider;
import jakarta.annotation.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HistoryGamesController {

    private final MatchDataProvider matchDataProvider;

    public HistoryGamesController(MatchDataProvider matchDataProvider) {
        this.matchDataProvider = matchDataProvider;
    }

    @GetMapping("games-history/{puuid}")
    public ResponseEntity<List<MatchDTO>> getGamesHistory(@PathVariable String puuid, @Nullable @RequestParam Integer queue) {
        return ResponseEntity.status(200).body(matchDataProvider.getMatchsHistory(puuid, queue));
    }
}
