package com.league.league_infos.services.riot;

import com.league.league_infos.common.constants.ApiRiotUrls;
import com.league.league_infos.common.exceptions.BusinessException;
import com.league.league_infos.dto.FreeChampionsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.league.league_infos.common.constants.ErrorMessagesEnum.ERROR_BUSINESS_4;

@Service
public class RiotChampionsService implements com.league.league_infos.services.api.ChampionsService {

    private final RestTemplate restTemplate;

    @Autowired
    public RiotChampionsService(@Qualifier("riotRestTemplate") RestTemplate restTemplate) {
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

        riotResponse.getBody().setFreeChampionIds(null);

        if (riotResponse.getBody() != null && (riotResponse.getBody().getFreeChampionIds() == null || riotResponse.getBody().getFreeChampionIdsForNewPlayers() == null)) {
            throw new BusinessException(ERROR_BUSINESS_4.getLibelle(), HttpStatus.BAD_GATEWAY);
        }
        return riotResponse.getBody();
    }
}
