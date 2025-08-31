package com.league.league_infos.services.api;

import java.util.List;

public interface HistoryGamesService {
    List<String> getHistoryIds(String puuid, Integer queue);

    void getMatchHistory(List<String> listMatchId);
}
