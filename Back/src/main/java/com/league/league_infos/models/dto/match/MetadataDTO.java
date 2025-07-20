package com.league.league_infos.models.dto.match;

import java.util.List;

public class MetadataDTO {
    private String dataVersion;
    private String matchId;
    private List<String> participants;

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public static class Builder {
        private String dataVersion;
        private String matchId;
        private List<String> participants;

        public MetadataDTO.Builder dataVersion(String dataVersion) {
            this.dataVersion = dataVersion;
            return this;
        }

        public MetadataDTO.Builder matchId(String matchId) {
            this.matchId = matchId;
            return this;
        }

        public MetadataDTO.Builder participants(List<String> participants) {
            this.participants = participants;
            return this;
        }

        public MetadataDTO build() {
            MetadataDTO dto = new MetadataDTO();
            dto.setDataVersion(this.dataVersion);
            dto.setMatchId(this.matchId);
            dto.setParticipants(this.participants);
            return dto;
        }
    }
}
