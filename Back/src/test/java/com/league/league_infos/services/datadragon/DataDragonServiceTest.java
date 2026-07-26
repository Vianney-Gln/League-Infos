package com.league.league_infos.services.datadragon;

import com.league.league_infos.common.constants.DataDragonUrls;
import com.league.league_infos.dto.ddragon.ChampionDataDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataDragonServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DataDragonServiceImpl dataDragonService;

    @Test
    @DisplayName("Should return all num versions")
    void getAllNumVersionsLol_success_1() {
        // GIVEN
        when(restTemplate.exchange(
                DataDragonUrls.DDRAGON_NUM_VERSIONS_API_URL,
                HttpMethod.GET,
                null,
                String[].class))
                .thenReturn(ResponseEntity.ok(new String[]{"1.0", "1.1", "1.2"}));

        // WHEN
        List<String> numVersions = dataDragonService.getAllNumVersionsLol();

        // THEN
        assertThat(numVersions)
                .isNotEmpty()
                .hasSize(3)
                .containsExactly("1.0", "1.1", "1.2");

        verify(restTemplate).exchange(DataDragonUrls.DDRAGON_NUM_VERSIONS_API_URL, HttpMethod.GET, null, String[].class);
    }

    @Test
    @DisplayName("Should return all champions data")
    void getChampionsLol_success_1() {
        // GIVEN
        ChampionDataDTO championDataDTO = new ChampionDataDTO();
        championDataDTO.setVersion("1.10");
        when(restTemplate.exchange(
                DataDragonUrls.DDRAGON_GET_CHAMPIONS_API_URL.formatted("1.10"),
                HttpMethod.GET,
                null,
                ChampionDataDTO.class))
                .thenReturn(ResponseEntity.ok(championDataDTO));

        // WHEN
        ChampionDataDTO result = dataDragonService.getChampionsLol("1.10");

        // THEN
        assertThat(result).isNotNull().extracting("version").isEqualTo("1.10");
        verify(restTemplate).exchange(DataDragonUrls.DDRAGON_GET_CHAMPIONS_API_URL.formatted("1.10"), HttpMethod.GET, null, ChampionDataDTO.class);
    }

}
