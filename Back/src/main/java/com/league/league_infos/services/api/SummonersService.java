package com.league.league_infos.services.api;

import com.league.league_infos.models.constants.ApiRiotUrls;
import com.league.league_infos.models.dto.SummonerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SummonersService {
    private final RestTemplate restTemplate;

    @Autowired
    public SummonersService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<SummonerDTO> getSummonerByPuuid(String puuid) {
        return restTemplate.exchange(
                ApiRiotUrls.SUMMONER_BY_PUUID_API_URL + "/" + puuid,
                HttpMethod.GET,
                null,
                SummonerDTO.class
        );
    }
}
