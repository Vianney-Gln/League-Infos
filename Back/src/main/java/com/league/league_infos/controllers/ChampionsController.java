package com.league.league_infos.controllers;

import com.league.league_infos.models.dto.FreeChampionsDTO;
import com.league.league_infos.services.api.ChampionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChampionsController {

    private final ChampionsService championsService;

    @Autowired
    public ChampionsController(ChampionsService championsService) {
        this.championsService = championsService;
    }

    @GetMapping("champions/free")
    public ResponseEntity<FreeChampionsDTO> getFreeChampions() {
        return championsService.getFreeChampionsInfos();
    }
}
