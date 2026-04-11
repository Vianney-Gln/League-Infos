package com.league.league_infos.dto.ia;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenerationCommentaryDTO {
    private ContextGenerationCommentaryDTO contextGenerationCommentaryDTO;
    private List<EventMatchDTO> eventsMatchDto;

    public List<EventMatchDTO> getEventsMatchDto() {
        return eventsMatchDto;
    }

    public void setEventsMatchDto(List<EventMatchDTO> eventsMatchDto) {
        this.eventsMatchDto = eventsMatchDto;
    }

    public ContextGenerationCommentaryDTO getContextGenerationCommentaryDTO() {
        return contextGenerationCommentaryDTO;
    }

    public void setContextGenerationCommentaryDTO(ContextGenerationCommentaryDTO contextGenerationCommentaryDTO) {
        this.contextGenerationCommentaryDTO = contextGenerationCommentaryDTO;
    }

    public static class Builder {
        private ContextGenerationCommentaryDTO contextGenerationCommentaryDTO;
        private List<EventMatchDTO> eventsMatchDto;

        public GenerationCommentaryDTO.Builder contextGenerationCommentaryDTO(ContextGenerationCommentaryDTO contextGenerationCommentaryDTO) {
            this.contextGenerationCommentaryDTO = contextGenerationCommentaryDTO;
            return this;
        }

        public GenerationCommentaryDTO.Builder eventsMatchDto(List<EventMatchDTO> eventsMatchDto) {
            this.eventsMatchDto = eventsMatchDto;
            return this;
        }

        public GenerationCommentaryDTO build() {
            GenerationCommentaryDTO generationCommentaryDTO = new GenerationCommentaryDTO();
            generationCommentaryDTO.setEventsMatchDto(this.eventsMatchDto);
            generationCommentaryDTO.setContextGenerationCommentaryDTO(this.contextGenerationCommentaryDTO);
            return generationCommentaryDTO;
        }
    }
}
