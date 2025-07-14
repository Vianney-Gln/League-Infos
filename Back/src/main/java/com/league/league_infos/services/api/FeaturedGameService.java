package com.league.league_infos.services.api;

import com.league.league_infos.common.exceptions.BusinessException;
import com.league.league_infos.models.constants.ApiRiotUrls;
import com.league.league_infos.models.dto.FeaturedGamesDTO;
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
public class FeaturedGameService {
    private final RestTemplate restTemplate;

    @Autowired
    public FeaturedGameService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<FeaturedGamesDTO> getFeaturedGames() {
        try {
            return restTemplate.exchange(
                    ApiRiotUrls.FEATURED_GAMES_API_URL,
                    HttpMethod.GET,
                    null,
                    FeaturedGamesDTO.class
            );

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new BusinessException(ERROR_BUSINESS_2.getLibelle(), HttpStatus.BAD_REQUEST);
        } catch (ResourceAccessException ex) {
            throw new BusinessException(ERROR_BUSINESS_3.getLibelle(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
