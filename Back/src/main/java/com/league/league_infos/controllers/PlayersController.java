package com.league.league_infos.controllers;

import com.league.league_infos.models.dto.ChampionMasteryDTO;
import com.league.league_infos.models.dto.LeagueEntryDTO;
import com.league.league_infos.models.dto.LeagueListDTO;
import com.league.league_infos.services.PlayersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayersController {

    private final PlayersService playersService;

    @Autowired
    public PlayersController(PlayersService playersService) {
        this.playersService = playersService;
    }

    @GetMapping("league-challengers-solo-queue")
    public ResponseEntity<LeagueListDTO> getLeagueChallengerDataSoloQ() {
        return playersService.getLeagueChallengerDataSoloQ();
    }

    @GetMapping("league-challengers-flex-queue")
    public ResponseEntity<LeagueListDTO> getLeagueChallengerDataFlexQ() {
        return playersService.getLeagueChallengerDataFlexQ();
    }

    @GetMapping("league-entries-by-puuid/{puuid}")
    public ResponseEntity<List<LeagueEntryDTO>> getLeagueEntriesByPuuid(@PathVariable String puuid) {
        return playersService.getLeagueEntriesByPuuid(puuid);
    }

    @GetMapping("champion-masteries/{puuid}/top")
    public ResponseEntity<List<ChampionMasteryDTO>> getChampionMasteriesByPuuid(@PathVariable String puuid) {
        return playersService.getChampionMasteriesByPuuid(puuid);
    }

}
