package com.league.league_infos.services.business;

import com.league.league_infos.dto.match.MatchDTO;
import com.league.league_infos.services.api.HistoryGamesService;
import com.league.league_infos.services.spi.HistoryPersistence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @InjectMocks
    private MatchDataProvider matchDataProvider;

    @Test
    @DisplayName("Must call historyGamesService.getHistoryIds")
    void getMatchsHistory_success_1() {

        // WHEN
        matchDataProvider.getMatchsHistory("puuid", 420);

        // THEN
        verify(historyGamesService, times(1)).getHistoryIds("puuid", 420);
    }

    @Test
    @DisplayName("Must call historyPersistence.getMatchHistoryByGameIds and do not call historyGamesService.getMatchHistory")
    void getMatchsHistory_success_2() {

        // GIVEN
        when(historyGamesService.getHistoryIds(anyString(), any(Integer.class))).thenReturn(List.of("1", "2"));
        when(historyPersistence.wasCreatedWithinLastHour(anyString(), any(Integer.class))).thenReturn(true);
        when(historyPersistence.findAllMatchByPuuidAndQueue(anyString(), any(Integer.class))).thenReturn(List.of(new MatchDTO()));

        // WHEN
        List<MatchDTO> result = matchDataProvider.getMatchsHistory("puuid", 420);

        // THEN
        verify(historyPersistence, times(1)).findAllMatchByPuuidAndQueue("puuid", 420);
        verify(historyGamesService, never()).getMatchHistory(any());

        assertThat(result).isNotEmpty().hasSize(1);
    }

    @Test
    @DisplayName("Must call historyPersistence.getMatchHistoryByGameIds and call historyGamesService.getMatchHistory")
    void getMatchsHistory_success_3() {

        // GIVEN
        when(historyGamesService.getHistoryIds(anyString(), any(Integer.class))).thenReturn(List.of("1", "2"));
        when(historyPersistence.wasCreatedWithinLastHour(anyString(), any(Integer.class))).thenReturn(false);
        when(historyPersistence.findAllMatchByPuuidAndQueue(anyString(), any(Integer.class))).thenReturn(List.of(new MatchDTO()));

        // WHEN
        List<MatchDTO> result = matchDataProvider.getMatchsHistory("puuid", 420);

        // THEN
        verify(historyPersistence, times(1)).findAllMatchByPuuidAndQueue("puuid", 420);
        verify(historyGamesService, times(1)).getMatchHistory(List.of("1", "2"));

        assertThat(result).isNotEmpty().hasSize(1);
    }

    @Test
    @DisplayName("Must return an empty list if listGameids is null")
    void getMatchsHistory_success_4() {

        // GIVEN
        when(historyGamesService.getHistoryIds(anyString(), any(Integer.class))).thenReturn(null);

        // WHEN
        List<MatchDTO> result = matchDataProvider.getMatchsHistory("puuid", 420);

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Must return an empty list if listGameids is empty")
    void getMatchsHistory_success_5() {

        // GIVEN
        when(historyGamesService.getHistoryIds(anyString(), any(Integer.class))).thenReturn(Collections.emptyList());

        // WHEN
        List<MatchDTO> result = matchDataProvider.getMatchsHistory("puuid", 420);

        // THEN
        assertThat(result).isEmpty();
    }
}
