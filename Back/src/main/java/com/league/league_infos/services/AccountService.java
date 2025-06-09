package com.league.league_infos.services;

import com.league.league_infos.models.constants.ApiRiotUrls;
import com.league.league_infos.models.dto.AccountDTO;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
}
