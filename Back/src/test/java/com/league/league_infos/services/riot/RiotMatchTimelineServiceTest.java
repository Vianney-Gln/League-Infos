package com.league.league_infos.services.riot;

import com.league.league_infos.dto.ia.EventMatchDTO;
import com.league.league_infos.dto.ia.PositionEventDTO;
import com.league.league_infos.services.business.TimelineParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RiotMatchTimelineServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TimelineParser timelineParser;

    @InjectMocks
    private RiotMatchTimelineService riotMatchTimelineService;

    @Captor
    private ArgumentCaptor<InputStream> inputStreamArgumentCaptor;

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should call api Riot, not call timelineParser and return an empty list")
    void getMatchEventsByMatchId_success_1(String body) throws Exception {
        // GIVEN
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(String.class))).thenReturn(ResponseEntity.ok(body));

        // WHEN
        List<EventMatchDTO> result = riotMatchTimelineService.getMatchEventsByMatchId("12345_euw");

        // THEN
        verify(restTemplate, times(1)).exchange("https://europe.api.riotgames.com/lol/match/v5/matches/12345_euw/timeline", HttpMethod.GET, null, String.class);
        assertThat(result).isEmpty();
        verify(timelineParser, never()).parseTimeline(any());
    }

    @Test
    @DisplayName("Should call api Riot, timelineParser and return List<EventMatchDTO> ")
    void getMatchEventsByMatchId_success_2() throws Exception {
        // GIVEN
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(String.class))).thenReturn(ResponseEntity.ok("a big json"));
        when(timelineParser.parseTimeline(any(InputStream.class))).thenReturn(List.of(new EventMatchDTO.Builder()
                .buildingType("tower")
                .monsterType("monster type")
                .monsterSubType("monster sub")
                .dragonType("fire drake")
                .team("10")
                .assistingParticipantIds(List.of(1, 2, 3))
                .laneType("bot lane")
                .type("Kill")
                .position(new PositionEventDTO.Builder()
                        .x(125)
                        .y(200)
                        .build())
                .timestamp(75022456)
                .victimId(3)
                .killerId(1)
                .towerType("inner tower")
                .time("time")
                .build()));

        // WHEN
        List<EventMatchDTO> result = riotMatchTimelineService.getMatchEventsByMatchId("12345_euw");

        // THEN
        verify(restTemplate, times(1)).exchange("https://europe.api.riotgames.com/lol/match/v5/matches/12345_euw/timeline", HttpMethod.GET, null, String.class);
        assertThat(result).isNotEmpty().hasSize(1)
                .extracting("buildingType", "monsterType", "monsterSubType", "dragonType", "team", "laneType", "type",
                        "timestamp", "victimId", "killerId", "towerType", "time")
                .containsExactly(tuple("tower",
                        "monster type",
                        "monster sub",
                        "fire drake",
                        "10",
                        "bot lane",
                        "Kill",
                        75022456L,
                        3,
                        1,
                        "inner tower",
                        "time"));
        verify(timelineParser, times(1)).parseTimeline(inputStreamArgumentCaptor.capture());
        assertThat(inputStreamArgumentCaptor.getValue()).isNotEmpty();
    }
}
