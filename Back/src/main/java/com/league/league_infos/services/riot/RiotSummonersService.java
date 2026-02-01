package com.league.league_infos.services.riot;

import com.league.league_infos.common.constants.ApiRiotUrls;
import com.league.league_infos.dto.SummonerDTO;
import com.league.league_infos.services.api.SummonersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RiotSummonersService implements SummonersService {
    private final RestTemplate restTemplate;

    @Autowired
    public RiotSummonersService(@Qualifier("riotRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public SummonerDTO getSummonerByPuuid(String puuid) {
        ResponseEntity<SummonerDTO> riotResponse = restTemplate.exchange(
                ApiRiotUrls.SUMMONER_BY_PUUID_API_URL + "/" + puuid,
                HttpMethod.GET,
                null,
                SummonerDTO.class
        );
        return riotResponse.getBody();
    }
}
