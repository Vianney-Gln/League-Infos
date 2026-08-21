package com.league.league_infos.services.handler;

import com.league.league_infos.common.exceptions.BusinessException;
import com.league.league_infos.dto.ddragon.ChampionDTO;
import com.league.league_infos.services.api.DataDragonService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.league.league_infos.common.constants.ErrorMessagesEnum.ERROR_BUSINESS_5;

@Service
public class MostRecentChampionHandler {

    private final DataDragonService dataDragonService;

    public MostRecentChampionHandler(DataDragonService dataDragonService) {
        this.dataDragonService = dataDragonService;
    }

    public ChampionDTO getMostRecentChampion() {
        List<ChampionDTO> newChamp = new ArrayList<>();

        // 20 dernières version
        List<String> twentyLastVersions = this.dataDragonService.getAllNumVersionsLol().stream().limit(20).toList();

        for (int i = 1; i < twentyLastVersions.size(); i++) {
            Map<String, ChampionDTO> currentData = this.dataDragonService.getChampionsLol(twentyLastVersions.get(i)).getData();
            Map<String, ChampionDTO> prevData = this.dataDragonService.getChampionsLol(twentyLastVersions.get(i - 1)).getData();
            List<String> listChampionsCurrentData = currentData.values().stream().map(ChampionDTO::getName).toList();

            newChamp = prevData.values().stream()
                    .filter(c -> !listChampionsCurrentData.contains(c.getName()))
                    .toList();

            if (newChamp.size() == 1) {
                return newChamp.getFirst();
            }
        }
        throw new BusinessException(ERROR_BUSINESS_5.getLibelle(), HttpStatus.NOT_FOUND);
    }
}
