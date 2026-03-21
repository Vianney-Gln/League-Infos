package com.league.league_infos.dao.repository;

import com.league.league_infos.dao.entity.ParticipantMatchEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.liquibase.enabled=false")
@ActiveProfiles("test")
class ParticipantMatchRepositoryTest {
    @Autowired
    private ParticipantMatchRepository participantMatchRepository;

    @Test
    @DisplayName("should get Participant from a match")
    void getParticipantByGameIdAndPuuid_success() {
        // GIVEN WHEN
        ParticipantMatchEntity participantMatchEntity = participantMatchRepository.getParticipantByGameIdAndPuuid(
                7706979430L,
                "xmy1FeituRetdLHk8tS6x7bnStnrIVKuxaiQDGV83QsTKIlg9XCernYcrWOxhK3RawWtI4gTL3SGw");

        // THEN
        assertThat(participantMatchEntity).isNotNull()
                .extracting("id", "championName", "puuid", "pseudo")
                .containsExactly(16L,
                        "Jayce",
                        "xmy1FeituRetdLHk8tS6x7bnStnrIVKuxaiQDGV83QsTKIlg9XCernYcrWOxhK3RawWtI4gTL3SGw",
                        "Pun1sher Reborn");
    }
}
