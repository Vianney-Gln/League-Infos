package com.league.league_infos.services.business;

import com.league.league_infos.common.utils.CurrentLocalDateTime;
import com.league.league_infos.dto.match.InfoMatchDTO;
import com.league.league_infos.dto.match.MatchDTO;
import com.league.league_infos.services.api.HistoryGamesService;
import com.league.league_infos.services.spi.HistoryPersistence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchDataProviderTest {

    @Mock
    private HistoryGamesService historyGamesService;

    @Mock
    private HistoryPersistence historyPersistence;

    @Mock
    private CurrentLocalDateTime currentLocalDateTime;

    @InjectMocks
    private MatchDataProvider matchDataProvider;

    @Test
    @DisplayName("Should call historyGamesService.getHistoryIds")
    void getMatchsHistory_success_1() {

        // WHEN
        matchDataProvider.getMatchsHistory("puuid", 420);

        // THEN
        verify(historyGamesService, times(1)).getHistoryIds("puuid", 420);
    }

    @Test
    @DisplayName("Should call historyPersistence.getMatchHistoryByGameIds and findAllMatchByPuuidAndQueue " +
            "do not call historyGamesService.getMatchHistory" +
            "do not call historyGamesService.getHistoryIds" +
            "case lastRefreshFromRiot < 12h"
    )
    void getMatchsHistory_success_2() {
        // GIVEN
        MatchDTO matchDTO = new MatchDTO.Builder()
                .info(new InfoMatchDTO.Builder()
                        .lastRefreshFromRiot(LocalDateTime.of(2025, 10, 9, 20, 13, 0))
                        .build())
                .build();

        when(historyPersistence.findAllMatchByPuuidAndQueue(anyString(), any(Integer.class))).thenReturn(List.of(matchDTO));
        when(currentLocalDateTime.getCurrentLocalDateTime()).thenReturn(LocalDateTime.of(2025, 10, 10, 0, 0, 0));

        // WHEN
        List<MatchDTO> result = matchDataProvider.getMatchsHistory("puuid", 420);

        // THEN
        verify(historyPersistence, times(1)).findAllMatchByPuuidAndQueue("puuid", 420);
        verify(historyGamesService, never()).getMatchHistory(any());
        verify(historyGamesService, never()).getHistoryIds(any(), any());
        assertThat(result).isNotEmpty().hasSize(1);
    }

    @Test
    @DisplayName("Should call historyGamesService.getHistoryIds, historyPersistence.getMatchHistoryByGameIds and findAllMatchByPuuidAndQueue  " +
            " historyGamesService.getMatchHistory case lastRefreshFromRiot > 12h")
    void getMatchsHistory_success_3() {

        // GIVEN
        MatchDTO matchDTO = new MatchDTO.Builder()
                .info(new InfoMatchDTO.Builder()
                        .lastRefreshFromRiot(LocalDateTime.of(2025, 8, 1, 10, 8, 14))
                        .build())
                .build();

        when(historyGamesService.getHistoryIds(anyString(), any(Integer.class))).thenReturn(List.of("1", "2"));
        when(historyPersistence.findAllMatchByPuuidAndQueue(anyString(), any(Integer.class))).thenReturn(List.of(matchDTO));
        when(currentLocalDateTime.getCurrentLocalDateTime()).thenReturn(LocalDateTime.of(2025, 10, 10, 0, 0, 0));

        // WHEN
        List<MatchDTO> result = matchDataProvider.getMatchsHistory("puuid", 420);

        // THEN
        verify(historyPersistence, times(1)).findAllMatchByPuuidAndQueue("puuid", 420);
        verify(historyGamesService, times(1)).getMatchHistory(List.of("1", "2"));
        verify(historyGamesService, times(1)).getHistoryIds("puuid", 420);
        assertThat(result).isNotEmpty().hasSize(1);
    }

    @Test
    @DisplayName("Should return an empty list if listGameids is null")
    void getMatchsHistory_success_4() {

        // GIVEN
        when(historyGamesService.getHistoryIds(anyString(), any(Integer.class))).thenReturn(null);

        // WHEN
        List<MatchDTO> result = matchDataProvider.getMatchsHistory("puuid", 420);

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return an empty list if listGameids is empty")
    void getMatchsHistory_success_5() {

        // GIVEN
        when(historyGamesService.getHistoryIds(anyString(), any(Integer.class))).thenReturn(Collections.emptyList());

        // WHEN
        List<MatchDTO> result = matchDataProvider.getMatchsHistory("puuid", 420);

        // THEN
        assertThat(result).isEmpty();
    }
}
