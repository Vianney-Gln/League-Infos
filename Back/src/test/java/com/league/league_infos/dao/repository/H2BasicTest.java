package com.league.league_infos.dao.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest(properties = "spring.liquibase.enabled=false")
@ActiveProfiles("test")
class H2BasicTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testH2IsUp() {
        Integer result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM T_INFOS_MATCH", Integer.class);
        assertThat(result).isEqualTo(1);
    }
}
