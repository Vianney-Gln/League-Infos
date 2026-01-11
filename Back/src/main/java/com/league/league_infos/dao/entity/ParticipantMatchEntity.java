package com.league.league_infos.dao.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_participant_match")
public class ParticipantMatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "participant_id")
    private Long idParticipant;

    @Column(name = "profile_icon")
    private Integer profileIcon;

    @Column(name = "assists", nullable = false)
    private Integer assists;

    @Column(name = "kills", nullable = false)
    private Integer kills;

    @Column(name = "deaths", nullable = false)
    private Integer deaths;

    @Column(name = "champ_level", nullable = false)
    private Integer champLevel;

    @Column(name = "champion_id", nullable = false)
    private Integer championId;

    @Column(name = "gold_earned", nullable = false)
    private Integer goldEarned;

    @Column(name = "item_0")
    private Integer item0;

    @Column(name = "item_1")
    private Integer item1;

    @Column(name = "item_2")
    private Integer item2;

    @Column(name = "item_3")
    private Integer item3;

    @Column(name = "item_4")
    private Integer item4;

    @Column(name = "item_5")
    private Integer item5;

    @Column(name = "item_6")
    private Integer item6;

    @Column(name = "champion_transform", nullable = false)
    private Integer championTransform;

    @Column(name = "penta_kills", nullable = false)
    private Integer pentaKills;

    @Column(name = "quadra_kills", nullable = false)
    private Integer quadraKills;

    @Column(name = "triple_kills", nullable = false)
    private Integer tripleKills;

    @Column(name = "double_kills", nullable = false)
    private Integer doubleKills;

    @Column(name = "summoner1_id", nullable = false)
    private Integer summoner1Id;

    @Column(name = "summoner2_id", nullable = false)
    private Integer summoner2Id;

    @Column(name = "team_id", nullable = false)
    private Integer teamId;

    @Column(name = "total_minions_killed", nullable = false)
    private Integer totalMinionsKilled;

    @Column(name = "neutral_minions_killed", nullable = false)
    private Integer neutralMinionsKilled;

    @Column(name = "champion_name", nullable = false)
    private String championName;

    @Column(name = "summoner_name")
    private String summonerName;

    @Column(name = "lane", nullable = false)
    private String lane;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "team_position", nullable = false)
    private String teamPosition;

    @Column(name = "puuid", nullable = false)
    private String puuid;

    @Column(name = "pseudo")
    private String pseudo;

    @Column(name = "win", nullable = false)
    private boolean win;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "challenges_id", nullable = false)
    private ChallengesEntity challengesEntity;

    @ManyToOne
    @JoinColumn(name = "info_match_id", nullable = false)
    private InfoMatchEntity infoMatchEntity;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "meta_data_id", nullable = false)
    private MetaDataEntity metaDataEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MetaDataEntity getMetaDataEntity() {
        return metaDataEntity;
    }

    public void setMetaDataEntity(MetaDataEntity metaDataEntity) {
        this.metaDataEntity = metaDataEntity;
    }

    public Long getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(Long idParticipant) {
        this.idParticipant = idParticipant;
    }

    public Integer getProfileIcon() {
        return profileIcon;
    }

    public void setProfileIcon(Integer profileIcon) {
        this.profileIcon = profileIcon;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Integer getKills() {
        return kills;
    }

    public void setKills(Integer kills) {
        this.kills = kills;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getChampLevel() {
        return champLevel;
    }

    public void setChampLevel(Integer champLevel) {
        this.champLevel = champLevel;
    }

    public Integer getChampionId() {
        return championId;
    }

    public void setChampionId(Integer championId) {
        this.championId = championId;
    }

    public Integer getGoldEarned() {
        return goldEarned;
    }

    public void setGoldEarned(Integer goldEarned) {
        this.goldEarned = goldEarned;
    }

    public Integer getItem0() {
        return item0;
    }

    public void setItem0(Integer item0) {
        this.item0 = item0;
    }

    public Integer getItem1() {
        return item1;
    }

    public void setItem1(Integer item1) {
        this.item1 = item1;
    }

    public Integer getItem2() {
        return item2;
    }

    public void setItem2(Integer item2) {
        this.item2 = item2;
    }

    public Integer getItem3() {
        return item3;
    }

    public void setItem3(Integer item3) {
        this.item3 = item3;
    }

    public Integer getItem4() {
        return item4;
    }

    public void setItem4(Integer item4) {
        this.item4 = item4;
    }

    public Integer getItem5() {
        return item5;
    }

    public void setItem5(Integer item5) {
        this.item5 = item5;
    }

    public Integer getItem6() {
        return item6;
    }

    public void setItem6(Integer item6) {
        this.item6 = item6;
    }

    public Integer getChampionTransform() {
        return championTransform;
    }

    public void setChampionTransform(Integer championTransform) {
        this.championTransform = championTransform;
    }

    public Integer getPentaKills() {
        return pentaKills;
    }

    public void setPentaKills(Integer pentaKills) {
        this.pentaKills = pentaKills;
    }

    public Integer getQuadraKills() {
        return quadraKills;
    }

    public void setQuadraKills(Integer quadraKills) {
        this.quadraKills = quadraKills;
    }

    public Integer getTripleKills() {
        return tripleKills;
    }

    public void setTripleKills(Integer tripleKills) {
        this.tripleKills = tripleKills;
    }

    public Integer getDoubleKills() {
        return doubleKills;
    }

    public void setDoubleKills(Integer doubleKills) {
        this.doubleKills = doubleKills;
    }

    public Integer getSummoner1Id() {
        return summoner1Id;
    }

    public void setSummoner1Id(Integer summoner1Id) {
        this.summoner1Id = summoner1Id;
    }

    public Integer getSummoner2Id() {
        return summoner2Id;
    }

    public void setSummoner2Id(Integer summoner2Id) {
        this.summoner2Id = summoner2Id;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getTotalMinionsKilled() {
        return totalMinionsKilled;
    }

    public void setTotalMinionsKilled(Integer totalMinionsKilled) {
        this.totalMinionsKilled = totalMinionsKilled;
    }

    public Integer getNeutralMinionsKilled() {
        return neutralMinionsKilled;
    }

    public void setNeutralMinionsKilled(Integer neutralMinionsKilled) {
        this.neutralMinionsKilled = neutralMinionsKilled;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTeamPosition() {
        return teamPosition;
    }

    public void setTeamPosition(String teamPosition) {
        this.teamPosition = teamPosition;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public ChallengesEntity getChallengesEntity() {
        return challengesEntity;
    }

    public void setChallengesEntity(ChallengesEntity challengesEntity) {
        this.challengesEntity = challengesEntity;
    }

    public InfoMatchEntity getInfoMatchEntity() {
        return infoMatchEntity;
    }

    public void setInfoMatchEntity(InfoMatchEntity infoMatchEntity) {
        this.infoMatchEntity = infoMatchEntity;
    }
}
