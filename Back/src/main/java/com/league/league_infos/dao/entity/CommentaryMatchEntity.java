package com.league.league_infos.dao.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_commentary_match")
public class CommentaryMatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "commentary_match_id", nullable = false)
    private String commentaryMatchId;

    @Column(name = "object")
    private String object;

    @Column(name = "created", nullable = false)
    private Long createdDate;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "service_tier", nullable = false)
    private String serviceTier;

    @Column(name = "system_fingerprint", nullable = false)
    private String fingerPrint;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "participant_match_id")
    private ParticipantMatchEntity participantMatchEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentaryMatchId() {
        return commentaryMatchId;
    }

    public void setCommentaryMatchId(String commentaryMatchId) {
        this.commentaryMatchId = commentaryMatchId;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getServiceTier() {
        return serviceTier;
    }

    public void setServiceTier(String serviceTier) {
        this.serviceTier = serviceTier;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public ParticipantMatchEntity getParticipantMatchEntity() {
        return participantMatchEntity;
    }

    public void setParticipantMatchEntity(ParticipantMatchEntity participantMatchEntity) {
        this.participantMatchEntity = participantMatchEntity;
    }
}
