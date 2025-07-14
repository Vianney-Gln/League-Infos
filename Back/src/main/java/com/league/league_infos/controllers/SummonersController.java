package com.league.league_infos.controllers;

import com.league.league_infos.models.dto.SummonerDTO;
import com.league.league_infos.services.api.SummonersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SummonersController {

    private final SummonersService summonersService;

    public SummonersController(SummonersService summonersService) {
        this.summonersService = summonersService;
    }

    @GetMapping("summoner/{puuid}")
    public ResponseEntity<SummonerDTO> getSummonerByPuuid(@PathVariable String puuid) {
        return summonersService.getSummonerByPuuid(puuid);
    }
}
