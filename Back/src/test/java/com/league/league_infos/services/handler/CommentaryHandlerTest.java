package com.league.league_infos.services.handler;

import com.league.league_infos.dto.ia.ChoiceDTO;
import com.league.league_infos.dto.ia.CommentaryMatchDTO;
import com.league.league_infos.dto.ia.GenerationCommentaryDTO;
import com.league.league_infos.dto.ia.MessageDTO;
import com.league.league_infos.dto.ia.OpenAiResponseDTO;
import com.league.league_infos.services.api.GenerationCommentaryService;
import com.league.league_infos.services.api.IAService;
import com.league.league_infos.services.spi.CommentaryMatchPersistence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentaryHandlerTest {

    @Mock
    private GenerationCommentaryService generationCommentaryService;

    @Mock
    private IAService iaService;

    @Mock
    private CommentaryMatchPersistence commentaryMatchPersistence;

    @InjectMocks
    private CommentaryHandler commentaryHandler;

    @Captor
    private ArgumentCaptor<GenerationCommentaryDTO> generationCommentaryDTOArgumentCaptor;

    @Captor
    private ArgumentCaptor<CommentaryMatchDTO> commentaryMatchDTOArgumentCaptor;

    @Test
    @DisplayName("should get commentary from bdd and don't try to generate it from IA, case exist into bdd")
    void getCommentaryFromAGameByPlayer_success_1() throws Exception {
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
        when(commentaryMatchPersistence.getCommentaryByGameIdAndPuuid(anyLong(), anyString())).thenReturn(Optional.of(commentaryMatchDTO));

        // WHEN
        CommentaryMatchDTO result = commentaryHandler.getCommentaryFromAGameByPlayer(545789456L, "puuid", "match id 545789456L", "Ninja");

        // THEN
        assertThat(result).isNotNull()
                .extracting("created", "model", "content", "object", "id", "serviceTier", "fingerPrint")
                .containsExactly(1762005666L, "model", "content", "object", "10", "service tier", "fingerprint");

        verify(commentaryMatchPersistence, times(1)).getCommentaryByGameIdAndPuuid(545789456L, "puuid");
        verify(generationCommentaryService, never()).getGenerationCommentaryDTO(any(), any(), any(), any());
        verify(iaService, never()).generateCommentaries(any());
    }

    @Test
    @DisplayName("should get commentary after it was generatede from IA, case not exist into bdd yet")
    void getCommentaryFromAGameByPlayer_success_2() throws Exception {
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


        OpenAiResponseDTO openAiResponseDTO = new OpenAiResponseDTO.Builder()
                .id("10")
                .created(1762005666L)
                .model("model")
                .object("object")
                .serviceTier("service tier")
                .fingerPrint("fingerprint")
                .choices(List.of(new ChoiceDTO.Builder()
                        .index(0)
                        .message(new MessageDTO.Builder()
                                .role("role")
                                .content("content")
                                .build())
                        .build()))
                .build();

        when(commentaryMatchPersistence.getCommentaryByGameIdAndPuuid(anyLong(), anyString())).thenReturn(Optional.empty());
        when(generationCommentaryService.getGenerationCommentaryDTO(anyLong(), anyString(), anyString(), anyString())).thenReturn(new GenerationCommentaryDTO());
        when(iaService.generateCommentaries(any(GenerationCommentaryDTO.class))).thenReturn(openAiResponseDTO);
        when(commentaryMatchPersistence.persist(any(CommentaryMatchDTO.class), anyLong(), anyString())).thenReturn(commentaryMatchDTO);


        // WHEN
        CommentaryMatchDTO result = commentaryHandler.getCommentaryFromAGameByPlayer(545789456L, "puuid", "match id 545789456L", "Ninja");

        // THEN
        assertThat(result).isNotNull()
                .extracting("created", "model", "content", "object", "id", "serviceTier", "fingerPrint")
                .containsExactly(1762005666L, "model", "content", "object", "10", "service tier", "fingerprint");

        verify(commentaryMatchPersistence, times(1)).getCommentaryByGameIdAndPuuid(545789456L, "puuid");
        verify(generationCommentaryService, times(1)).getGenerationCommentaryDTO(545789456L, "puuid", "match id 545789456L", "Ninja");

        verify(iaService, times(1)).generateCommentaries(generationCommentaryDTOArgumentCaptor.capture());
        assertThat(generationCommentaryDTOArgumentCaptor.getValue()).isNotNull();

        verify(commentaryMatchPersistence, times(1)).persist(commentaryMatchDTOArgumentCaptor.capture(), eq(545789456L), eq("puuid"));
        assertThat(commentaryMatchDTOArgumentCaptor.getValue()).isNotNull()
                .extracting("created", "model", "content", "object", "id", "serviceTier", "fingerPrint")
                .containsExactly(1762005666L, "model", "content", "object", "10", "service tier", "fingerprint");
    }
}
