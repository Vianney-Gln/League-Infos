package com.league.league_infos.services.riot;

import com.league.league_infos.common.constants.ApiRiotUrls;
import com.league.league_infos.common.exceptions.BusinessException;
import com.league.league_infos.dto.match.MatchDTO;
import com.league.league_infos.services.api.HistoryGamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.league.league_infos.common.constants.ApiRiotUrls.HISTORY_GAMES_URL;
import static com.league.league_infos.common.constants.ErrorMessagesEnum.ERROR_BUSINESS_1;
import static com.league.league_infos.common.constants.ErrorMessagesEnum.ERROR_BUSINESS_2;
import static com.league.league_infos.common.constants.ErrorMessagesEnum.ERROR_BUSINESS_3;

@Service
public class RiotHistoryGamesService implements HistoryGamesService {

    private final RestTemplate restTemplate;

    @Value("${app.game-history.max-count}")
    private int MAX_COUNT_MATCH_HISTORY;

    @Autowired
    public RiotHistoryGamesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<String> getHistoryIds(String puuid, Integer queue) {

        try {
            ResponseEntity<String[]> result = restTemplate.exchange(
                    buildUrl(puuid, queue),
                    HttpMethod.GET,
                    null,
                    String[].class
            );
            return result.getBody() != null ? Arrays.asList(result.getBody()) : Collections.emptyList();

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new BusinessException(ERROR_BUSINESS_1.getLibelle(), HttpStatus.NOT_FOUND);
            } else {
                throw new BusinessException(ERROR_BUSINESS_2.getLibelle(), HttpStatus.BAD_REQUEST);
            }
        } catch (ResourceAccessException ex) {
            throw new BusinessException(ERROR_BUSINESS_3.getLibelle(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @Override
    public List<MatchDTO> getMatchHistory(List<String> listMatchId) {

        List<MatchDTO> listMatchDTO = new ArrayList<>();
        try {
            listMatchDTO = listMatchId.stream().map(matchId -> {
                ResponseEntity<MatchDTO> result = restTemplate.exchange(
                        HISTORY_GAMES_URL + "/" + matchId,
                        HttpMethod.GET,
                        null,
                        MatchDTO.class
                );
                return result.getBody();
            }).toList();

            return listMatchDTO;

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new BusinessException(ERROR_BUSINESS_1.getLibelle(), HttpStatus.NOT_FOUND);
            } else {
                throw new BusinessException(ERROR_BUSINESS_2.getLibelle(), HttpStatus.BAD_REQUEST);
            }
        } catch (ResourceAccessException ex) {
            throw new BusinessException(ERROR_BUSINESS_3.getLibelle(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private String buildUrl(String puuid, Integer queue) {
        String baseUrl = ApiRiotUrls.HISTORY_GAMES_IDS_API_URL + "/" + puuid + "/ids?start=0&count=" + MAX_COUNT_MATCH_HISTORY;
        if (queue == null) {
            return baseUrl;
        }
        return baseUrl + "&queue=" + queue;
    }
}
