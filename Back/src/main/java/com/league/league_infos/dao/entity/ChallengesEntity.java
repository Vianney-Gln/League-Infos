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
@Table(name = "t_challenges")
public class ChallengesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenges_id")
    private Long challengesId;

    @Column(name = "game_length", nullable = false)
    private Float gameLength;

    @Column(name = "kda")
    private Float kda;

    @OneToMany(mappedBy = "challengesEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParticipantMatchEntity> participantMatchEntityList = new ArrayList<>();

    public Long getChallengesId() {
        return challengesId;
    }

    public void setChallengesId(Long challengesId) {
        this.challengesId = challengesId;
    }

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

    public List<ParticipantMatchEntity> getParticipantMatchEntityList() {
        return participantMatchEntityList;
    }

    public void setParticipantMatchEntityList(List<ParticipantMatchEntity> participantMatchEntityList) {
        this.participantMatchEntityList = participantMatchEntityList;
    }
}
