package com.league.league_infos.services.handler;

import com.league.league_infos.common.exceptions.BusinessException;
import com.league.league_infos.dto.ddragon.ChampionDTO;
import com.league.league_infos.dto.ddragon.ChampionDataDTO;
import com.league.league_infos.services.api.DataDragonService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.league.league_infos.common.constants.ErrorMessagesEnum.ERROR_BUSINESS_5;

@Service
public class MostRecentChampionHandler {

    private final DataDragonService dataDragonService;

    public MostRecentChampionHandler(DataDragonService dataDragonService) {
        this.dataDragonService = dataDragonService;
    }

    public ChampionDTO getMostRecentChampion() {
        List<String> twentyLastVersions = this.dataDragonService.getAllNumVersionsLol().stream().limit(20).toList();
        List<ChampionDataDTO> championDataDTOList = twentyLastVersions.stream().map(this.dataDragonService::getChampionsLol).toList();

        for (int i = 1; i < championDataDTOList.size(); i++) {
            var prevListChampData = championDataDTOList.get(i - 1).getData();
            var currentListChampData = championDataDTOList.get(i).getData();

            if (prevListChampData != null && currentListChampData != null) {
                if (prevListChampData.size() > currentListChampData.size()) {
                    return prevListChampData.entrySet()
                            .stream()
                            .filter(entry -> !currentListChampData.containsKey(entry.getKey()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                            .values()
                            .stream()
                            .findFirst()
                            .orElseThrow(() -> new BusinessException(ERROR_BUSINESS_5.getLibelle(), HttpStatus.NOT_FOUND));
                }
            }
        }
        throw new BusinessException(ERROR_BUSINESS_5.getLibelle(), HttpStatus.NOT_FOUND);
    }

}
