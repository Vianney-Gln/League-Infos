package com.league.league_infos.services.riot;

import com.league.league_infos.common.constants.ApiRiotUrls;
import com.league.league_infos.common.exceptions.BusinessException;
import com.league.league_infos.dto.FeaturedGamesDTO;
import com.league.league_infos.services.api.FeaturedGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static com.league.league_infos.common.constants.ErrorMessagesEnum.ERROR_BUSINESS_2;
import static com.league.league_infos.common.constants.ErrorMessagesEnum.ERROR_BUSINESS_3;

@Service
public class RiotFeaturedGameService implements FeaturedGameService {
    private final RestTemplate restTemplate;

    @Autowired
    public RiotFeaturedGameService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public FeaturedGamesDTO getFeaturedGames() {
        try {
            ResponseEntity<FeaturedGamesDTO> riotResponse = restTemplate.exchange(
                    ApiRiotUrls.FEATURED_GAMES_API_URL,
                    HttpMethod.GET,
                    null,
                    FeaturedGamesDTO.class
            );

            return riotResponse.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new BusinessException(ERROR_BUSINESS_2.getLibelle(), HttpStatus.BAD_REQUEST);
        } catch (ResourceAccessException ex) {
            throw new BusinessException(ERROR_BUSINESS_3.getLibelle(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
