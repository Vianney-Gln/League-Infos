package com.league.league_infos;

import com.league.league_infos.config.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CorsProperties.class)
public class LeagueInfosApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeagueInfosApplication.class, args);
    }

}
