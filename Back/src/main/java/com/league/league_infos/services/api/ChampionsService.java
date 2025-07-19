package com.league.league_infos.services.api;

import com.league.league_infos.models.constants.ApiRiotUrls;
import com.league.league_infos.models.dto.FreeChampionsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChampionsService {

    private final RestTemplate restTemplate;

    @Autowired
    public ChampionsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<FreeChampionsDTO> getFreeChampionsInfos() {
        ResponseEntity<FreeChampionsDTO> riotResponse = restTemplate.exchange(
                ApiRiotUrls.CHAMPION_ROTATIONS_API_URL,
                HttpMethod.GET,
                null,
                FreeChampionsDTO.class
        );
        return ResponseEntity
                .status(riotResponse.getStatusCode())
                .body(riotResponse.getBody());
    }
}
