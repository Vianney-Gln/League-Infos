package com.league.league_infos.services.persistence;

import com.league.league_infos.dao.entity.ChallengesEntity;
import com.league.league_infos.dao.entity.InfoMatchEntity;
import com.league.league_infos.dao.entity.MetaDataEntity;
import com.league.league_infos.dao.entity.ParticipantMatchEntity;
import com.league.league_infos.dao.entity.TeamEntity;
import com.league.league_infos.dao.repository.InfoMatchRepository;
import com.league.league_infos.dto.match.ChallengesDTO;
import com.league.league_infos.dto.match.InfoMatchDTO;
import com.league.league_infos.dto.match.MatchDTO;
import com.league.league_infos.dto.match.MetadataDTO;
import com.league.league_infos.dto.match.ParticipantMatchDTO;
import com.league.league_infos.dto.match.TeamDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistoryPersistenceBddTest {

    @Mock
    private InfoMatchRepository infoMatchRepository;

    @InjectMocks
    private HistoryPersistenceBdd historyPersistenceBdd;

    @Captor
    private ArgumentCaptor<List<InfoMatchEntity>> listInfoMatchEntityArgumentCaptor;

    @Test
    @DisplayName("Must persist all match history that doesn't exist in db")
    void persistMatchHistory_success_1() {
        // GIVEN
        ParticipantMatchDTO participantMatchDTO = new ParticipantMatchDTO.Builder()
                .participantId(10)
                .win(true)
                .challenges(new ChallengesDTO.Builder()
                        .kda(20F)
                        .gameLength(1333F)
                        .build())
                .build();

        MatchDTO match1 = new MatchDTO.Builder()
                .info(new InfoMatchDTO.Builder()
                        .gameId(10L)
                        .build())
                .build();

        MatchDTO match2 = new MatchDTO.Builder()
                .info(new InfoMatchDTO.Builder()
                        .gameId(20L)
                        .participants(List.of(participantMatchDTO))
                        .teams(List.of(new TeamDTO.Builder()
                                .teamId(10)
                                .win(true)
                                .build()))
                        .build())
                .metadata(new MetadataDTO.Builder()
                        .matchId("100")
                        .dataVersion("version")
                        .build())
                .build();

        when(infoMatchRepository.findByGameId(10L)).thenReturn(new InfoMatchEntity());
        when(infoMatchRepository.findByGameId(20L)).thenReturn(null);

        // WHEN
        historyPersistenceBdd.persistMatchHistory(List.of(match1, match2));

        // THEN
        verify(infoMatchRepository, times(1)).findByGameId(10L);
        verify(infoMatchRepository, times(1)).findByGameId(20L);
        verify(infoMatchRepository, times(1)).saveAll(listInfoMatchEntityArgumentCaptor.capture());

        assertThat(listInfoMatchEntityArgumentCaptor.getValue()).isNotEmpty().hasSize(1)
                .extracting("gameId").containsExactly(20L);
    }

    @Test
    @DisplayName("Must not persist any match history if listMatch is empty")
    void persistMatchHistory_success_2() {
        // WHEN
        historyPersistenceBdd.persistMatchHistory(Collections.emptyList());

        // THEN
        verify(infoMatchRepository, times(1)).saveAll(listInfoMatchEntityArgumentCaptor.capture());
        assertThat(listInfoMatchEntityArgumentCaptor.getValue()).isEmpty();
    }

    @Test
    @DisplayName("Must return a list of MatchDTO")
    void getMatchHistoryByGameIds_success_1() {
        // GIVEN
        InfoMatchEntity infoMatchEntity = new InfoMatchEntity();

        ChallengesEntity challengesEntity = new ChallengesEntity();
        challengesEntity.setKda(13F);
        challengesEntity.setGameLength(1380F);

        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setWin(true);
        teamEntity.setIdTeam(20L);
        teamEntity.setTeamIdNumero(20);

        MetaDataEntity metaDataEntity = new MetaDataEntity();
        metaDataEntity.setDataVersion("version");
        metaDataEntity.setMatchId("#TAG_10");

        ParticipantMatchEntity participantMatchEntity = new ParticipantMatchEntity();
        participantMatchEntity.setWin(true);
        participantMatchEntity.setChallengesEntity(challengesEntity);
        participantMatchEntity.setMetaDataEntity(metaDataEntity);
        participantMatchEntity.setIdParticipant(15L);

        infoMatchEntity.setParticipantMatchEntityList(List.of(participantMatchEntity));
        infoMatchEntity.setTeamEntity(List.of(teamEntity));

        when(infoMatchRepository.findByGameId(anyLong())).thenReturn(infoMatchEntity);

        // WHEN
        List<MatchDTO> result = historyPersistenceBdd.getMatchHistoryByGameIds(List.of("#TAG_10"));

        // THEN
        verify(infoMatchRepository, times(1)).findByGameId(10L);
        assertThat(result).isNotEmpty().hasSize(1);

        assertThat(result.getFirst().getInfo()).isNotNull();
        assertThat(result.getFirst().getInfo().getParticipants())
                .isNotEmpty()
                .hasSize(1)
                .extracting("participantId")
                .containsExactly(15);

        assertThat(result.getFirst().getInfo().getParticipants().getFirst().getChallenges())
                .isNotNull()
                .extracting("kda", "gameLength")
                .containsExactly(13F, 1380F);

        assertThat(result.getFirst().getMetadata())
                .isNotNull()
                .extracting("dataVersion", "matchId")
                .containsExactly("version", "#TAG_10");

        assertThat(result.getFirst().getInfo().getTeams())
                .isNotEmpty()
                .hasSize(1)
                .extracting("win", "teamId")
                .containsExactly(tuple(true, 20));
    }

    @Test
    @DisplayName("Don't add object null in the list")
    void getMatchHistoryByGameIds_success_2() {
        // GIVEN
        when(infoMatchRepository.findByGameId(anyLong())).thenReturn(null);

        // WHEN
        List<MatchDTO> result = historyPersistenceBdd.getMatchHistoryByGameIds(List.of("#TAG_10"));

        // THEN
        verify(infoMatchRepository, times(1)).findByGameId(10L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Must return true if the repository returns a match less than one hour old.")
    void wasCreatedWithinLastHour_success_1() {

        // GIVEN
        when(infoMatchRepository.findRecentsMatchByPuuidAndQueue(anyString(), any(LocalDateTime.class), any(Integer.class)))
                .thenReturn(List.of(new InfoMatchEntity()));

        // WHEN
        boolean result = historyPersistenceBdd.wasCreatedWithinLastHour("puuid", 420);
        verify(infoMatchRepository, times(1)).findRecentsMatchByPuuidAndQueue(eq("puuid"), any(LocalDateTime.class), eq(420));
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Must return false if the repository doesn't returns a match less than one hour old.")
    void wasCreatedWithinLastHour_success_2() {

        // GIVEN
        when(infoMatchRepository.findRecentsMatchByPuuidAndQueue(anyString(), any(LocalDateTime.class), any(Integer.class)))
                .thenReturn(Collections.emptyList());

        // WHEN
        boolean result = historyPersistenceBdd.wasCreatedWithinLastHour("puuid", 420);
        verify(infoMatchRepository, times(1)).findRecentsMatchByPuuidAndQueue(eq("puuid"), any(LocalDateTime.class), eq(420));
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should return list of MatchDTO")
    void findAllMatchByPuuidAndQueue_success() {
        // GIVEN
        InfoMatchEntity infoMatchEntity = new InfoMatchEntity();

        ChallengesEntity challengesEntity = new ChallengesEntity();
        challengesEntity.setKda(13F);
        challengesEntity.setGameLength(1380F);

        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setWin(true);
        teamEntity.setIdTeam(20L);
        teamEntity.setTeamIdNumero(20);

        MetaDataEntity metaDataEntity = new MetaDataEntity();
        metaDataEntity.setDataVersion("version");
        metaDataEntity.setMatchId("#TAG_10");

        ParticipantMatchEntity participantMatchEntity = new ParticipantMatchEntity();
        participantMatchEntity.setWin(true);
        participantMatchEntity.setChallengesEntity(challengesEntity);
        participantMatchEntity.setMetaDataEntity(metaDataEntity);
        participantMatchEntity.setIdParticipant(15L);

        infoMatchEntity.setParticipantMatchEntityList(List.of(participantMatchEntity));
        infoMatchEntity.setTeamEntity(List.of(teamEntity));

        when(infoMatchRepository.findAllMatchByPuuidAndQueue(anyString(), any(Integer.class))).thenReturn(List.of(infoMatchEntity));

        // WHEN
        List<MatchDTO> result = historyPersistenceBdd.findAllMatchByPuuidAndQueue("uuid", 420);

        // THEN
        verify(infoMatchRepository, times(1)).findAllMatchByPuuidAndQueue("uuid", 420);
        assertThat(result).isNotEmpty().hasSize(1);

        assertThat(result.getFirst().getInfo()).isNotNull();
        assertThat(result.getFirst().getInfo().getParticipants())
                .isNotEmpty()
                .hasSize(1)
                .extracting("participantId")
                .containsExactly(15);

        assertThat(result.getFirst().getInfo().getParticipants().getFirst().getChallenges())
                .isNotNull()
                .extracting("kda", "gameLength")
                .containsExactly(13F, 1380F);

        assertThat(result.getFirst().getMetadata())
                .isNotNull()
                .extracting("dataVersion", "matchId")
                .containsExactly("version", "#TAG_10");

        assertThat(result.getFirst().getInfo().getTeams())
                .isNotEmpty()
                .hasSize(1)
                .extracting("win", "teamId")
                .containsExactly(tuple(true, 20));
    }

    @Test
    @DisplayName("Should return list of MatchDTO")
    void findAllMatchByPuuidAndQueueBeforeGivenDate_success() {
        // GIVEN
        InfoMatchEntity infoMatchEntity = new InfoMatchEntity();

        ChallengesEntity challengesEntity = new ChallengesEntity();
        challengesEntity.setKda(13F);
        challengesEntity.setGameLength(1380F);

        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setWin(true);
        teamEntity.setIdTeam(20L);
        teamEntity.setTeamIdNumero(20);

        MetaDataEntity metaDataEntity = new MetaDataEntity();
        metaDataEntity.setDataVersion("version");
        metaDataEntity.setMatchId("#TAG_10");

        ParticipantMatchEntity participantMatchEntity = new ParticipantMatchEntity();
        participantMatchEntity.setWin(true);
        participantMatchEntity.setChallengesEntity(challengesEntity);
        participantMatchEntity.setMetaDataEntity(metaDataEntity);
        participantMatchEntity.setIdParticipant(15L);

        infoMatchEntity.setParticipantMatchEntityList(List.of(participantMatchEntity));
        infoMatchEntity.setTeamEntity(List.of(teamEntity));

        when(infoMatchRepository.findAllMatchByPuuidAndQueueBeforeGivenDate(anyString(), anyLong(), any(Integer.class))).thenReturn(List.of(infoMatchEntity));

        // WHEN
        List<MatchDTO> result = historyPersistenceBdd.findAllMatchByPuuidAndQueueBeforeGivenDate("uuid", 175125125125L, 420);

        // THEN
        verify(infoMatchRepository, times(1)).findAllMatchByPuuidAndQueueBeforeGivenDate("uuid", 175125125125L, 420);
        assertThat(result).isNotEmpty().hasSize(1);

        assertThat(result.getFirst().getInfo()).isNotNull();
        assertThat(result.getFirst().getInfo().getParticipants())
                .isNotEmpty()
                .hasSize(1)
                .extracting("participantId")
                .containsExactly(15);

        assertThat(result.getFirst().getInfo().getParticipants().getFirst().getChallenges())
                .isNotNull()
                .extracting("kda", "gameLength")
                .containsExactly(13F, 1380F);

        assertThat(result.getFirst().getMetadata())
                .isNotNull()
                .extracting("dataVersion", "matchId")
                .containsExactly("version", "#TAG_10");

        assertThat(result.getFirst().getInfo().getTeams())
                .isNotEmpty()
                .hasSize(1)
                .extracting("win", "teamId")
                .containsExactly(tuple(true, 20));
    }
}
