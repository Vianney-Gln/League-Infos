package com.league.league_infos.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}
