package com.league.league_infos.dto;

public class BannedChampionDTO {
    private int pickTurn;
    private long championId;
    private long teamId;

    public int getPickTurn() {
        return pickTurn;
    }

    public void setPickTurn(int pickTurn) {
        this.pickTurn = pickTurn;
    }

    public long getChampionId() {
        return championId;
    }

    public void setChampionId(long championId) {
        this.championId = championId;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public static class Builder {
        private int pickTurn;
        private long championId;
        private long teamId;

        public BannedChampionDTO.Builder pickTurn(int pickTurn) {
            this.pickTurn = pickTurn;
            return this;
        }

        public BannedChampionDTO.Builder championId(long championId) {
            this.championId = championId;
            return this;
        }

        public BannedChampionDTO.Builder teamId(long teamId) {
            this.teamId = teamId;
            return this;
        }

        public BannedChampionDTO build() {
            BannedChampionDTO bannedChampionDTO = new BannedChampionDTO();
            bannedChampionDTO.setPickTurn(this.pickTurn);
            bannedChampionDTO.setChampionId(this.championId);
            bannedChampionDTO.setTeamId(this.teamId);
            return bannedChampionDTO;
        }
    }
}
