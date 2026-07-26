package com.league.league_infos.services.api;

import com.league.league_infos.dto.ddragon.ChampionDataDTO;

import java.util.List;

public interface DataDragonService {
    List<String> getAllNumVersionsLol();

    ChampionDataDTO getChampionsLol(String numVersion);
}
