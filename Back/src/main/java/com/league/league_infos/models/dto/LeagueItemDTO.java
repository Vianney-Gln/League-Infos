package com.league.league_infos.models.dto;

public class LeagueItemDTO {
    private String summonerId;
    private String puuid;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;
    private boolean veteran;
    private boolean inactive;
    private boolean freshBlood;
    private boolean hotStreak;
    private MiniSeriesDTO miniSeries;

    public String getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(String summonerId) {
        this.summonerId = summonerId;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public int getLeaguePoints() {
        return leaguePoints;
    }

    public void setLeaguePoints(int leaguePoints) {
        this.leaguePoints = leaguePoints;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public boolean isVeteran() {
        return veteran;
    }

    public void setVeteran(boolean veteran) {
        this.veteran = veteran;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public boolean isFreshBlood() {
        return freshBlood;
    }

    public void setFreshBlood(boolean freshBlood) {
        this.freshBlood = freshBlood;
    }

    public boolean isHotStreak() {
        return hotStreak;
    }

    public void setHotStreak(boolean hotStreak) {
        this.hotStreak = hotStreak;
    }

    public MiniSeriesDTO getMiniSeries() {
        return miniSeries;
    }

    public void setMiniSeries(MiniSeriesDTO miniSeries) {
        this.miniSeries = miniSeries;
    }

    public static class Builder {
        private String summonerId;
        private String puuid;
        private String rank;
        private int leaguePoints;
        private int wins;
        private int losses;
        private boolean veteran;
        private boolean inactive;
        private boolean freshBlood;
        private boolean hotStreak;
        private MiniSeriesDTO miniSeriesDTO;

        public LeagueItemDTO.Builder summonerId(String summonerId) {
            this.summonerId = summonerId;
            return this;
        }

        public LeagueItemDTO.Builder puuid(String puuid) {
            this.puuid = puuid;
            return this;
        }

        public LeagueItemDTO.Builder leaguePoints(int leaguePoints) {
            this.leaguePoints = leaguePoints;
            return this;
        }

        public LeagueItemDTO.Builder rank(String rank) {
            this.rank = rank;
            return this;
        }

        public LeagueItemDTO.Builder wins(int wins) {
            this.wins = wins;
            return this;
        }

        public LeagueItemDTO.Builder losses(int losses) {
            this.losses = losses;
            return this;
        }

        public LeagueItemDTO.Builder veteran(boolean veteran) {
            this.veteran = veteran;
            return this;
        }

        public LeagueItemDTO.Builder inactive(boolean inactive) {
            this.inactive = inactive;
            return this;
        }

        public LeagueItemDTO.Builder freshBlood(boolean freshBlood) {
            this.freshBlood = freshBlood;
            return this;
        }

        public LeagueItemDTO.Builder hotStreak(boolean hotStreak) {
            this.hotStreak = hotStreak;
            return this;
        }

        public LeagueItemDTO.Builder miniSeriesDTO(MiniSeriesDTO miniSeriesDTO) {
            this.miniSeriesDTO = miniSeriesDTO;
            return this;
        }

        public LeagueItemDTO build() {
            LeagueItemDTO dto = new LeagueItemDTO();
            dto.setSummonerId(this.summonerId);
            dto.setPuuid(this.puuid);
            dto.setLeaguePoints(this.leaguePoints);
            dto.setRank(this.rank);
            dto.setWins(this.wins);
            dto.setLosses(this.losses);
            dto.setVeteran(this.veteran);
            dto.setInactive(this.inactive);
            dto.setFreshBlood(this.freshBlood);
            dto.setHotStreak(this.hotStreak);
            dto.setMiniSeries(this.miniSeriesDTO);
            return dto;
        }
    }
}
