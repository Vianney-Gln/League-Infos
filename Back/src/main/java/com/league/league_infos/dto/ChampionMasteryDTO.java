package com.league.league_infos.dto;

public class ChampionMasteryDTO {

    private String puuid;
    private long championPointsUntilNextLevel;
    private long championId;
    private long lastPlayTime;
    private long championPointsSinceLastLevel;
    private int championLevel;
    private int championPoints;
    private int markRequiredForNextLevel;
    private int championSeasonMilestone;
    private boolean chestGranted;


    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public long getChampionPointsUntilNextLevel() {
        return championPointsUntilNextLevel;
    }

    public void setChampionPointsUntilNextLevel(long championPointsUntilNextLevel) {
        this.championPointsUntilNextLevel = championPointsUntilNextLevel;
    }

    public long getChampionId() {
        return championId;
    }

    public void setChampionId(long championId) {
        this.championId = championId;
    }

    public long getLastPlayTime() {
        return lastPlayTime;
    }

    public void setLastPlayTime(long lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }

    public long getChampionPointsSinceLastLevel() {
        return championPointsSinceLastLevel;
    }

    public void setChampionPointsSinceLastLevel(long championPointsSinceLastLevel) {
        this.championPointsSinceLastLevel = championPointsSinceLastLevel;
    }

    public int getChampionLevel() {
        return championLevel;
    }

    public void setChampionLevel(int championLevel) {
        this.championLevel = championLevel;
    }

    public int getChampionPoints() {
        return championPoints;
    }

    public void setChampionPoints(int championPoints) {
        this.championPoints = championPoints;
    }

    public int getMarkRequiredForNextLevel() {
        return markRequiredForNextLevel;
    }

    public void setMarkRequiredForNextLevel(int markRequiredForNextLevel) {
        this.markRequiredForNextLevel = markRequiredForNextLevel;
    }

    public int getChampionSeasonMilestone() {
        return championSeasonMilestone;
    }

    public void setChampionSeasonMilestone(int championSeasonMilestone) {
        this.championSeasonMilestone = championSeasonMilestone;
    }

    public boolean isChestGranted() {
        return chestGranted;
    }

    public void setChestGranted(boolean chestGranted) {
        this.chestGranted = chestGranted;
    }

    public static class Builder {
        private String puuid;
        private long championPointsUntilNextLevel;
        private long championId;
        private long lastPlayTime;
        private long championPointsSinceLastLevel;
        private int championLevel;
        private int championPoints;
        private int markRequiredForNextLevel;
        private int championSeasonMilestone;
        private boolean chestGranted;

        public ChampionMasteryDTO.Builder puuid(String puuid) {
            this.puuid = puuid;
            return this;
        }

        public ChampionMasteryDTO.Builder championPointsUntilNextLevel(long championPointsUntilNextLevel) {
            this.championPointsUntilNextLevel = championPointsUntilNextLevel;
            return this;
        }

        public ChampionMasteryDTO.Builder championId(long championId) {
            this.championId = championId;
            return this;
        }

        public ChampionMasteryDTO.Builder lastPlayTime(long lastPlayTime) {
            this.lastPlayTime = lastPlayTime;
            return this;
        }

        public ChampionMasteryDTO.Builder championPointsSinceLastLevel(long championPointsSinceLastLevel) {
            this.championPointsSinceLastLevel = championPointsSinceLastLevel;
            return this;
        }

        public ChampionMasteryDTO.Builder championLevel(int championLevel) {
            this.championLevel = championLevel;
            return this;
        }

        public ChampionMasteryDTO.Builder championPoints(int championPoints) {
            this.championPoints = championPoints;
            return this;
        }

        public ChampionMasteryDTO.Builder markRequiredForNextLevel(int markRequiredForNextLevel) {
            this.markRequiredForNextLevel = markRequiredForNextLevel;
            return this;
        }

        public ChampionMasteryDTO.Builder championSeasonMilestone(int championSeasonMilestone) {
            this.championSeasonMilestone = championSeasonMilestone;
            return this;
        }

        public ChampionMasteryDTO.Builder chestGranted(boolean chestGranted) {
            this.chestGranted = chestGranted;
            return this;
        }

        public ChampionMasteryDTO build() {
            ChampionMasteryDTO championMasteryDTO = new ChampionMasteryDTO();
            championMasteryDTO.setPuuid(this.puuid);
            championMasteryDTO.setChampionPointsUntilNextLevel(this.championPointsUntilNextLevel);
            championMasteryDTO.setChampionId(this.championId);
            championMasteryDTO.setLastPlayTime(this.lastPlayTime);
            championMasteryDTO.setChampionPointsSinceLastLevel(this.championPointsSinceLastLevel);
            championMasteryDTO.setChampionLevel(this.championLevel);
            championMasteryDTO.setChampionPoints(this.championPoints);
            championMasteryDTO.setMarkRequiredForNextLevel(this.markRequiredForNextLevel);
            championMasteryDTO.setChampionSeasonMilestone(this.championSeasonMilestone);
            championMasteryDTO.setChestGranted(this.chestGranted);
            return championMasteryDTO;
        }
    }
}
