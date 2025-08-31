package com.league.league_infos.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_teams")
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", nullable = false)
    private Long idTeam;

    @Column(name = "team_id_numero", nullable = false)
    private Integer teamIdNumero;

    @Column(name = "win", nullable = false)
    private boolean win;

    @ManyToOne
    @JoinColumn(name = "info_match_id")
    private InfoMatchEntity infoMatchEntity;

    public Long getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(Long idTeam) {
        this.idTeam = idTeam;
    }

    public Integer getTeamIdNumero() {
        return teamIdNumero;
    }

    public void setTeamIdNumero(Integer teamIdNumero) {
        this.teamIdNumero = teamIdNumero;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public InfoMatchEntity getInfoMatchEntity() {
        return infoMatchEntity;
    }

    public void setInfoMatchEntity(InfoMatchEntity infoMatchEntity) {
        this.infoMatchEntity = infoMatchEntity;
    }
}
