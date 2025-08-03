package com.league.league_infos.controllers;

import com.league.league_infos.dto.ChampionMasteryDTO;
import com.league.league_infos.dto.LeagueEntryDTO;
import com.league.league_infos.dto.LeagueListDTO;
import com.league.league_infos.services.riot.RiotPlayersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayersController {

    private final RiotPlayersService riotPlayersService;

    @Autowired
    public PlayersController(RiotPlayersService riotPlayersService) {
        this.riotPlayersService = riotPlayersService;
    }

    @GetMapping("league-challengers-solo-queue")
    public ResponseEntity<LeagueListDTO> getLeagueChallengerDataSoloQ() {

        return ResponseEntity.ok(riotPlayersService.getLeagueChallengerDataSoloQ());
    }

    @GetMapping("league-challengers-flex-queue")
    public ResponseEntity<LeagueListDTO> getLeagueChallengerDataFlexQ() {

        return ResponseEntity.ok(riotPlayersService.getLeagueChallengerDataFlexQ());
    }

    @GetMapping("league-entries-by-puuid/{puuid}")
    public ResponseEntity<List<LeagueEntryDTO>> getLeagueEntriesByPuuid(@PathVariable String puuid) {
        return ResponseEntity.ok(riotPlayersService.getLeagueEntriesByPuuid(puuid));
    }

    @GetMapping("champion-masteries/{puuid}/top")
    public ResponseEntity<List<ChampionMasteryDTO>> getChampionMasteriesByPuuid(@PathVariable String puuid) {
        return ResponseEntity.ok(riotPlayersService.getChampionMasteriesByPuuid(puuid));
    }

}
