package com.league.league_infos.services.riot;

import com.league.league_infos.common.constants.ApiRiotUrls;
import com.league.league_infos.dto.FreeChampionsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RiotChampionsService implements com.league.league_infos.services.api.ChampionsService {

    private final RestTemplate restTemplate;

    @Autowired
    public RiotChampionsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public FreeChampionsDTO getFreeChampionsInfos() {
        ResponseEntity<FreeChampionsDTO> riotResponse = restTemplate.exchange(
                ApiRiotUrls.CHAMPION_ROTATIONS_API_URL,
                HttpMethod.GET,
                null,
                FreeChampionsDTO.class
        );
        return riotResponse.getBody();
    }
}
