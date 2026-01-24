package com.league.league_infos.dto.ia;

public class ParticipantGenerationCommentaryDTO {
    private String puuid;
    private Integer kills;
    private Integer deaths;
    private Integer assists;
    private Integer goldEarned;
    private Integer participantId;
    private String role;
    private String champion;

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public Integer getKills() {
        return kills;
    }

    public void setKills(Integer kills) {
        this.kills = kills;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Integer getGoldEarned() {
        return goldEarned;
    }

    public void setGoldEarned(Integer goldEarned) {
        this.goldEarned = goldEarned;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    public String getChampion() {
        return champion;
    }

    public void setChampion(String champion) {
        this.champion = champion;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static class Builder {
        private String puuid;
        private Integer kills;
        private Integer deaths;
        private Integer assists;
        private Integer goldEarned;
        private Integer participantId;
        private String role;
        private String champion;

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

        public ParticipantGenerationCommentaryDTO.Builder champion(String champion) {
            this.champion = champion;
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
            participantGenerationCommentaryDTO.setChampion(this.champion);
            return participantGenerationCommentaryDTO;
        }
    }
}
