package com.league.league_infos.controllers;

import com.league.league_infos.dto.SummonerDTO;
import com.league.league_infos.services.riot.RiotSummonersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SummonersController {

    private final RiotSummonersService riotSummonersService;

    public SummonersController(RiotSummonersService riotSummonersService) {
        this.riotSummonersService = riotSummonersService;
    }

    @GetMapping("summoner/{puuid}")
    public ResponseEntity<SummonerDTO> getSummonerByPuuid(@PathVariable String puuid) {
        return ResponseEntity.ok(riotSummonersService.getSummonerByPuuid(puuid));
    }
}
