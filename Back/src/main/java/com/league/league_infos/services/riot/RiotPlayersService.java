package com.league.league_infos.services.riot;

import com.league.league_infos.common.constants.ApiRiotUrls;
import com.league.league_infos.common.enums.QueueEnum;
import com.league.league_infos.common.exceptions.BusinessException;
import com.league.league_infos.dto.ChampionMasteryDTO;
import com.league.league_infos.dto.LeagueEntryDTO;
import com.league.league_infos.dto.LeagueListDTO;
import com.league.league_infos.services.api.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.league.league_infos.common.constants.ErrorMessagesEnum.ERROR_BUSINESS_1;
import static com.league.league_infos.common.constants.ErrorMessagesEnum.ERROR_BUSINESS_2;
import static com.league.league_infos.common.constants.ErrorMessagesEnum.ERROR_BUSINESS_3;

@Service
public class RiotPlayersService implements PlayerService {

    private final RestTemplate restTemplate;

    @Autowired
    public RiotPlayersService(@Qualifier("riotRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public LeagueListDTO getLeagueChallengerDataSoloQ() {
        ResponseEntity<LeagueListDTO> riotResponse = restTemplate.exchange(
                ApiRiotUrls.CHALLENGERS_LEAGUE_API_URL + "/" + QueueEnum.RANKED_SOLO_5x5.getLibelle(),
                HttpMethod.GET,
                null,
                LeagueListDTO.class
        );
        return riotResponse.getBody();
    }

    @Override
    public LeagueListDTO getLeagueChallengerDataFlexQ() {
        ResponseEntity<LeagueListDTO> riotResponse = restTemplate.exchange(
                ApiRiotUrls.CHALLENGERS_LEAGUE_API_URL + "/" + QueueEnum.RANKED_FLEX_SR.getLibelle(),
                HttpMethod.GET,
                null,
                LeagueListDTO.class
        );
        return riotResponse.getBody();
    }

    @Override
    public List<LeagueEntryDTO> getLeagueEntriesByPuuid(String puuid) {
        try {
            ResponseEntity<LeagueEntryDTO[]> result = restTemplate.exchange(
                    ApiRiotUrls.PLAYER_LEAGUE_API_URL + "/" + puuid,
                    HttpMethod.GET,
                    null,
                    LeagueEntryDTO[].class
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
    public List<ChampionMasteryDTO> getChampionMasteriesByPuuid(String puuid) {
        try {
            ResponseEntity<ChampionMasteryDTO[]> result = restTemplate.exchange(
                    ApiRiotUrls.CHAMPION_MASTERY_API_URL + "/" + puuid + "/top",
                    HttpMethod.GET,
                    null,
                    ChampionMasteryDTO[].class
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

}
