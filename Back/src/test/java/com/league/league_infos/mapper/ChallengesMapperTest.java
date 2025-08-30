package com.league.league_infos.mapper;

import com.league.league_infos.dao.entity.ChallengesEntity;
import com.league.league_infos.dao.entity.InfoMatchEntity;
import com.league.league_infos.dao.entity.ParticipantMatchEntity;
import com.league.league_infos.dto.match.ChallengesDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChallengesMapperTest {

    @Test
    @DisplayName("Must map correctly")
    void challengesDtoToEntity_success() {
        // GIVEN
        ChallengesDTO challengesDTO = new ChallengesDTO.Builder().kda(27.5F).gameLength(1380F).build();
        ParticipantMatchEntity participantMatchEntity1 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity2 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity3 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity4 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity5 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity6 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity7 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity8 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity9 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity10 = new ParticipantMatchEntity();
        participantMatchEntity1.setIdParticipant(10L);
        participantMatchEntity2.setIdParticipant(20L);
        participantMatchEntity3.setIdParticipant(30L);
        participantMatchEntity4.setIdParticipant(40L);
        participantMatchEntity5.setIdParticipant(50L);
        participantMatchEntity6.setIdParticipant(60L);
        participantMatchEntity7.setIdParticipant(70L);
        participantMatchEntity8.setIdParticipant(80L);
        participantMatchEntity9.setIdParticipant(90L);
        participantMatchEntity10.setIdParticipant(100L);

        InfoMatchEntity infoMatchEntity = new InfoMatchEntity();
        infoMatchEntity.setParticipantMatchEntityList(List.of(
                participantMatchEntity1,
                participantMatchEntity2,
                participantMatchEntity3,
                participantMatchEntity4,
                participantMatchEntity5,
                participantMatchEntity6,
                participantMatchEntity7,
                participantMatchEntity8,
                participantMatchEntity9,
                participantMatchEntity10
        ));

        // WHEN
        ChallengesEntity result = ChallengesMapper.challengesDtoToEntity(challengesDTO, infoMatchEntity);

        // THEN
        assertThat(result).isNotNull().extracting("kda", "gameLength").containsExactly(27.5F, 1380F);
        assertThat(result.getParticipantMatchEntityList()).isNotEmpty().hasSize(10).extracting("idParticipant").containsExactly(
                10L,
                20L,
                30L,
                40L,
                50L,
                60L,
                70L,
                80L,
                90L,
                100L
        );
    }

    @Test
    @DisplayName("Must throw an exception if challengesDTO is null")
    void challengesDtoToEntity_fail_1() {
        // GIVEN
        ParticipantMatchEntity participantMatchEntity1 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity2 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity3 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity4 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity5 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity6 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity7 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity8 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity9 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity10 = new ParticipantMatchEntity();
        participantMatchEntity1.setIdParticipant(10L);
        participantMatchEntity2.setIdParticipant(20L);
        participantMatchEntity3.setIdParticipant(30L);
        participantMatchEntity4.setIdParticipant(40L);
        participantMatchEntity5.setIdParticipant(50L);
        participantMatchEntity6.setIdParticipant(60L);
        participantMatchEntity7.setIdParticipant(70L);
        participantMatchEntity8.setIdParticipant(80L);
        participantMatchEntity9.setIdParticipant(90L);
        participantMatchEntity10.setIdParticipant(100L);

        InfoMatchEntity infoMatchEntity = new InfoMatchEntity();
        infoMatchEntity.setParticipantMatchEntityList(List.of(
                participantMatchEntity1,
                participantMatchEntity2,
                participantMatchEntity3,
                participantMatchEntity4,
                participantMatchEntity5,
                participantMatchEntity6,
                participantMatchEntity7,
                participantMatchEntity8,
                participantMatchEntity9,
                participantMatchEntity10
        ));

        // WHEN + THEN
        assertThatThrownBy(() -> ChallengesMapper.challengesDtoToEntity(null, infoMatchEntity))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("challengesDTO cannot be null");
    }

    @Test
    @DisplayName("Must throw an exception if infoMatchEntity is null")
    void challengesDtoToEntity_fail_2() {
        // GIVEN
        ChallengesDTO challengesDTO = new ChallengesDTO.Builder().kda(27.5F).gameLength(1380F).build();

        // WHEN + THEN
        assertThatThrownBy(() -> ChallengesMapper.challengesDtoToEntity(challengesDTO, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("infoMatchEntity cannot be null");
    }

    @Test
    @DisplayName("Must throw an exception if challengesDTO.kda is null")
    void challengesDtoToEntity_fail_3() {
        // GIVEN
        ChallengesDTO challengesDTO = new ChallengesDTO.Builder().gameLength(1380F).build();
        ParticipantMatchEntity participantMatchEntity1 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity2 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity3 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity4 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity5 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity6 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity7 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity8 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity9 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity10 = new ParticipantMatchEntity();
        participantMatchEntity1.setIdParticipant(10L);
        participantMatchEntity2.setIdParticipant(20L);
        participantMatchEntity3.setIdParticipant(30L);
        participantMatchEntity4.setIdParticipant(40L);
        participantMatchEntity5.setIdParticipant(50L);
        participantMatchEntity6.setIdParticipant(60L);
        participantMatchEntity7.setIdParticipant(70L);
        participantMatchEntity8.setIdParticipant(80L);
        participantMatchEntity9.setIdParticipant(90L);
        participantMatchEntity10.setIdParticipant(100L);

        InfoMatchEntity infoMatchEntity = new InfoMatchEntity();
        infoMatchEntity.setParticipantMatchEntityList(List.of(
                participantMatchEntity1,
                participantMatchEntity2,
                participantMatchEntity3,
                participantMatchEntity4,
                participantMatchEntity5,
                participantMatchEntity6,
                participantMatchEntity7,
                participantMatchEntity8,
                participantMatchEntity9,
                participantMatchEntity10
        ));

        // WHEN + THEN
        assertThatThrownBy(() -> ChallengesMapper.challengesDtoToEntity(challengesDTO, infoMatchEntity))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("challengesDTO.kda cannot be null");
    }

    @Test
    @DisplayName("Must throw an exception if challengesDTO.gameLength is null")
    void challengesDtoToEntity_fail_4() {
        // GIVEN
        ChallengesDTO challengesDTO = new ChallengesDTO.Builder().kda(27.5F).build();
        ParticipantMatchEntity participantMatchEntity1 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity2 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity3 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity4 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity5 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity6 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity7 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity8 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity9 = new ParticipantMatchEntity();
        ParticipantMatchEntity participantMatchEntity10 = new ParticipantMatchEntity();
        participantMatchEntity1.setIdParticipant(10L);
        participantMatchEntity2.setIdParticipant(20L);
        participantMatchEntity3.setIdParticipant(30L);
        participantMatchEntity4.setIdParticipant(40L);
        participantMatchEntity5.setIdParticipant(50L);
        participantMatchEntity6.setIdParticipant(60L);
        participantMatchEntity7.setIdParticipant(70L);
        participantMatchEntity8.setIdParticipant(80L);
        participantMatchEntity9.setIdParticipant(90L);
        participantMatchEntity10.setIdParticipant(100L);

        InfoMatchEntity infoMatchEntity = new InfoMatchEntity();
        infoMatchEntity.setParticipantMatchEntityList(List.of(
                participantMatchEntity1,
                participantMatchEntity2,
                participantMatchEntity3,
                participantMatchEntity4,
                participantMatchEntity5,
                participantMatchEntity6,
                participantMatchEntity7,
                participantMatchEntity8,
                participantMatchEntity9,
                participantMatchEntity10
        ));

        // WHEN + THEN
        assertThatThrownBy(() -> ChallengesMapper.challengesDtoToEntity(challengesDTO, infoMatchEntity))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("challengesDTO.gameLength cannot be null");
    }


    @Test
    @DisplayName("Must map correctly")
    void challengesEntityToDTO_sucess() {
        // GIVEN
        ChallengesEntity challengesEntity = new ChallengesEntity();
        challengesEntity.setKda(34.4F);
        challengesEntity.setGameLength(1225F);

        // WHEN
        ChallengesDTO result = ChallengesMapper.challengesEntityToDTO(challengesEntity);

        // THEN
        assertThat(result).isNotNull().extracting("kda", "gameLength").containsExactly(34.4F, 1225F);
    }

    @Test
    @DisplayName("Must throw an exception if challengeEntity is null")
    void challengesEntityToDTO_fail_1() {
        // GIVEN + WHEN + THEN
        assertThatThrownBy(() -> ChallengesMapper.challengesEntityToDTO(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("challengesEntity cannot be null");
    }

    @Test
    @DisplayName("Must throw an exception if challengeEntity.gameLength is null")
    void challengesEntityToDTO_fail_2() {
        // GIVEN
        ChallengesEntity challengesEntity = new ChallengesEntity();
        challengesEntity.setKda(34.4F);

        // WHEN + THEN
        assertThatThrownBy(() -> ChallengesMapper.challengesEntityToDTO(challengesEntity))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("challengesEntity.gameLength cannot be null");
    }

    @Test
    @DisplayName("Must throw an exception if challengeEntity.kda is null")
    void challengesEntityToDTO_fail_3() {
        // GIVEN
        ChallengesEntity challengesEntity = new ChallengesEntity();
        challengesEntity.setGameLength(1225F);

        // WHEN + THEN
        assertThatThrownBy(() -> ChallengesMapper.challengesEntityToDTO(challengesEntity))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("challengesEntity.kda cannot be null");
    }
}
