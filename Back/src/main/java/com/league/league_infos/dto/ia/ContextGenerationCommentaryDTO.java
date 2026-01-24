package com.league.league_infos.dto.ia;

import java.util.List;

public class ContextGenerationCommentaryDTO {
    private String pseudoFocusedPlayer;
    private String puuidFocusedPlayer;
    private List<ParticipantGenerationCommentaryDTO> participants;

    public String getPseudoFocusedPlayer() {
        return pseudoFocusedPlayer;
    }

    public void setPseudoFocusedPlayer(String pseudoFocusedPlayer) {
        this.pseudoFocusedPlayer = pseudoFocusedPlayer;
    }

    public String getPuuidFocusedPlayer() {
        return puuidFocusedPlayer;
    }

    public void setPuuidFocusedPlayer(String puuidFocusedPlayer) {
        this.puuidFocusedPlayer = puuidFocusedPlayer;
    }

    public List<ParticipantGenerationCommentaryDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantGenerationCommentaryDTO> participants) {
        this.participants = participants;
    }

    public static class Builder {
        private String pseudoFocusedPlayer;
        private String puuidFocusedPlayer;
        private List<ParticipantGenerationCommentaryDTO> participantRequestCommentaries;

        public ContextGenerationCommentaryDTO.Builder pseudoFocusedPlayer(String pseudoFocusedPlayer) {
            this.pseudoFocusedPlayer = pseudoFocusedPlayer;
            return this;
        }

        public ContextGenerationCommentaryDTO.Builder puuidFocusedPlayer(String puuidFocusedPlayer) {
            this.puuidFocusedPlayer = puuidFocusedPlayer;
            return this;
        }

        public ContextGenerationCommentaryDTO.Builder participantRequestCommentaries(List<ParticipantGenerationCommentaryDTO> participantRequestCommentaries) {
            this.participantRequestCommentaries = participantRequestCommentaries;
            return this;
        }

        public ContextGenerationCommentaryDTO build() {
            ContextGenerationCommentaryDTO contextGenerationCommentaryDTO = new ContextGenerationCommentaryDTO();
            contextGenerationCommentaryDTO.setPseudoFocusedPlayer(this.pseudoFocusedPlayer);
            contextGenerationCommentaryDTO.setPuuidFocusedPlayer(this.puuidFocusedPlayer);
            contextGenerationCommentaryDTO.setParticipants(this.participantRequestCommentaries);
            return contextGenerationCommentaryDTO;
        }
    }
}
