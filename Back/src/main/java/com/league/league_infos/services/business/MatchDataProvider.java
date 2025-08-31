package com.league.league_infos.services.business;

import com.league.league_infos.dto.match.MatchDTO;
import com.league.league_infos.services.api.HistoryGamesService;
import com.league.league_infos.services.spi.HistoryPersistence;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchDataProvider {
    private final HistoryGamesService historyGamesService;
    private final HistoryPersistence historyPersistence;

    public MatchDataProvider(HistoryGamesService historyGamesService, HistoryPersistence historyPersistence) {
        this.historyGamesService = historyGamesService;
        this.historyPersistence = historyPersistence;
    }

    public List<MatchDTO> getMatchsHistory(String puuid, Integer queue) {
        List<String> listGamesIds = historyGamesService.getHistoryIds(puuid, queue);
        List<MatchDTO> listMatchHistory = new ArrayList<>();

        if (listGamesIds != null && !listGamesIds.isEmpty()) {
            if (!wasCreatedWithinLastHour(puuid, queue)) {
                historyGamesService.getMatchHistory(listGamesIds);
            }
            listMatchHistory = historyPersistence.getMatchHistoryByGameIds(listGamesIds);
        }
        return listMatchHistory;
    }

    private boolean wasCreatedWithinLastHour(String puuid, Integer queue) {
        return historyPersistence.wasCreatedWithinLastHour(puuid, queue);
    }
}
