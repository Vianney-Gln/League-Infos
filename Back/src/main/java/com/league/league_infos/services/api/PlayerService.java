package com.league.league_infos.services.api;

import com.league.league_infos.dto.ChampionMasteryDTO;
import com.league.league_infos.dto.LeagueEntryDTO;
import com.league.league_infos.dto.LeagueListDTO;

import java.util.List;

public interface PlayerService {
    LeagueListDTO getLeagueChallengerDataSoloQ();

    LeagueListDTO getLeagueChallengerDataFlexQ();

    List<LeagueEntryDTO> getLeagueEntriesByPuuid(String puuid);

    List<ChampionMasteryDTO> getChampionMasteriesByPuuid(String puuid);
}
