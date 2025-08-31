package com.league.league_infos.services.spi;

import com.league.league_infos.dto.match.MatchDTO;

import java.util.List;

public interface HistoryPersistence {
    void persistMatchHistory(List<MatchDTO> listMatch);

    List<MatchDTO> getMatchHistoryByGameIds(List<String> gameIds);

    boolean wasCreatedWithinLastHour(String puuid, Integer queue);
}
