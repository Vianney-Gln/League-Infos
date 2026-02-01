package com.league.league_infos.services.riot;

import com.league.league_infos.common.constants.ApiRiotUrls;
import com.league.league_infos.common.exceptions.BusinessException;
import com.league.league_infos.dto.AccountDTO;
import com.league.league_infos.services.api.AccountService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static com.league.league_infos.common.constants.ErrorMessagesEnum.ERROR_BUSINESS_1;
import static com.league.league_infos.common.constants.ErrorMessagesEnum.ERROR_BUSINESS_2;
import static com.league.league_infos.common.constants.ErrorMessagesEnum.ERROR_BUSINESS_3;

@Service
public class RiotAccountService implements AccountService {
    private final RestTemplate restTemplate;

    public RiotAccountService(@Qualifier("riotRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public AccountDTO getAccountByPuuid(String puuid) {
        ResponseEntity<AccountDTO> riotResponse = restTemplate.exchange(
                ApiRiotUrls.ACCOUNT_BY_PUUID_API_URL + "/" + puuid,
                HttpMethod.GET,
                null,
                AccountDTO.class
        );
        return riotResponse.getBody();
    }

    @Override
    public AccountDTO getAccountByRiotId(String gameName, String tagLine) {

        try {
            ResponseEntity<AccountDTO> riotResponse = restTemplate.exchange(
                    ApiRiotUrls.ACCOUNT_BY_RIOT_ID_API_URL + "/" + gameName + "/" + tagLine,
                    HttpMethod.GET,
                    null,
                    AccountDTO.class
            );
            return riotResponse.getBody();
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
