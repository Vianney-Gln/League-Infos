package com.league.league_infos.services.api;

import com.league.league_infos.dto.match.MatchDTO;

import java.util.List;

public interface HistoryGamesService {
    List<String> getHistoryIds(String puuid, Integer queue);

    MatchDTO getMatchHistory(String matchId);
}
