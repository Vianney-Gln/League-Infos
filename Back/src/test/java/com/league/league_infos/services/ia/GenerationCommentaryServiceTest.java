package com.league.league_infos.services.ia;

import com.league.league_infos.common.enums.ObjectivesEnum;
import com.league.league_infos.dto.ia.EventMatchDTO;
import com.league.league_infos.dto.ia.GenerationCommentaryDTO;
import com.league.league_infos.dto.ia.PositionEventDTO;
import com.league.league_infos.dto.match.InfoMatchDTO;
import com.league.league_infos.dto.match.MatchDTO;
import com.league.league_infos.dto.match.ParticipantMatchDTO;
import com.league.league_infos.services.api.MatchTimelineService;
import com.league.league_infos.services.spi.HistoryPersistence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerationCommentaryServiceTest {

    @Mock
    private MatchTimelineService matchTimelineService;

    @Mock
    private HistoryPersistence historyPersistence;

    @InjectMocks
    private GenerationCommentaryServiceImpl generationCommentaryServiceImpl;

    @ParameterizedTest
    @EnumSource(value = ObjectivesEnum.class, names = {"INNER_TURRET", "OUTER_TURRET", "BASE_TURRET", "NEXUS_TURRET"})
    @DisplayName("getGenerationCommentaryDTO should call historyPersistence.findMatchByGameId to get match's context , participants ect..." +
            "call matchTimelineService.getMatchEventsByMatchId to get game's event timeline" +
            "and format data correctly, case Kill tower event")
    void getGenerationCommentaryDTO_success_1(ObjectivesEnum towerEvent) throws Exception {
        // GIVEN
        when(matchTimelineService.getMatchEventsByMatchId(anyString())).thenReturn(List.of(
                new EventMatchDTO.Builder()
                        .buildingType("TOWER_BUILDING")
                        .team("10")
                        .assistingParticipantIds(List.of(1, 2, 3))
                        .laneType("bot lane")
                        .type("BUILDING_KILL")
                        .position(new PositionEventDTO.Builder()
                                .x(125)
                                .y(200)
                                .build())
                        .timestamp(3502245)
                        .victimId(3)
                        .killerId(1)
                        .towerType(towerEvent.getCode())
                        .time("20:30 min")
                        .build())
        );

        when(historyPersistence.findMatchByGameId(anyLong())).thenReturn(new MatchDTO.Builder()
                .info(new InfoMatchDTO.Builder()
                        .participants(List.of(
                                new ParticipantMatchDTO.Builder()
                                        .puuid("puuid from one participant")
                                        .kills(8)
                                        .assists(9)
                                        .deaths(2)
                                        .goldEarned(11522)
                                        .participantId(10)
                                        .teamPosition("mid")
                                        .championName("Garen")
                                        .win(true)
                                        .build()
                        ))
                        .build())
                .build());

        // WHEN
        GenerationCommentaryDTO result = generationCommentaryServiceImpl.getGenerationCommentaryDTO(1234L, "puuid", "1234_euw", "pseudo");

        // THEN
        verify(matchTimelineService, times(1)).getMatchEventsByMatchId("1234_euw");
        verify(historyPersistence, times(1)).findMatchByGameId(1234L);

        assertThat(result).isNotNull();
        assertThat(result.getEventsMatchDto()).isNotEmpty().hasSize(1)
                .extracting("buildingType", "monsterType", "monsterSubType", "dragonType", "team", "laneType", "type",
                        "timestamp", "victimId", "killerId", "towerType", "time")
                .containsExactly(tuple("TOWER_BUILDING", null, null, null, "10", "bot lane", "BUILDING_KILL", 3502245L, 3, 1, towerEvent.getLibelle(), "58:22 min"));

        assertThat(result.getContextGenerationCommentaryDTO()).isNotNull()
                .extracting("puuidFocusedPlayer", "pseudoFocusedPlayer")
                .containsExactly("puuid", "pseudo");

        assertThat(result.getContextGenerationCommentaryDTO().getParticipants())
                .isNotEmpty()
                .hasSize(1)
                .extracting("puuid", "kills", "deaths", "assists", "goldEarned", "participantId", "role", "championName", "win")
                .containsExactly(tuple("puuid from one participant", 8, 2, 9, 11522, 10, "mid", "Garen", true));
    }

    @Test
    @DisplayName("getGenerationCommentaryDTO should call historyPersistence.findMatchByGameId to get match's context , participants ect..." +
            "call matchTimelineService.getMatchEventsByMatchId to get game's event timeline" +
            "and format data correctly, case Kill inhibitor event")
    void getGenerationCommentaryDTO_success_2() throws Exception {
        // GIVEN
        when(matchTimelineService.getMatchEventsByMatchId(anyString())).thenReturn(List.of(
                new EventMatchDTO.Builder()
                        .buildingType("INHIBITOR_BUILDING")
                        .team("10")
                        .assistingParticipantIds(List.of(1, 2, 3))
                        .laneType("bot lane")
                        .type("BUILDING_KILL")
                        .position(new PositionEventDTO.Builder()
                                .x(125)
                                .y(200)
                                .build())
                        .timestamp(3502245)
                        .victimId(3)
                        .killerId(1)
                        .time("20:30 min")
                        .build())
        );

        when(historyPersistence.findMatchByGameId(anyLong())).thenReturn(new MatchDTO.Builder()
                .info(new InfoMatchDTO.Builder()
                        .participants(List.of(
                                new ParticipantMatchDTO.Builder()
                                        .puuid("puuid from one participant")
                                        .kills(8)
                                        .assists(9)
                                        .deaths(2)
                                        .goldEarned(11522)
                                        .participantId(10)
                                        .teamPosition("mid")
                                        .championName("Garen")
                                        .win(true)
                                        .build()
                        ))
                        .build())
                .build());

        // WHEN
        GenerationCommentaryDTO result = generationCommentaryServiceImpl.getGenerationCommentaryDTO(1234L, "puuid", "1234_euw", "pseudo");

        // THEN
        verify(matchTimelineService, times(1)).getMatchEventsByMatchId("1234_euw");
        verify(historyPersistence, times(1)).findMatchByGameId(1234L);

        assertThat(result).isNotNull();
        assertThat(result.getEventsMatchDto()).isNotEmpty().hasSize(1)
                .extracting("buildingType", "monsterType", "monsterSubType", "dragonType", "team", "laneType", "type",
                        "timestamp", "victimId", "killerId", "towerType", "time")
                .containsExactly(tuple("INHIBITOR_BUILDING", null, null, null, "10", "bot lane", "BUILDING_KILL", 3502245L, 3, 1, "Inhibiteur", "58:22 min"));

        assertThat(result.getContextGenerationCommentaryDTO()).isNotNull()
                .extracting("puuidFocusedPlayer", "pseudoFocusedPlayer")
                .containsExactly("puuid", "pseudo");

        assertThat(result.getContextGenerationCommentaryDTO().getParticipants())
                .isNotEmpty()
                .hasSize(1)
                .extracting("puuid", "kills", "deaths", "assists", "goldEarned", "participantId", "role", "championName", "win")
                .containsExactly(tuple("puuid from one participant", 8, 2, 9, 11522, 10, "mid", "Garen", true));
    }

    @ParameterizedTest
    @EnumSource(value = ObjectivesEnum.class, names = {"FIRE_DRAGON", "WATER_DRAGON", "AIR_DRAGON", "EARTH_DRAGON", "HEXTECH_DRAGON", "CHEMTECH_DRAGON", "ELDER_DRAGON"})
    @DisplayName("getGenerationCommentaryDTO should call historyPersistence.findMatchByGameId to get match's context , participants ect..." +
            "call matchTimelineService.getMatchEventsByMatchId to get game's event timeline" +
            "and format data correctly, case Dragons Kill events")
    void getGenerationCommentaryDTO_success_3(ObjectivesEnum dragon) throws Exception {
        // GIVEN
        when(matchTimelineService.getMatchEventsByMatchId(anyString())).thenReturn(List.of(
                new EventMatchDTO.Builder()
                        .team("10")
                        .assistingParticipantIds(List.of(1, 2, 3))
                        .laneType("bot lane")
                        .type("ELITE_MONSTER_KILL")
                        .monsterType("DRAGON")
                        .monsterSubType(dragon.getCode())
                        .position(new PositionEventDTO.Builder()
                                .x(125)
                                .y(200)
                                .build())
                        .timestamp(3502245)
                        .victimId(3)
                        .killerId(1)
                        .time("20:30 min")
                        .build())
        );

        when(historyPersistence.findMatchByGameId(anyLong())).thenReturn(new MatchDTO.Builder()
                .info(new InfoMatchDTO.Builder()
                        .participants(List.of(
                                new ParticipantMatchDTO.Builder()
                                        .puuid("puuid from one participant")
                                        .kills(8)
                                        .assists(9)
                                        .deaths(2)
                                        .goldEarned(11522)
                                        .participantId(10)
                                        .teamPosition("mid")
                                        .championName("Garen")
                                        .win(true)
                                        .build()
                        ))
                        .build())
                .build());

        // WHEN
        GenerationCommentaryDTO result = generationCommentaryServiceImpl.getGenerationCommentaryDTO(1234L, "puuid", "1234_euw", "pseudo");

        // THEN
        verify(matchTimelineService, times(1)).getMatchEventsByMatchId("1234_euw");
        verify(historyPersistence, times(1)).findMatchByGameId(1234L);

        assertThat(result).isNotNull();
        assertThat(result.getEventsMatchDto()).isNotEmpty().hasSize(1)
                .extracting("buildingType", "monsterType", "dragonType", "monsterSubType", "team", "laneType", "type",
                        "timestamp", "victimId", "killerId", "towerType", "time")
                .containsExactly(tuple(null, "DRAGON", dragon.getLibelle(), dragon.getCode(), "10", "bot lane", "ELITE_MONSTER_KILL", 3502245L, 3, 1, null, "58:22 min"));

        assertThat(result.getContextGenerationCommentaryDTO()).isNotNull()
                .extracting("puuidFocusedPlayer", "pseudoFocusedPlayer")
                .containsExactly("puuid", "pseudo");

        assertThat(result.getContextGenerationCommentaryDTO().getParticipants())
                .isNotEmpty()
                .hasSize(1)
                .extracting("puuid", "kills", "deaths", "assists", "goldEarned", "participantId", "role", "championName", "win")
                .containsExactly(tuple("puuid from one participant", 8, 2, 9, 11522, 10, "mid", "Garen", true));
    }

    @ParameterizedTest
    @EnumSource(value = ObjectivesEnum.class, names = {"BARON_NASHOR", "HORDE", "RIFTHERALD", "ATAKHAN"})
    @DisplayName("getGenerationCommentaryDTO should call historyPersistence.findMatchByGameId to get match's context , participants ect..." +
            "call matchTimelineService.getMatchEventsByMatchId to get game's event timeline" +
            "and format data correctly, case other monsters kill events")
    void getGenerationCommentaryDTO_success_4(ObjectivesEnum monster) throws Exception {
        // GIVEN
        when(matchTimelineService.getMatchEventsByMatchId(anyString())).thenReturn(List.of(
                new EventMatchDTO.Builder()
                        .team("10")
                        .assistingParticipantIds(List.of(1, 2, 3))
                        .laneType("bot lane")
                        .type("ELITE_MONSTER_KILL")
                        .monsterType(monster.getCode())
                        .monsterSubType(monster.getLibelle())
                        .position(new PositionEventDTO.Builder()
                                .x(125)
                                .y(200)
                                .build())
                        .timestamp(3502245)
                        .victimId(3)
                        .killerId(1)
                        .time("20:30 min")
                        .build())
        );

        when(historyPersistence.findMatchByGameId(anyLong())).thenReturn(new MatchDTO.Builder()
                .info(new InfoMatchDTO.Builder()
                        .participants(List.of(
                                new ParticipantMatchDTO.Builder()
                                        .puuid("puuid from one participant")
                                        .kills(8)
                                        .assists(9)
                                        .deaths(2)
                                        .goldEarned(11522)
                                        .participantId(10)
                                        .teamPosition("mid")
                                        .championName("Garen")
                                        .win(false)
                                        .build()
                        ))
                        .build())
                .build());

        // WHEN
        GenerationCommentaryDTO result = generationCommentaryServiceImpl.getGenerationCommentaryDTO(1234L, "puuid", "1234_euw", "pseudo");

        // THEN
        verify(matchTimelineService, times(1)).getMatchEventsByMatchId("1234_euw");
        verify(historyPersistence, times(1)).findMatchByGameId(1234L);

        assertThat(result).isNotNull();
        assertThat(result.getEventsMatchDto()).isNotEmpty().hasSize(1)
                .extracting("buildingType", "monsterType", "dragonType", "monsterSubType", "team", "laneType", "type",
                        "timestamp", "victimId", "killerId", "towerType", "time")
                .containsExactly(tuple(null, monster.getCode(), null, monster.getLibelle(), "10", "bot lane", "ELITE_MONSTER_KILL", 3502245L, 3, 1, null, "58:22 min"));

        assertThat(result.getContextGenerationCommentaryDTO()).isNotNull()
                .extracting("puuidFocusedPlayer", "pseudoFocusedPlayer")
                .containsExactly("puuid", "pseudo");

        assertThat(result.getContextGenerationCommentaryDTO().getParticipants())
                .isNotEmpty()
                .hasSize(1)
                .extracting("puuid", "kills", "deaths", "assists", "goldEarned", "participantId", "role", "championName", "win")
                .containsExactly(tuple("puuid from one participant", 8, 2, 9, 11522, 10, "mid", "Garen", false));
    }
}
