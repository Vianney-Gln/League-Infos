package com.league.league_infos.services.api;

import com.league.league_infos.common.exceptions.BusinessException;
import com.league.league_infos.models.constants.ApiRiotUrls;
import com.league.league_infos.models.dto.AccountDTO;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static com.league.league_infos.common.constants.ErrorMessagesEnum.*;

@Service
public class AccountService {
    private final RestTemplate restTemplate;

    public AccountService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<AccountDTO> getAccountByPuuid(String puuid) {
        return restTemplate.exchange(
                ApiRiotUrls.ACCOUNT_BY_PUUID_API_URL + "/" + puuid,
                HttpMethod.GET,
                null,
                AccountDTO.class
        );
    }

    public ResponseEntity<AccountDTO> getAccountByRiotId(String gameName, String tagLine) {

        try {
            return restTemplate.exchange(
                    ApiRiotUrls.ACCOUNT_BY_RIOT_ID_API_URL + "/" + gameName + "/" + tagLine,
                    HttpMethod.GET,
                    null,
                    AccountDTO.class
            );
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            if (ex.getStatusCode().value() == 404) {
                throw new BusinessException(ERROR_BUSINESS_1.getLibelle(), HttpStatus.NOT_FOUND);
            } else {
                throw new BusinessException(ERROR_BUSINESS_2.getLibelle(), HttpStatus.BAD_REQUEST);
            }
        } catch (ResourceAccessException ex) {
            throw new BusinessException(ERROR_BUSINESS_3.getLibelle(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
