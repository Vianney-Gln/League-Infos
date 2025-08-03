package com.league.league_infos.dto.match;

public class ChallengesDTO {
    private Float gameLength;
    private Float kda;

    public Float getGameLength() {
        return gameLength;
    }

    public void setGameLength(Float gameLength) {
        this.gameLength = gameLength;
    }

    public Float getKda() {
        return kda;
    }

    public void setKda(Float kda) {
        this.kda = kda;
    }

    public static class Builder {
        private Float gameLength;
        private Float kda;

        public ChallengesDTO.Builder gameLength(Float gameLength) {
            this.gameLength = gameLength;
            return this;
        }

        public ChallengesDTO.Builder kda(Float kda) {
            this.kda = kda;
            return this;
        }

        public ChallengesDTO build() {
            ChallengesDTO dto = new ChallengesDTO();
            dto.setKda(this.kda);
            dto.setGameLength(this.gameLength);
            return dto;
        }
    }
}
