package com.league.league_infos.dto.match;

import java.time.LocalDateTime;
import java.util.List;

public class InfoMatchDTO {
    private String endOfGameResult;
    private String gameMode;
    private String gameName;
    private String gameType;
    private String gameVersion;
    private Long gameCreation;
    private Long gameDuration;
    private Long gameEndTimestamp;
    private Long gameId;
    private Integer mapId;
    private Integer queueId;
    private List<ParticipantMatchDTO> participants;
    private List<TeamDTO> teams;
    private LocalDateTime creationDate;

    public String getEndOfGameResult() {
        return endOfGameResult;
    }

    public void setEndOfGameResult(String endOfGameResult) {
        this.endOfGameResult = endOfGameResult;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    public Long getGameCreation() {
        return gameCreation;
    }

    public void setGameCreation(Long gameCreation) {
        this.gameCreation = gameCreation;
    }

    public Long getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(Long gameDuration) {
        this.gameDuration = gameDuration;
    }

    public Long getGameEndTimestamp() {
        return gameEndTimestamp;
    }

    public void setGameEndTimestamp(Long gameEndTimestamp) {
        this.gameEndTimestamp = gameEndTimestamp;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Integer getMapId() {
        return mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    public Integer getQueueId() {
        return queueId;
    }

    public void setQueueId(Integer queueId) {
        this.queueId = queueId;
    }

    public List<ParticipantMatchDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantMatchDTO> participants) {
        this.participants = participants;
    }

    public List<TeamDTO> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamDTO> teams) {
        this.teams = teams;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public static class Builder {
        private String endOfGameResult;
        private String gameMode;
        private String gameName;
        private String gameType;
        private String gameVersion;
        private Long gameCreation;
        private Long gameDuration;
        private Long gameEndTimestamp;
        private Long gameId;
        private Integer mapId;
        private Integer queueId;
        private List<ParticipantMatchDTO> participants;
        private List<TeamDTO> teams;
        private LocalDateTime creationDate;

        public InfoMatchDTO.Builder endOfGameResult(String endOfGameResult) {
            this.endOfGameResult = endOfGameResult;
            return this;
        }

        public InfoMatchDTO.Builder gameMode(String gameMode) {
            this.gameMode = gameMode;
            return this;
        }

        public InfoMatchDTO.Builder gameName(String gameName) {
            this.gameName = gameName;
            return this;
        }

        public InfoMatchDTO.Builder gameType(String gameType) {
            this.gameType = gameType;
            return this;
        }

        public InfoMatchDTO.Builder gameVersion(String gameVersion) {
            this.gameVersion = gameVersion;
            return this;
        }

        public InfoMatchDTO.Builder gameCreation(Long gameCreation) {
            this.gameCreation = gameCreation;
            return this;
        }

        public InfoMatchDTO.Builder gameDuration(Long gameDuration) {
            this.gameDuration = gameDuration;
            return this;
        }

        public InfoMatchDTO.Builder gameEndTimestamp(Long gameEndTimestamp) {
            this.gameEndTimestamp = gameEndTimestamp;
            return this;
        }

        public InfoMatchDTO.Builder gameId(Long gameId) {
            this.gameId = gameId;
            return this;
        }

        public InfoMatchDTO.Builder mapId(Integer mapId) {
            this.mapId = mapId;
            return this;
        }

        public InfoMatchDTO.Builder queueId(Integer queueId) {
            this.queueId = queueId;
            return this;
        }

        public InfoMatchDTO.Builder participants(List<ParticipantMatchDTO> participants) {
            this.participants = participants;
            return this;
        }

        public InfoMatchDTO.Builder teams(List<TeamDTO> teams) {
            this.teams = teams;
            return this;
        }

        public InfoMatchDTO.Builder creationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public InfoMatchDTO build() {
            InfoMatchDTO dto = new InfoMatchDTO();
            dto.setEndOfGameResult(this.endOfGameResult);
            dto.setGameMode(this.gameMode);
            dto.setGameName(this.gameName);
            dto.setGameType(this.gameType);
            dto.setGameVersion(this.gameVersion);
            dto.setGameCreation(this.gameCreation);
            dto.setGameDuration(this.gameDuration);
            dto.setGameEndTimestamp(this.gameEndTimestamp);
            dto.setGameId(this.gameId);
            dto.setMapId(this.mapId);
            dto.setQueueId(this.queueId);
            dto.setParticipants(this.participants);
            dto.setTeams(this.teams);
            dto.setCreationDate(this.creationDate);
            return dto;
        }
    }
}
