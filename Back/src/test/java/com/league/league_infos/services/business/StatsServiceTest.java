package com.league.league_infos.services.business;

import com.league.league_infos.common.exceptions.BusinessException;
import com.league.league_infos.models.dto.BannedChampionDTO;
import com.league.league_infos.models.dto.FeaturedGameInfoDTO;
import com.league.league_infos.models.dto.FeaturedGamesDTO;
import com.league.league_infos.services.api.FeaturedGameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatsServiceTest {

    @InjectMocks
    private StatsService statsService;

    @Mock
    private FeaturedGameService featuredGameService;

    @Test
    @DisplayName("Should return the most banned id champion")
    void calculateMostBannedChampion_success() {

        // GIVEN
        BannedChampionDTO bannedChampion1 = new BannedChampionDTO.Builder().championId(200).pickTurn(1).teamId(10).build();
        BannedChampionDTO bannedChampion2 = new BannedChampionDTO.Builder().championId(198).pickTurn(2).teamId(20).build();
        BannedChampionDTO bannedChampion3 = new BannedChampionDTO.Builder().championId(-1).pickTurn(3).teamId(20).build();
        BannedChampionDTO bannedChampion4 = new BannedChampionDTO.Builder().championId(200).pickTurn(4).teamId(10).build();
        BannedChampionDTO bannedChampion5 = new BannedChampionDTO.Builder().championId(200).pickTurn(5).teamId(10).build();
        BannedChampionDTO bannedChampion6 = new BannedChampionDTO.Builder().championId(200).pickTurn(6).teamId(20).build();
        BannedChampionDTO bannedChampion7 = new BannedChampionDTO.Builder().championId(200).pickTurn(7).teamId(20).build();
        BannedChampionDTO bannedChampion8 = new BannedChampionDTO.Builder().championId(101).pickTurn(8).teamId(10).build();
        BannedChampionDTO bannedChampion9 = new BannedChampionDTO.Builder().championId(0).pickTurn(9).teamId(10).build();
        BannedChampionDTO bannedChampion10 = new BannedChampionDTO.Builder().championId(33).pickTurn(10).teamId(20).build();

        FeaturedGameInfoDTO featuredGameInfo = new FeaturedGameInfoDTO.Builder()
                .bannedChampions(List.of(bannedChampion1, bannedChampion2, bannedChampion3, bannedChampion4, bannedChampion5, bannedChampion6, bannedChampion7, bannedChampion8,
                        bannedChampion9, bannedChampion10))
                .build();
        FeaturedGamesDTO featuredGamesDTO = new FeaturedGamesDTO.Builder()
                .gameList(List.of(featuredGameInfo, featuredGameInfo, featuredGameInfo, featuredGameInfo, featuredGameInfo))
                .build();

        when(featuredGameService.getFeaturedGames()).thenReturn(ResponseEntity.ok(featuredGamesDTO));

        // WHEN
        Long result = statsService.calculateMostBannedChampion();

        // THEN
        assertThat(result).isEqualTo(200L);
        verify(featuredGameService, times(1)).getFeaturedGames();
    }

    @Test
    @DisplayName("Should throw Business Exception if getFeaturedGames get non one id champion banned (-1 or 0 only)")
    void calculateMostBannedChampion_fail_1() {
        // GIVEN
        BannedChampionDTO bannedChampion1 = new BannedChampionDTO.Builder().championId(0).pickTurn(1).teamId(10).build();
        BannedChampionDTO bannedChampion2 = new BannedChampionDTO.Builder().championId(0).pickTurn(2).teamId(20).build();
        BannedChampionDTO bannedChampion3 = new BannedChampionDTO.Builder().championId(0).pickTurn(3).teamId(20).build();
        BannedChampionDTO bannedChampion4 = new BannedChampionDTO.Builder().championId(-1).pickTurn(4).teamId(10).build();
        BannedChampionDTO bannedChampion5 = new BannedChampionDTO.Builder().championId(-1).pickTurn(5).teamId(10).build();
        BannedChampionDTO bannedChampion6 = new BannedChampionDTO.Builder().championId(0).pickTurn(6).teamId(20).build();
        BannedChampionDTO bannedChampion7 = new BannedChampionDTO.Builder().championId(0).pickTurn(7).teamId(20).build();
        BannedChampionDTO bannedChampion8 = new BannedChampionDTO.Builder().championId(0).pickTurn(8).teamId(10).build();
        BannedChampionDTO bannedChampion9 = new BannedChampionDTO.Builder().championId(0).pickTurn(9).teamId(10).build();
        BannedChampionDTO bannedChampion10 = new BannedChampionDTO.Builder().championId(0).pickTurn(10).teamId(20).build();

        FeaturedGameInfoDTO featuredGameInfo = new FeaturedGameInfoDTO.Builder()
                .bannedChampions(List.of(bannedChampion1, bannedChampion2, bannedChampion3, bannedChampion4, bannedChampion5, bannedChampion6, bannedChampion7, bannedChampion8,
                        bannedChampion9, bannedChampion10))
                .build();
        FeaturedGamesDTO featuredGamesDTO = new FeaturedGamesDTO.Builder()
                .gameList(List.of(featuredGameInfo, featuredGameInfo, featuredGameInfo, featuredGameInfo, featuredGameInfo))
                .build();
        when(featuredGameService.getFeaturedGames()).thenReturn(ResponseEntity.ok(featuredGamesDTO));

        // WHEN + THEN
        assertThatThrownBy(() -> statsService.calculateMostBannedChampion())
                .isInstanceOf(BusinessException.class)
                .hasMessage("Le champion le plus bannis n'a pas pu être récupéré.");
    }

    @Test
    @DisplayName("Should throw Business Exception if getFeaturedGames is null")
    void calculateMostBannedChampion_fail_2() {
        // GIVEN
        when(featuredGameService.getFeaturedGames()).thenReturn(ResponseEntity.ok(null));

        // WHEN + THEN
        assertThatThrownBy(() -> statsService.calculateMostBannedChampion())
                .isInstanceOf(BusinessException.class)
                .hasMessage("Le champion le plus bannis n'a pas pu être récupéré.");
    }
}
