package com.league.league_infos.services;

import com.league.league_infos.models.constants.ApiRiotUrls;
import com.league.league_infos.models.dto.LeagueListDTO;
import com.league.league_infos.models.enums.QueueEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PlayersService {

    private final RestTemplate restTemplate;

    @Autowired
    public PlayersService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<LeagueListDTO> getLeagueChallengerDataSoloQ() {
        return restTemplate.exchange(
                ApiRiotUrls.CHALLENGERS_LEAGUE_API_URL + "/" + QueueEnum.RANKED_SOLO_5x5.getLibelle(),
                HttpMethod.GET,
                null,
                LeagueListDTO.class
        );
    }

    public ResponseEntity<LeagueListDTO> getLeagueChallengerDataFlexQ() {
        return restTemplate.exchange(
                ApiRiotUrls.CHALLENGERS_LEAGUE_API_URL + "/" + QueueEnum.RANKED_FLEX_SR.getLibelle(),
                HttpMethod.GET,
                null,
                LeagueListDTO.class
        );
    }
}
