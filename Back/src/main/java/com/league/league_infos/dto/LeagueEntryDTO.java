package com.league.league_infos.dto;

public class LeagueEntryDTO extends LeagueItemDTO {
    private String leagueId;
    private String tier;
    private String queueType;

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public static class Builder {
        private String leagueId;
        private String summonerId;
        private String puuid;
        private String rank;
        private String tier;
        private String queueType;
        private int leaguePoints;
        private int wins;
        private int losses;
        private boolean veteran;
        private boolean inactive;
        private boolean freshBlood;
        private boolean hotStreak;
        private MiniSeriesDTO miniSeriesDTO;

        public LeagueEntryDTO.Builder leagueId(String leagueId) {
            this.leagueId = leagueId;
            return this;
        }

        public LeagueEntryDTO.Builder summonerId(String summonerId) {
            this.summonerId = summonerId;
            return this;
        }

        public LeagueEntryDTO.Builder puuid(String puuid) {
            this.puuid = puuid;
            return this;
        }

        public LeagueEntryDTO.Builder leaguePoints(int leaguePoints) {
            this.leaguePoints = leaguePoints;
            return this;
        }

        public LeagueEntryDTO.Builder rank(String rank) {
            this.rank = rank;
            return this;
        }

        public LeagueEntryDTO.Builder tier(String tier) {
            this.tier = tier;
            return this;
        }

        public LeagueEntryDTO.Builder queueType(String queueType) {
            this.queueType = queueType;
            return this;
        }

        public LeagueEntryDTO.Builder wins(int wins) {
            this.wins = wins;
            return this;
        }

        public LeagueEntryDTO.Builder losses(int losses) {
            this.losses = losses;
            return this;
        }

        public LeagueEntryDTO.Builder veteran(boolean veteran) {
            this.veteran = veteran;
            return this;
        }

        public LeagueEntryDTO.Builder inactive(boolean inactive) {
            this.inactive = inactive;
            return this;
        }

        public LeagueEntryDTO.Builder freshBlood(boolean freshBlood) {
            this.freshBlood = freshBlood;
            return this;
        }

        public LeagueEntryDTO.Builder hotStreak(boolean hotStreak) {
            this.hotStreak = hotStreak;
            return this;
        }

        public LeagueEntryDTO.Builder miniSeriesDTO(MiniSeriesDTO miniSeriesDTO) {
            this.miniSeriesDTO = miniSeriesDTO;
            return this;
        }

        public LeagueEntryDTO build() {
            LeagueEntryDTO dto = new LeagueEntryDTO();
            dto.setLeagueId(this.leagueId);
            dto.setSummonerId(this.summonerId);
            dto.setPuuid(this.puuid);
            dto.setLeaguePoints(this.leaguePoints);
            dto.setQueueType(this.queueType);
            dto.setRank(this.rank);
            dto.setTier(this.tier);
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
