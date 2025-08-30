package com.league.league_infos.dao.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_meta_data")
public class MetaDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meta_data_id")
    private Long metaDataId;

    @Column(name = "data_version", nullable = false)
    private String dataVersion;

    @Column(name = "match_id", nullable = false)
    private String matchId;

    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @OneToMany(mappedBy = "metaDataEntity", cascade = CascadeType.ALL)
    private List<ParticipantMatchEntity> participants = new ArrayList<>();

    public List<ParticipantMatchEntity> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantMatchEntity> participants) {
        this.participants = participants;
    }

    public Long getMetaDataId() {
        return metaDataId;
    }

    public void setMetaDataId(Long metaDataId) {
        this.metaDataId = metaDataId;
    }

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

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}
