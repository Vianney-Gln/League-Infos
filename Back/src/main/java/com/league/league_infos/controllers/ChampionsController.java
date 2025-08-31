package com.league.league_infos.controllers;

import com.league.league_infos.dto.FreeChampionsDTO;
import com.league.league_infos.services.riot.RiotChampionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChampionsController {

    private final RiotChampionsService riotChampionsService;

    @Autowired
    public ChampionsController(RiotChampionsService riotChampionsService) {
        this.riotChampionsService = riotChampionsService;
    }

    @GetMapping("champions/free")
    public ResponseEntity<FreeChampionsDTO> getFreeChampions() {
        return ResponseEntity.ok(riotChampionsService.getFreeChampionsInfos());
    }
}
