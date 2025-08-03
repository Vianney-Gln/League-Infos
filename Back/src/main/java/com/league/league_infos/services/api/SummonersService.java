package com.league.league_infos.services.api;

import com.league.league_infos.dto.SummonerDTO;

public interface SummonersService {
    public SummonerDTO getSummonerByPuuid(String puuid);
}
