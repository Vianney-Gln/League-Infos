package com.league.league_infos.dao.repository;

import com.league.league_infos.dao.entity.InfoMatchEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@DataJpaTest(properties = "spring.liquibase.enabled=false")
@ActiveProfiles("test")
class InfoMatchRepositoryTest {

    @Autowired
    private InfoMatchRepository infoMatchRepository;

    @Test
    void findByGameId_success() {
        // WHEN
        InfoMatchEntity infoMatchEntity = infoMatchRepository.findByGameId(7508515244L);

        // THEN
        assertThat(infoMatchEntity).isNotNull().extracting("endOfGameResult",
                        "gameCreation",
                        "gameDuration",
                        "gameMode",
                        "gameType",
                        "gameEndTimestamp",
                        "gameVersion",
                        "queueId",
                        "mapId")
                .containsExactly("GameComplete",
                        1756259870149L,
                        1742L,
                        "CLASSIC",
                        "MATCHED_GAME",
                        1756251627166L,
                        "15.16.706.7476",
                        420,
                        11);
    }

    @Test
    @DisplayName("Should not get any match if there is no recent match in the current last hour")
    void findRecentsMatchByPuuidAndQueue_success_1() {
        // WHEN
        List<InfoMatchEntity> results = infoMatchRepository.findRecentsMatchByPuuidAndQueue(
                "zb1DuHxFttxhq_01ETiTwyjkBzaCq9JcysGVfW_J6u45vNLQi0AQTdZE3cvS0gsYHK_VKLAbtWDpmQ",
                LocalDateTime.of(2025, 11, 27, 0, 0, 0),
                420
        );
        // THEN
        assertThat(results).isEmpty();
    }

    @Test
    @DisplayName("Should get matchs from the last current hour")
    void findRecentsMatchByPuuidAndQueue_success_2() {
        // WHEN
        List<InfoMatchEntity> results = infoMatchRepository.findRecentsMatchByPuuidAndQueue(
                "zb1DuHxFttxhq_01ETiTwyjkBzaCq9JcysGVfW_J6u45vNLQi0AQTdZE3cvS0gsYHK_VKLAbtWDpmQ",
                LocalDateTime.of(2025, 10, 13, 0, 30, 0),
                420
        );
        // THEN
        assertThat(results).isNotEmpty().hasSize(1).extracting("endOfGameResult",
                        "gameCreation",
                        "gameDuration",
                        "gameMode",
                        "gameType",
                        "gameEndTimestamp",
                        "gameVersion",
                        "queueId",
                        "mapId")
                .containsExactly(tuple("GameComplete", 1756400970149L, 1742L, "CLASSIC", "MATCHED_GAME", 1756251627166L, "15.16.706.7476", 420, 11));
    }

    @Test
    @DisplayName("should get all matchs by puuid and queueId")
    void findAllMatchByPuuidAndQueue_success() {
        // WHEN
        List<InfoMatchEntity> results = infoMatchRepository.findAllMatchByPuuidAndQueue("zb1DuHxFttxhq_01ETiTwyjkBzaCq9JcysGVfW_J6u45vNLQi0AQTdZE3cvS0gsYHK_VKLAbtWDpmQ", 420);

        // THEN
        assertThat(results).isNotEmpty().hasSize(10).extracting("endOfGameResult",
                        "gameCreation",
                        "gameDuration",
                        "gameMode",
                        "gameType",
                        "gameEndTimestamp",
                        "gameVersion",
                        "queueId",
                        "mapId")
                .containsExactly(
                        tuple("GameComplete", 1756400970149L, 1742L, "CLASSIC", "MATCHED_GAME", 1756251627166L, "15.16.706.7476", 420, 11),
                        tuple("GameComplete", 1756390970149L, 1742L, "CLASSIC", "MATCHED_GAME", 1756251627166L, "15.16.706.7476", 420, 11),
                        tuple("GameComplete", 1756380970149L, 1742L, "CLASSIC", "MATCHED_GAME", 1756251627166L, "15.16.706.7476", 420, 11),
                        tuple("GameComplete", 1756370970149L, 1742L, "CLASSIC", "MATCHED_GAME", 1756251627166L, "15.16.706.7476", 420, 11),
                        tuple("GameComplete", 1756360970149L, 1742L, "CLASSIC", "MATCHED_GAME", 1756251627166L, "15.16.706.7476", 420, 11),
                        tuple("GameComplete", 1756350970149L, 1742L, "CLASSIC", "MATCHED_GAME", 1756251627166L, "15.16.706.7476", 420, 11),
                        tuple("GameComplete", 1756340970149L, 1742L, "CLASSIC", "MATCHED_GAME", 1756251627166L, "15.16.706.7476", 420, 11),
                        tuple("GameComplete", 1756330970149L, 1742L, "CLASSIC", "MATCHED_GAME", 1756251627166L, "15.16.706.7476", 420, 11),
                        tuple("GameComplete", 1756320970149L, 1742L, "CLASSIC", "MATCHED_GAME", 1756251627166L, "15.16.706.7476", 420, 11),
                        tuple("GameComplete", 1756310970149L, 1742L, "CLASSIC", "MATCHED_GAME", 1756251627166L, "15.16.706.7476", 420, 11));
    }

    @Test
    @DisplayName("Should return entries before the given date limited to 10 max")
    void findAllMatchByPuuidAndQueueBeforeGivenDate_success() {
        // GIVEN
        Long creationGame = 1756299870150L;

        // WHEN
        List<InfoMatchEntity> results = infoMatchRepository.findAllMatchByPuuidAndQueueBeforeGivenDate(
                "zb1DuHxFttxhq_01ETiTwyjkBzaCq9JcysGVfW_J6u45vNLQi0AQTdZE3cvS0gsYHK_VKLAbtWDpmQ", creationGame, 420);

        // THEN
        assertThat(results).hasSize(5)
                .extracting("endOfGameResult",
                        "gameCreation",
                        "gameDuration",
                        "gameMode",
                        "gameType",
                        "gameEndTimestamp",
                        "gameVersion",
                        "queueId",
                        "mapId").containsExactly(
                        tuple("GameComplete", 1756299870149L, 1742L, "CLASSIC", "MATCHED_GAME", 1756251627166L, "15.16.706.7476", 420, 11),
                        tuple("GameComplete", 1756289870149L, 1742L, "CLASSIC", "MATCHED_GAME", 1756251627166L, "15.16.706.7476", 420, 11),
                        tuple("GameComplete", 1756279870149L, 1742L, "CLASSIC", "MATCHED_GAME", 1756251627166L, "15.16.706.7476", 420, 11),
                        tuple("GameComplete", 1756269870149L, 1742L, "CLASSIC", "MATCHED_GAME", 1756251627166L, "15.16.706.7476", 420, 11),
                        tuple("GameComplete", 1756259870149L, 1742L, "CLASSIC", "MATCHED_GAME", 1756251627166L, "15.16.706.7476", 420, 11));
    }
}
