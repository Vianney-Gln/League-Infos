package com.league.league_infos.dto.ia;

import com.league.league_infos.dto.match.ParticipantMatchDTO;

public class ParticipantGenerationCommentaryDTO extends ParticipantMatchDTO {

    public static class Builder {
        private String puuid;
        private Integer kills;
        private Integer deaths;
        private Integer assists;
        private Integer goldEarned;
        private Integer participantId;
        private String role;
        private String championName;

        public ParticipantGenerationCommentaryDTO.Builder puuid(String puuid) {
            this.puuid = puuid;
            return this;
        }

        public ParticipantGenerationCommentaryDTO.Builder kills(Integer kills) {
            this.kills = kills;
            return this;
        }

        public ParticipantGenerationCommentaryDTO.Builder deaths(Integer deaths) {
            this.deaths = deaths;
            return this;
        }

        public ParticipantGenerationCommentaryDTO.Builder assists(Integer assists) {
            this.assists = assists;
            return this;
        }

        public ParticipantGenerationCommentaryDTO.Builder goldEarned(Integer goldEarned) {
            this.goldEarned = goldEarned;
            return this;
        }

        public ParticipantGenerationCommentaryDTO.Builder participantId(Integer participantId) {
            this.participantId = participantId;
            return this;
        }

        public ParticipantGenerationCommentaryDTO.Builder role(String role) {
            this.role = role;
            return this;
        }

        public ParticipantGenerationCommentaryDTO.Builder championName(String championName) {
            this.championName = championName;
            return this;
        }

        public ParticipantGenerationCommentaryDTO build() {
            ParticipantGenerationCommentaryDTO participantGenerationCommentaryDTO = new ParticipantGenerationCommentaryDTO();
            participantGenerationCommentaryDTO.setPuuid(this.puuid);
            participantGenerationCommentaryDTO.setKills(this.kills);
            participantGenerationCommentaryDTO.setDeaths(this.deaths);
            participantGenerationCommentaryDTO.setAssists(this.assists);
            participantGenerationCommentaryDTO.setGoldEarned(this.goldEarned);
            participantGenerationCommentaryDTO.setParticipantId(this.participantId);
            participantGenerationCommentaryDTO.setRole(this.role);
            participantGenerationCommentaryDTO.setChampionName(this.championName);
            return participantGenerationCommentaryDTO;
        }
    }
}
