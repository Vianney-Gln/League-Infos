package com.league.league_infos.controllers;

import com.league.league_infos.dto.match.MatchDTO;
import com.league.league_infos.services.riot.RiotHistoryGamesService;
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

    private final RiotHistoryGamesService riotHistoryGamesService;

    @Autowired
    public HistoryGamesController(RiotHistoryGamesService riotHistoryGamesService) {
        this.riotHistoryGamesService = riotHistoryGamesService;
    }

    @GetMapping("games-history/{puuid}")
    public ResponseEntity<List<MatchDTO>> getGamesHistory(@PathVariable String puuid, @Nullable @RequestParam Integer queue) {
        List<String> listGamesIds = riotHistoryGamesService.getHistoryIds(puuid, queue);

        if (listGamesIds != null) {
            List<MatchDTO> listMatchHistory = listGamesIds.stream().map(riotHistoryGamesService::getMatchHistory).toList();
            return ResponseEntity.status(200).body(listMatchHistory);
        }
        return ResponseEntity.status(204).body(Collections.emptyList());
    }
}
