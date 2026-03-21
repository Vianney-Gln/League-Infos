package com.league.league_infos.dao.repository;

import com.league.league_infos.dao.entity.CommentaryMatchEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.liquibase.enabled=false")
@ActiveProfiles("test")
class CommentaryMatchRepositoryTest {

    @Autowired
    private CommentaryMatchRepository commentaryMatchRepository;


    @Test
    void getByGameIdAndPuuid_success() {
        // WHEN
        Optional<CommentaryMatchEntity> result = commentaryMatchRepository.getByGameIdAndPuuid(7706979430L,
                "xmy1FeituRetdLHk8tS6x7bnStnrIVKuxaiQDGV83QsTKIlg9XCernYcrWOxhK3RawWtI4gTL3SGw");

        // THEN
        assertThat(result.isPresent()).isTrue();
        CommentaryMatchEntity commentary = result.get();
        assertThat(commentary).isNotNull()
                .extracting("commentaryMatchId", "object", "createdDate", "createdDate", "model", "content", "serviceTier", "fingerPrint")
                .containsExactly("chatcmpl-D5e3GABPryZ2vEHhFtQj4rFThgFv3",
                        "chat.completion",
                        1770237970L,
                        1770237970L,
                        "gpt-4.1-mini-2025-04-14",
                        "Pun1sher Reborn a livré une performance solide sur Cassiopeia, contribuant activement à la victoire de son équipe. Dès le début, il a su imposer sa " +
                                "présence en sécurisant plusieurs éliminations clés, notamment contre Ambessa en début de partie et en participant aux combats majeurs avec Viego" +
                                " et Alistar. Malgré quelques morts, il a fait preuve d’un style de jeu agressif mais intelligent, capitalisant sur ses dégâts constants pour " +
                                "harceler et affaiblir ses adversaires. Son rôle de midlaner a été central, créant des opportunités et soutenant les assauts orchestrés par " +
                                "Viego, le jungler dominant, et Gragas, solide en top. Comparé à ses coéquipiers, Pun1sher Reborn a équilibré agressivité et soutien, avec un " +
                                "impact stratégique notable dans le contrôle des combats d’équipe, participant à la pression sur les objectifs et à la prise de tours. Sa " +
                                "performance a été un pilier dans la victoire collective, montrant un style de jeu à la fois proactif et efficace.",
                        "default",
                        "fp_e01c6f58e1");

        assertThat(commentary.getParticipantMatchEntity()).isNotNull()
                .extracting("puuid").isEqualTo("xmy1FeituRetdLHk8tS6x7bnStnrIVKuxaiQDGV83QsTKIlg9XCernYcrWOxhK3RawWtI4gTL3SGw");

        assertThat(commentary.getParticipantMatchEntity().getInfoMatchEntity()).isNotNull()
                .extracting("gameId").isEqualTo(7706979430L);
    }
}
