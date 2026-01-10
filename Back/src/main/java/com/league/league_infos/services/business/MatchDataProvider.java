package com.league.league_infos.services.business;

import com.league.league_infos.common.utils.CurrentLocalDateTime;
import com.league.league_infos.dto.match.MatchDTO;
import com.league.league_infos.services.api.HistoryGamesService;
import com.league.league_infos.services.spi.HistoryPersistence;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchDataProvider {
    private final HistoryGamesService historyRiotGamesService;
    private final HistoryPersistence historyPersistence;
    private final CurrentLocalDateTime currentLocalDateTime;

    public MatchDataProvider(HistoryGamesService historyRiotGamesService, HistoryPersistence historyPersistence, CurrentLocalDateTime currentLocalDateTime) {
        this.historyRiotGamesService = historyRiotGamesService;
        this.historyPersistence = historyPersistence;
        this.currentLocalDateTime = currentLocalDateTime;
    }

    public List<MatchDTO> getMatchsHistory(String puuid, Integer queue) {
        List<MatchDTO> listMatchHistory = historyPersistence.findAllMatchByPuuidAndQueue(puuid, queue);
        if (!wasResfreshedFromRiot(listMatchHistory)) {
            List<String> listGamesIds = historyRiotGamesService.getHistoryIds(puuid, queue);
            historyRiotGamesService.getMatchHistory(listGamesIds);
        }
        return listMatchHistory;
    }

    private boolean wasResfreshedFromRiot(List<MatchDTO> matchs) {
        return matchs.stream().anyMatch(match ->
                match.getInfo().getLastRefreshFromRiot() != null && match.getInfo().getLastRefreshFromRiot().isAfter(currentLocalDateTime.getCurrentLocalDateTime().minusHours(12)));
    }
}
