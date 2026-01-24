package com.league.league_infos.services.business;

import com.fasterxml.jackson.core.JsonParseException;
import com.league.league_infos.dto.ia.EventMatchDTO;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;

@ExtendWith(MockitoExtension.class)
class TimelineParserTest {

    @InjectMocks
    private TimelineParser timelineParser;

    @Test
    @DisplayName("should parse a given jsonStream into a List<EventMatchDTO>")
    void parseTimeline_success_1() throws Exception {

        // GIVEN
        String jsonMock = """
                {
                  "metadata": {
                    "dataVersion": "2",
                    "matchId": "EUW1_6543210987",
                    "participants": [
                      "puuid1",
                      "puuid2",
                      "puuid3",
                      "puuid4",
                      "puuid5",
                      "puuid6",
                      "puuid7",
                      "puuid8",
                      "puuid9",
                      "puuid10"
                    ]
                  },
                  "info": {
                    "frameInterval": 60000,
                    "frames": [
                      {
                        "timestamp": 0,
                        "participantFrames": {
                          "1": {
                            "participantId": 1,
                            "position": { "x": 0, "y": 0 },
                            "currentGold": 500,
                            "totalGold": 500,
                            "level": 1,
                            "xp": 0,
                            "minionsKilled": 0,
                            "jungleMinionsKilled": 0
                          },
                          "2": {
                            "participantId": 2,
                            "position": { "x": 0, "y": 0 },
                            "currentGold": 500,
                            "totalGold": 500,
                            "level": 1,
                            "xp": 0,
                            "minionsKilled": 0,
                            "jungleMinionsKilled": 0
                          }
                        },
                        "events": [
                          {
                            "type": "GAME_START",
                            "timestamp": 0
                          }
                        ]
                      },
                      {
                        "timestamp": 60000,
                        "participantFrames": {
                          "1": {
                            "participantId": 1,
                            "position": { "x": 6500, "y": 6500 },
                            "currentGold": 620,
                            "totalGold": 620,
                            "level": 1,
                            "xp": 120,
                            "minionsKilled": 6,
                            "jungleMinionsKilled": 0
                          }
                        },
                        "events": [
                          {
                            "type": "ITEM_PURCHASED",
                            "timestamp": 65000,
                            "participantId": 1,
                            "itemId": 1055
                          },
                          {
                            "type": "SKILL_LEVEL_UP",
                            "timestamp": 67000,
                            "participantId": 1,
                            "skillSlot": 1,
                            "levelUpType": "NORMAL"
                          },
                          {
                            "type": "WARD_PLACED",
                            "timestamp": 69000,
                            "participantId": 2,
                            "wardType": "YELLOW_TRINKET",
                            "position": { "x": 5000, "y": 5000 }
                          }
                        ]
                      },
                      {
                        "timestamp": 180000,
                        "participantFrames": {
                          "1": {
                            "participantId": 1,
                            "position": { "x": 7200, "y": 7200 },
                            "currentGold": 900,
                            "totalGold": 1200,
                            "level": 3,
                            "xp": 560,
                            "minionsKilled": 18,
                            "jungleMinionsKilled": 2
                          }
                        },
                        "events": [
                          {
                            "type": "CHAMPION_KILL",
                            "timestamp": 185000,
                            "killerId": 1,
                            "victimId": 6,
                            "assistingParticipantIds": [2, 3],
                            "position": { "x": 7200, "y": 7200 },
                            "bounty": 150,
                            "killStreakLength": 1,
                            "shutdownBounty": 0
                          }
                        ]
                      },
                      {
                        "timestamp": 420000,
                        "participantFrames": {},
                        "events": [
                          {
                            "type": "ELITE_MONSTER_KILL",
                            "timestamp": 425000,
                            "killerId": 4,
                            "monsterType": "DRAGON",
                            "monsterSubType": "INFERNAL_DRAGON",
                            "position": { "x": 9866, "y": 4414 }
                          }
                        ]
                      },
                      {
                        "timestamp": 600000,
                        "participantFrames": {},
                        "events": [
                          {
                            "type": "BUILDING_KILL",
                            "timestamp": 605000,
                            "killerId": 7,
                            "buildingType": "TOWER_BUILDING",
                            "laneType": "MID_LANE",
                            "towerType": "OUTER_TURRET",
                            "teamId": 100,
                            "position": { "x": 5846, "y": 6396 }
                          },
                          {
                            "type": "TURRET_PLATE_DESTROYED",
                            "timestamp": 607000,
                            "killerId": 7,
                            "laneType": "MID_LANE",
                            "teamId": 100
                          }
                        ]
                      },
                      {
                        "timestamp": 900000,
                        "participantFrames": {},
                        "events": [
                          {
                            "type": "OBJECTIVE_BOUNTY_PRESTART",
                            "timestamp": 900500,
                            "teamId": 200
                          },
                          {
                            "type": "OBJECTIVE_BOUNTY_FINISH",
                            "timestamp": 960000,
                            "teamId": 200
                          }
                        ]
                      },
                      {
                        "timestamp": 1800000,
                        "participantFrames": {},
                        "events": [
                          {
                            "type": "GAME_END",
                            "timestamp": 1800500,
                            "winningTeam": 100
                          }
                        ]
                      }
                    ]
                  }
                }
                """;

        InputStream inputStream = IOUtils.toInputStream(jsonMock, "UTF-8");

        // WHEN
        List<EventMatchDTO> result = timelineParser.parseTimeline(inputStream);

        // THEN
        assertThat(result).isNotEmpty().hasSize(3)
                .extracting("buildingType", "monsterType", "monsterSubType", "dragonType", "team", "laneType", "type",
                        "timestamp", "victimId", "killerId", "towerType", "position.x", "position.y", "assistingParticipantIds")
                .containsExactly(
                        tuple(null, null, null, null, null, null, "CHAMPION_KILL", 185000L, 6, 1, null, 7200, 7200, List.of(2, 3)),
                        tuple(null, "DRAGON", "INFERNAL_DRAGON", null, null, null, "ELITE_MONSTER_KILL", 425000L, null, 4, null, 9866, 4414, Collections.emptyList()),
                        tuple("TOWER_BUILDING", null, null, null, null, "MID_LANE", "BUILDING_KILL", 605000L, null, 7, "OUTER_TURRET", 5846, 6396, Collections.emptyList())
                );

    }

    @Test
    @DisplayName("should return an empty list if infoNode is null")
    void parseTimeline_success_2() throws Exception {

        // GIVEN
        String jsonMock = """
                {
                  "metadata": {
                    "dataVersion": "2",
                    "matchId": "EUW1_6543210987",
                    "participants": [
                      "puuid1",
                      "puuid2",
                      "puuid3",
                      "puuid4",
                      "puuid5",
                      "puuid6",
                      "puuid7",
                      "puuid8",
                      "puuid9",
                      "puuid10"
                    ]
                  }
                }
                """;

        InputStream inputStream = IOUtils.toInputStream(jsonMock, "UTF-8");

        // WHEN
        List<EventMatchDTO> result = timelineParser.parseTimeline(inputStream);

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("should return an empty list if frames is empty")
    void parseTimeline_success_3() throws Exception {

        // GIVEN
        String jsonMock = """
                {
                  "metadata": {
                    "dataVersion": "2",
                    "matchId": "EUW1_6543210987",
                    "participants": [
                      "puuid1",
                      "puuid2",
                      "puuid3",
                      "puuid4",
                      "puuid5",
                      "puuid6",
                      "puuid7",
                      "puuid8",
                      "puuid9",
                      "puuid10"
                    ]
                  },
                  "info": {
                    "frameInterval": 60000,
                    "frames": []
                  }
                }
                """;

        InputStream inputStream = IOUtils.toInputStream(jsonMock, "UTF-8");

        // WHEN
        List<EventMatchDTO> result = timelineParser.parseTimeline(inputStream);

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("should return an empty list if frames is null")
    void parseTimeline_success_4() throws Exception {

        // GIVEN
        String jsonMock = """
                {
                  "metadata": {
                    "dataVersion": "2",
                    "matchId": "EUW1_6543210987",
                    "participants": [
                      "puuid1",
                      "puuid2",
                      "puuid3",
                      "puuid4",
                      "puuid5",
                      "puuid6",
                      "puuid7",
                      "puuid8",
                      "puuid9",
                      "puuid10"
                    ]
                  },
                  "info": {
                    "frameInterval": 60000
                  }
                }
                """;

        InputStream inputStream = IOUtils.toInputStream(jsonMock, "UTF-8");

        // WHEN
        List<EventMatchDTO> result = timelineParser.parseTimeline(inputStream);

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("should throw a JsonParseException if an error occurs during parsing")
    void parseTimeline_fail_1() throws Exception {

        // GIVEN
        String jsonMock = """
                {
                  "metadata": {
                    "dataVersion": "2",
                    "matchId": "EUW1_6543210987",
                    "participants": [
                      "puuid1",
                      "puuid2",
                      "puuid3",
                      "puuid4",
                      "puuid5",
                      "puuid6",
                      "puuid7",
                      "puuid8",
                      "puuid9",
                      "puuid10"
                    ]
                  },
                  "info": {
                    "frameInterval": 60000,
                  }
                }
                """;

        InputStream inputStream = IOUtils.toInputStream(jsonMock, "UTF-8");

        // WHEN + THEN
        assertThatThrownBy(() -> timelineParser.parseTimeline(inputStream)).isInstanceOf(JsonParseException.class);
    }
}
