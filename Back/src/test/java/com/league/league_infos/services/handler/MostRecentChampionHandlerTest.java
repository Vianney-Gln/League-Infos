package com.league.league_infos.services.handler;

import com.league.league_infos.common.exceptions.BusinessException;
import com.league.league_infos.dto.ddragon.ChampionDTO;
import com.league.league_infos.dto.ddragon.ChampionDataDTO;
import com.league.league_infos.services.api.DataDragonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MostRecentChampionHandlerTest {

    @Mock
    private DataDragonService dataDragonService;

    @InjectMocks
    private MostRecentChampionHandler mostRecentChampionHandler;

    @Test
    @DisplayName("Should throw a BusinessException if no champion is found within the last 20 versions.")
    void getMostRecentChampion_fail_1() {
        // GIVEN
        List<String> twentyLastVersions = List.of("1.0", "1.1", "1.2", "1.3", "1.4", "1.5", "1.6", "1.7", "1.8", "1.9", "1.10",
                "2.0", "2.1", "2.2", "2.3", "2.4", "2.5", "2.6", "2.7", "2.8");

        when(dataDragonService.getAllNumVersionsLol()).thenReturn(twentyLastVersions);
        when(dataDragonService.getChampionsLol(anyString())).thenReturn(new ChampionDataDTO());

        // WHEN + THEN
        assertThatThrownBy(() -> mostRecentChampionHandler.getMostRecentChampion())
                .isInstanceOf(BusinessException.class)
                .hasMessage("Pas de nouveau champion trouvé au cours des 20 dernières versions");

        verify(dataDragonService).getAllNumVersionsLol();
        twentyLastVersions.forEach(version -> verify(dataDragonService).getChampionsLol(version));
    }

    @Test
    @DisplayName("Should return the most recent champion within the last 20 versions")
    void getMostRecentChampion_sucess_1() {
        // GIVEN
        List<String> twentyLastVersions = List.of("1.0", "1.1", "1.2", "1.3", "1.4", "1.5", "1.6", "1.7", "1.8", "1.9", "1.10",
                "2.0", "2.1", "2.2", "2.3", "2.4", "2.5", "2.6", "2.7", "2.8");

        var championDataV1 = this.generateChampionData(twentyLastVersions.getFirst());
        var championDataV2 = this.generateChampionData(twentyLastVersions.get(1));
        var championDataV3 = this.generateChampionData(twentyLastVersions.get(2));
        var championDataV4 = this.generateChampionData(twentyLastVersions.get(3));
        var championDataV5 = this.generateChampionData(twentyLastVersions.get(4));
        var championDataV6 = this.generateChampionData(twentyLastVersions.get(5));
        var championDataV7 = this.generateChampionData(twentyLastVersions.get(6));
        var championDataV8 = this.generateChampionData(twentyLastVersions.get(7));
        var championDataV9 = this.generateChampionData(twentyLastVersions.get(8));
        var championDataV10 = this.generateChampionData(twentyLastVersions.get(9));
        var championDataV11 = this.generateChampionData(twentyLastVersions.get(10));
        var championDataV12 = this.generateChampionData(twentyLastVersions.get(11));
        var championDataV13 = this.generateChampionData(twentyLastVersions.get(12));
        var championDataV14 = this.generateChampionData(twentyLastVersions.get(13));
        var championDataV15 = this.generateChampionData(twentyLastVersions.get(14));

        var championDataV16 = this.generateChampionData(twentyLastVersions.get(15));
        ChampionDTO championDTO = new ChampionDTO();
        championDTO.setName("Shyvanna");
        championDTO.setVersion("2.4");
        championDataV16.getData().put("Shyvanna", championDTO);

        var championDataV17 = this.generateChampionData(twentyLastVersions.get(16));
        var championDataV18 = this.generateChampionData(twentyLastVersions.get(17));
        var championDataV19 = this.generateChampionData(twentyLastVersions.get(18));
        var championDataV20 = this.generateChampionData(twentyLastVersions.getLast());

        when(dataDragonService.getAllNumVersionsLol()).thenReturn(twentyLastVersions);
        when(dataDragonService.getChampionsLol(anyString()))
                .thenReturn(championDataV1)
                .thenReturn(championDataV2)
                .thenReturn(championDataV3)
                .thenReturn(championDataV4)
                .thenReturn(championDataV5)
                .thenReturn(championDataV6)
                .thenReturn(championDataV7)
                .thenReturn(championDataV8)
                .thenReturn(championDataV9)
                .thenReturn(championDataV10)
                .thenReturn(championDataV11)
                .thenReturn(championDataV12)
                .thenReturn(championDataV13)
                .thenReturn(championDataV14)
                .thenReturn(championDataV15)
                .thenReturn(championDataV16)
                .thenReturn(championDataV17)
                .thenReturn(championDataV18)
                .thenReturn(championDataV19)
                .thenReturn(championDataV20);

        // WHEN
        ChampionDTO mostRecentChamp = mostRecentChampionHandler.getMostRecentChampion();

        // THEN
        assertThat(mostRecentChamp).isNotNull().extracting("name").isEqualTo("Shyvanna");
        verify(dataDragonService).getAllNumVersionsLol();
        twentyLastVersions.forEach(version -> verify(dataDragonService).getChampionsLol(version));
    }

    private ChampionDataDTO generateChampionData(String version) {
        Map<String, ChampionDTO> data = new HashMap<>();

        ChampionDTO champ1 = new ChampionDTO();
        champ1.setName("Ahri");
        data.put("Ahri", champ1);

        ChampionDTO champ2 = new ChampionDTO();
        champ2.setName("Aatrox");
        data.put("Aatrox", champ2);

        var champData = new ChampionDataDTO();
        champData.setVersion(version);
        champData.setData(data);
        return champData;
    }
}
