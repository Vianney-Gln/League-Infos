package com.league.league_infos.services.business;

import com.league.league_infos.common.exceptions.BusinessException;
import com.league.league_infos.models.dto.BannedChampionDTO;
import com.league.league_infos.models.dto.FeaturedGameInfoDTO;
import com.league.league_infos.models.dto.FeaturedGamesDTO;
import com.league.league_infos.services.api.FeaturedGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.league.league_infos.common.constants.ErrorMessagesEnum.ERROR_BUSINESS_4;

@Service
public class StatsService {
    private final FeaturedGameService featuredGameService;

    @Autowired
    public StatsService(FeaturedGameService featuredGameService) {
        this.featuredGameService = featuredGameService;
    }

    /**
     * Calculate the most banned champion from a suit of few games
     *
     * @return Long
     */
    public Long calculateMostBannedChampion() {
        ResponseEntity<FeaturedGamesDTO> featuredGamesResponse = featuredGameService.getFeaturedGames();
        FeaturedGamesDTO featuredGames = featuredGamesResponse != null ? featuredGamesResponse.getBody() : null;
        List<List<BannedChampionDTO>> bannedChampionDTOList;
        Map<Long, Integer> idsBannedMap = new HashMap<>();
        if (featuredGames != null) {
            bannedChampionDTOList = featuredGames.getGameList().stream().map(FeaturedGameInfoDTO::getBannedChampions).toList();
            for (List<BannedChampionDTO> listBannedChampions : bannedChampionDTOList) {
                for (BannedChampionDTO bannedChampionDTO : listBannedChampions) {
                    idsBannedMap.put(bannedChampionDTO.getChampionId(), idsBannedMap.getOrDefault(bannedChampionDTO.getChampionId(), 0) + 1);
                }
            }
            long idMostBannedChamp = 0L;
            Integer maxBans = 0;
            for (Map.Entry<Long, Integer> entry : idsBannedMap.entrySet()) {
                if (entry.getValue() > maxBans && entry.getKey() != -1 && entry.getKey() != 0) {
                    idMostBannedChamp = entry.getKey();
                    maxBans = entry.getValue();
                }
            }

            if (idMostBannedChamp != 0L) {
                return idMostBannedChamp;
            }
        }
        throw new BusinessException(ERROR_BUSINESS_4.getLibelle(), HttpStatus.NOT_FOUND);
    }
}
