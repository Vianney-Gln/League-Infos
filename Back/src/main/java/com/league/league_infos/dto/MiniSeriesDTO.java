package com.league.league_infos.dto;

public class MiniSeriesDTO {
    private int losses;
    private int target;
    private int wins;
    private String progress;

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public static class Builder {
        private int losses;
        private int target;
        private int wins;
        private String progress;

        public MiniSeriesDTO.Builder losses(int losses) {
            this.losses = losses;
            return this;
        }

        public MiniSeriesDTO.Builder target(int target) {
            this.target = target;
            return this;
        }

        public MiniSeriesDTO.Builder wins(int wins) {
            this.wins = wins;
            return this;
        }

        public MiniSeriesDTO.Builder progress(String progress) {
            this.progress = progress;
            return this;
        }

        public MiniSeriesDTO build() {
            MiniSeriesDTO dto = new MiniSeriesDTO();
            dto.setLosses(this.losses);
            dto.setWins(this.wins);
            dto.setTarget(this.target);
            dto.setProgress(this.progress);
            return dto;
        }
    }
}
