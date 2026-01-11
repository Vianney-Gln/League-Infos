package com.league.league_infos.services.business;

import com.league.league_infos.common.utils.CurrentLocalDateTime;
import com.league.league_infos.dto.AccountDTO;
import com.league.league_infos.dto.match.MatchDTO;
import com.league.league_infos.dto.match.ParticipantMatchDTO;
import com.league.league_infos.services.api.AccountService;
import com.league.league_infos.services.api.HistoryGamesService;
import com.league.league_infos.services.spi.HistoryPersistence;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchDataProvider {
    private final HistoryGamesService historyRiotGamesService;
    private final HistoryPersistence historyPersistence;
    private final CurrentLocalDateTime currentLocalDateTime;
    private final AccountService riotAccountService;

    public MatchDataProvider(HistoryGamesService historyRiotGamesService, HistoryPersistence historyPersistence, CurrentLocalDateTime currentLocalDateTime,
                             AccountService riotAccountService) {
        this.historyRiotGamesService = historyRiotGamesService;
        this.historyPersistence = historyPersistence;
        this.currentLocalDateTime = currentLocalDateTime;
        this.riotAccountService = riotAccountService;
    }

    public List<MatchDTO> getMatchsHistory(String puuid, Integer queue) {
        List<MatchDTO> listMatchHistory = historyPersistence.findAllMatchByPuuidAndQueue(puuid, queue);
        if (listMatchHistory.isEmpty() || !wasResfreshedFromRiot(listMatchHistory)) {
            List<String> listGamesIds = historyRiotGamesService.getHistoryIds(puuid, queue);
            List<MatchDTO> listMatchToPersist = historyRiotGamesService.getMatchHistory(listGamesIds);
            updatePseudoFromFromCurrentParticipant(puuid, listMatchToPersist);
            historyPersistence.persistAndRefreshFromRiotMatchHistory(listMatchToPersist);
        }
        return listMatchHistory;
    }

    private boolean wasResfreshedFromRiot(List<MatchDTO> matchs) {
        return matchs.stream().anyMatch(match ->
                match.getInfo().getLastRefreshFromRiot() != null && match.getInfo().getLastRefreshFromRiot().isAfter(currentLocalDateTime.getCurrentLocalDateTime().minusHours(12)));
    }

    private void updatePseudoFromFromCurrentParticipant(String puuid, List<MatchDTO> listMatchToPersist) {
        AccountDTO accountDTO = riotAccountService.getAccountByPuuid(puuid);
        listMatchToPersist.forEach(match -> {
            ParticipantMatchDTO currentParticipant = match.getInfo().getParticipants().stream().filter(participant ->
                    participant.getPuuid().equals(puuid)).toList().getFirst();
            currentParticipant.setPseudo(accountDTO.getGameName());
        });
    }
}
