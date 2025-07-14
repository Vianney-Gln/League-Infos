package com.league.league_infos.models.dto;

import java.util.List;

public class FeaturedGameInfoDTO {
    private String gameMode;
    private String gameType;
    private String platformId;
    private long gameLength;
    private long mapId;
    private long gameId;
    private long gameQueueConfigId;
    private List<BannedChampionDTO> bannedChampions;
    private List<ParticipantDTO> participants;

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public long getGameLength() {
        return gameLength;
    }

    public void setGameLength(long gameLength) {
        this.gameLength = gameLength;
    }

    public long getMapId() {
        return mapId;
    }

    public void setMapId(long mapId) {
        this.mapId = mapId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getGameQueueConfigId() {
        return gameQueueConfigId;
    }

    public void setGameQueueConfigId(long gameQueueConfigId) {
        this.gameQueueConfigId = gameQueueConfigId;
    }

    public List<BannedChampionDTO> getBannedChampions() {
        return bannedChampions;
    }

    public void setBannedChampions(List<BannedChampionDTO> bannedChampions) {
        this.bannedChampions = bannedChampions;
    }

    public List<ParticipantDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantDTO> participants) {
        this.participants = participants;
    }

    public static class Builder {
        private String gameMode;
        private String gameType;
        private String platformId;
        private long gameLength;
        private long mapId;
        private long gameId;
        private long gameQueueConfigId;
        private List<BannedChampionDTO> bannedChampions;
        private List<ParticipantDTO> participants;

        public FeaturedGameInfoDTO.Builder gameMode(String gameMode) {
            this.gameMode = gameMode;
            return this;
        }

        public FeaturedGameInfoDTO.Builder gameType(String gameType) {
            this.gameType = gameType;
            return this;
        }

        public FeaturedGameInfoDTO.Builder platformId(String platformId) {
            this.platformId = platformId;
            return this;
        }

        public FeaturedGameInfoDTO.Builder gameLength(long gameLength) {
            this.gameLength = gameLength;
            return this;
        }

        public FeaturedGameInfoDTO.Builder mapId(long mapId) {
            this.mapId = mapId;
            return this;
        }

        public FeaturedGameInfoDTO.Builder gameId(long gameId) {
            this.gameId = gameId;
            return this;
        }

        public FeaturedGameInfoDTO.Builder gameQueueConfigId(long gameQueueConfigId) {
            this.gameQueueConfigId = gameQueueConfigId;
            return this;
        }

        public FeaturedGameInfoDTO.Builder bannedChampions(List<BannedChampionDTO> bannedChampions) {
            this.bannedChampions = bannedChampions;
            return this;
        }

        public FeaturedGameInfoDTO.Builder participants(List<ParticipantDTO> participants) {
            this.participants = participants;
            return this;
        }

        public FeaturedGameInfoDTO build() {
            FeaturedGameInfoDTO featuredGameInfoDTO = new FeaturedGameInfoDTO();
            featuredGameInfoDTO.setGameMode(this.gameMode);
            featuredGameInfoDTO.setGameType(this.gameType);
            featuredGameInfoDTO.setPlatformId(this.platformId);
            featuredGameInfoDTO.setGameLength(this.gameLength);
            featuredGameInfoDTO.setMapId(this.mapId);
            featuredGameInfoDTO.setGameId(this.gameId);
            featuredGameInfoDTO.setGameQueueConfigId(this.gameQueueConfigId);
            featuredGameInfoDTO.setBannedChampions(this.bannedChampions);
            featuredGameInfoDTO.setParticipants(this.participants);
            return featuredGameInfoDTO;
        }
    }
}
