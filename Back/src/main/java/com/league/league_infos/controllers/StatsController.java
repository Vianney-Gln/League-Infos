package com.league.league_infos.controllers;

import com.league.league_infos.services.business.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController {
    private final StatsService statsService;

    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping(value = "stats/most-banned-champion", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> getMostBannedChampions() {
        return new ResponseEntity<>(statsService.calculateMostBannedChampion(), HttpStatus.OK);
    }
}
