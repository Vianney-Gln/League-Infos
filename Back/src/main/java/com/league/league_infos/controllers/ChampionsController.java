package com.league.league_infos.controllers;

import com.league.league_infos.dto.FreeChampionsDTO;
import com.league.league_infos.dto.ddragon.ChampionDTO;
import com.league.league_infos.services.handler.MostRecentChampionHandler;
import com.league.league_infos.services.riot.RiotChampionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChampionsController {

    private final RiotChampionsService riotChampionsService;
    private final MostRecentChampionHandler mostRecentChampionHandler;

    @Autowired
    public ChampionsController(RiotChampionsService riotChampionsService, MostRecentChampionHandler mostRecentChampionHandler) {
        this.riotChampionsService = riotChampionsService;
        this.mostRecentChampionHandler = mostRecentChampionHandler;
    }

    @GetMapping("champions/free")
    public ResponseEntity<FreeChampionsDTO> getFreeChampions() {
        return ResponseEntity.ok(this.riotChampionsService.getFreeChampionsInfos());
    }

    @GetMapping("champions/mostRecent")
    public ResponseEntity<ChampionDTO> getMostrecentChampion() {
        return ResponseEntity.ok(this.mostRecentChampionHandler.getMostRecentChampion());
    }
}
