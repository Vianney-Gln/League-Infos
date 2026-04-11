package com.league.league_infos.services.persistence;

import com.league.league_infos.dao.entity.CommentaryMatchEntity;
import com.league.league_infos.dao.entity.ParticipantMatchEntity;
import com.league.league_infos.dao.repository.CommentaryMatchRepository;
import com.league.league_infos.dao.repository.ParticipantMatchRepository;
import com.league.league_infos.dto.ia.CommentaryMatchDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentaryMatchPersistenceBddTest {

    @Mock
    private ParticipantMatchRepository participantMatchRepository;

    @Mock
    private CommentaryMatchRepository commentaryMatchRepository;

    @InjectMocks
    private CommentaryMatchPersistenceBdd commentaryMatchPersistenceBdd;

    @Captor
    private ArgumentCaptor<CommentaryMatchEntity> commentaryMatchEntityArgumentCaptor;

    @Test
    @DisplayName("should get commentary successfully")
    void getCommentaryByGameIdAndPuuid_success() {
        // GIVEN
        CommentaryMatchEntity commentaryMatchEntity = new CommentaryMatchEntity();
        commentaryMatchEntity.setCreatedDate(1762005666L);
        commentaryMatchEntity.setModel("model chat gpt");
        commentaryMatchEntity.setContent("le compte rendu du match du joueur");
        commentaryMatchEntity.setObject("object");
        commentaryMatchEntity.setCommentaryMatchId("10");
        commentaryMatchEntity.setFingerPrint("fingerPrint");
        commentaryMatchEntity.setServiceTier("serviceTier");
        when(commentaryMatchRepository.getByGameIdAndPuuid(anyLong(), anyString())).thenReturn(Optional.of(commentaryMatchEntity));

        // WHEN
        Optional<CommentaryMatchDTO> result = commentaryMatchPersistenceBdd.getCommentaryByGameIdAndPuuid(30L, "puuid");

        // THEN
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get())
                .extracting("created", "model", "content", "object", "id", "fingerPrint", "serviceTier")
                .containsExactly(1762005666L, "model chat gpt", "le compte rendu du match du joueur", "object", "10", "fingerPrint", "serviceTier");

        verify(commentaryMatchRepository, times(1)).getByGameIdAndPuuid(30L, "puuid");

    }

    @Test
    @DisplayName("should persist commentary successfully")
    void persist_succes() {
        // GIVEN
        CommentaryMatchDTO commentaryMatchDTO = new CommentaryMatchDTO.Builder()
                .id("10")
                .created(1762005666L)
                .model("model")
                .content("content")
                .object("object")
                .serviceTier("service tier")
                .fingerPrint("fingerprint")
                .build();

        CommentaryMatchEntity commentaryMatchEntity = new CommentaryMatchEntity();
        commentaryMatchEntity.setCreatedDate(1762005666L);
        commentaryMatchEntity.setModel("model chat gpt");
        commentaryMatchEntity.setContent("le compte rendu du match du joueur");
        commentaryMatchEntity.setObject("object");
        commentaryMatchEntity.setCommentaryMatchId("10");
        commentaryMatchEntity.setServiceTier("service tier");
        commentaryMatchEntity.setFingerPrint("fingerprint");

        ParticipantMatchEntity participantMatchEntity = new ParticipantMatchEntity();
        participantMatchEntity.setIdParticipant(275L);
        participantMatchEntity.setPseudo("Ninja");
        participantMatchEntity.setPuuid("puuid");

        when(commentaryMatchRepository.save(any(CommentaryMatchEntity.class))).thenReturn(commentaryMatchEntity);
        when(participantMatchRepository.getParticipantByGameIdAndPuuid(anyLong(), anyString())).thenReturn(participantMatchEntity);

        // WHEN
        CommentaryMatchDTO result = commentaryMatchPersistenceBdd.persist(commentaryMatchDTO, 125125125L, "puuid");

        // THEN
        assertThat(result).isNotNull()
                .extracting("created", "model", "content", "object", "id", "serviceTier", "fingerPrint")
                .containsExactly(1762005666L, "model chat gpt", "le compte rendu du match du joueur", "object", "10", "service tier", "fingerprint");

        verify(participantMatchRepository, times(1)).getParticipantByGameIdAndPuuid(125125125L, "puuid");
        verify(commentaryMatchRepository, times(1)).save(commentaryMatchEntityArgumentCaptor.capture());

        assertThat(commentaryMatchEntityArgumentCaptor.getValue()).isNotNull()
                .extracting("commentaryMatchId", "object", "createdDate", "createdDate", "model", "content", "serviceTier", "fingerPrint")
                .containsExactly("10", "object", 1762005666L, 1762005666L, "model", "content", "service tier", "fingerprint");

        assertThat(commentaryMatchEntityArgumentCaptor.getValue().getParticipantMatchEntity())
                .isNotNull()
                .extracting("idParticipant", "pseudo", "puuid")
                .containsExactly(275L, "Ninja", "puuid");
    }
}
