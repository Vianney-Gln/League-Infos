package com.league.league_infos.dao.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "t_infos_match")
public class InfoMatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "info_match_id", nullable = false)
    private Long idInfoMatch;

    @Column(name = "end_of_game_result", nullable = false)
    private String endOfGameResult;

    @Column(name = "game_mode", nullable = false)
    private String gameMode;

    @Column(name = "game_name")
    private String gameName;

    @Column(name = "game_type", nullable = false)
    private String gameType;

    @Column(name = "game_version", nullable = false)
    private String gameVersion;

    @Column(name = "game_creation", nullable = false)
    private Long gameCreation;

    @Column(name = "game_duration", nullable = false)
    private Long gameDuration;

    @Column(name = "game_end_timestamp", nullable = false)
    private Long gameEndTimestamp;

    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @Column(name = "map_id", nullable = false)
    private Integer mapId;

    @Column(name = "queue_id", nullable = false)
    private Integer queueId;

    @OneToMany(mappedBy = "infoMatchEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamEntity> teamEntity;

    @OneToMany(mappedBy = "infoMatchEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParticipantMatchEntity> participantMatchEntityList;

    public Long getIdInfoMatch() {
        return idInfoMatch;
    }

    public void setIdInfoMatch(Long idInfoMatch) {
        this.idInfoMatch = idInfoMatch;
    }

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

    public List<TeamEntity> getTeamEntity() {
        return teamEntity;
    }

    public void setTeamEntity(List<TeamEntity> teamEntity) {
        this.teamEntity = teamEntity;
    }

    public List<ParticipantMatchEntity> getParticipantMatchEntityList() {
        return participantMatchEntityList;
    }

    public void setParticipantMatchEntityList(List<ParticipantMatchEntity> participantMatchEntityList) {
        this.participantMatchEntityList = participantMatchEntityList;
    }
}
