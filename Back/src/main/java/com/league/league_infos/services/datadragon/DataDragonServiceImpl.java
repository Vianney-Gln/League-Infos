package com.league.league_infos.services.datadragon;

import com.league.league_infos.common.constants.DataDragonUrls;
import com.league.league_infos.dto.ddragon.ChampionDataDTO;
import com.league.league_infos.services.api.DataDragonService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class DataDragonServiceImpl implements DataDragonService {

    private final RestTemplate restTemplate;

    public DataDragonServiceImpl(@Qualifier("ddragonRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<String> getAllNumVersionsLol() {
        ResponseEntity<String[]> ddragonResponse = restTemplate.exchange(
                DataDragonUrls.DDRAGON_NUM_VERSIONS_API_URL,
                HttpMethod.GET,
                null,
                String[].class
        );
        return ddragonResponse.getBody() != null ? Arrays.asList(ddragonResponse.getBody()) : Collections.emptyList();
    }

    @Override
    public ChampionDataDTO getChampionsLol(String numVersion) {
        return restTemplate.exchange(
                        DataDragonUrls.DDRAGON_GET_CHAMPIONS_API_URL.formatted(numVersion),
                        HttpMethod.GET,
                        null,
                        ChampionDataDTO.class)
                .getBody();
    }
}
