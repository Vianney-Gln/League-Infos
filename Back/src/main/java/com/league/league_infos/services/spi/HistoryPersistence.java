package com.league.league_infos.services.spi;

import com.league.league_infos.dto.match.MatchDTO;

import java.util.List;

public interface HistoryPersistence {
    void persistAndRefreshFromRiotMatchHistory(List<MatchDTO> listMatch);

    List<MatchDTO> getMatchHistoryByGameIds(List<String> gameIds);

    List<MatchDTO> findAllMatchByPuuidAndQueue(String puuid, Integer queue);

    List<MatchDTO> findAllMatchByPuuidAndQueueBeforeGivenDate(String puuid, Long gameCreation, Integer queue);

    MatchDTO findMatchByGameId(Long gameId);
}
