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
                        1756249870149L,
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
        // GIVEN
        LocalDateTime creationDatePlus50Min = LocalDateTime.of(2025, 9, 28, 1, 0, 0).plusMinutes(50L);

        // WHEN
        List<InfoMatchEntity> results = infoMatchRepository.findRecentsMatchByPuuidAndQueue(
                "zb1DuHxFttxhq_01ETiTwyjkBzaCq9JcysGVfW_J6u45vNLQi0AQTdZE3cvS0gsYHK_VKLAbtWDpmQ",
                creationDatePlus50Min,
                420
        );
        // THEN
        assertThat(results).isEmpty();
    }

    @Test
    @DisplayName("Should get matchs from the last current hour")
    void findRecentsMatchByPuuidAndQueue_success_2() {
        // GIVEN
        LocalDateTime creationDateMinus50Min = LocalDateTime.of(2025, 9, 28, 1, 0, 0).minusMinutes(50L);

        // WHEN
        List<InfoMatchEntity> results = infoMatchRepository.findRecentsMatchByPuuidAndQueue(
                "zb1DuHxFttxhq_01ETiTwyjkBzaCq9JcysGVfW_J6u45vNLQi0AQTdZE3cvS0gsYHK_VKLAbtWDpmQ",
                creationDateMinus50Min,
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
                .containsExactly(tuple("GameComplete",
                        1756249870149L,
                        1742L,
                        "CLASSIC",
                        "MATCHED_GAME",
                        1756251627166L,
                        "15.16.706.7476",
                        420,
                        11));
    }
}
