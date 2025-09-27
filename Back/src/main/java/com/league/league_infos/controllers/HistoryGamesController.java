package com.league.league_infos.controllers;

import com.league.league_infos.dto.match.MatchDTO;
import com.league.league_infos.services.business.MatchDataProvider;
import com.league.league_infos.services.spi.HistoryPersistence;
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
    private final HistoryPersistence historyPersistence;

    public HistoryGamesController(MatchDataProvider matchDataProvider, HistoryPersistence historyPersistence) {
        this.matchDataProvider = matchDataProvider;
        this.historyPersistence = historyPersistence;
    }

    @GetMapping("games-history/{puuid}")
    public ResponseEntity<List<MatchDTO>> getGamesHistory(@PathVariable String puuid, @Nullable @RequestParam Integer queue) {
        return ResponseEntity.status(200).body(matchDataProvider.getMatchsHistory(puuid, queue));
    }

    @GetMapping("games-history-before-creation-date/{puuid}")
    public ResponseEntity<List<MatchDTO>> getGamesHistoryBeforeCreationDate(@PathVariable String puuid, @RequestParam Long gameCreation,
                                                                            @Nullable @RequestParam Integer queue) {
        return ResponseEntity.status(200).body(historyPersistence.findAllMatchByPuuidAndQueueBeforeGivenDate(puuid, gameCreation, queue));
    }
}
