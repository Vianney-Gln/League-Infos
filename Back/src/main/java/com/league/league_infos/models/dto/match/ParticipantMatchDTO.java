package com.league.league_infos.models.dto.match;

public class ParticipantMatchDTO {
    private Integer participantId;
    private Integer profileIcon;
    private Integer assists;
    private Integer kills;
    private Integer deaths;
    private Integer champLevel;
    private Integer championId;
    private Integer goldEarned;
    private Integer item0;
    private Integer item1;
    private Integer item2;
    private Integer item3;
    private Integer item4;
    private Integer item5;
    private Integer item6;
    private Integer championTransform; // used only for champion Kayn
    private Integer pentaKills;
    private Integer quadraKills;
    private Integer tripleKills;
    private Integer doubleKills;
    private Integer summoner1Id;
    private Integer summoner2Id;
    private Integer teamId;
    private Integer totalMinionsKilled;
    private Integer neutralMinionsKilled;
    private String championName;
    private String summonerName;
    private String lane;
    private String role;
    private String puuid;
    private Boolean win;
    private ChallengesDTO challenges;

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
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

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public Boolean getWin() {
        return win;
    }

    public void setWin(Boolean win) {
        this.win = win;
    }

    public ChallengesDTO getChallenges() {
        return challenges;
    }

    public void setChallenges(ChallengesDTO challenges) {
        this.challenges = challenges;
    }

    public static class Builder {
        private Integer participantId;
        private Integer profileIcon;
        private Integer assists;
        private Integer kills;
        private Integer deaths;
        private Integer champLevel;
        private Integer championId;
        private Integer goldEarned;
        private Integer item0;
        private Integer item1;
        private Integer item2;
        private Integer item3;
        private Integer item4;
        private Integer item5;
        private Integer item6;
        private Integer championTransform; // used only for champion Kayn
        private Integer pentaKills;
        private Integer quadraKills;
        private Integer tripleKills;
        private Integer doubleKills;
        private Integer summoner1Id;
        private Integer summoner2Id;
        private Integer teamId;
        private Integer totalMinionsKilled;
        private Integer neutralMinionsKilled;
        private String championName;
        private String summonerName;
        private String lane;
        private String role;
        private String puuid;
        private Boolean win;
        private ChallengesDTO challenges;

        public ParticipantMatchDTO.Builder participantId(Integer participantId) {
            this.participantId = participantId;
            return this;
        }

        public ParticipantMatchDTO.Builder profileIcon(Integer profileIcon) {
            this.profileIcon = profileIcon;
            return this;
        }

        public ParticipantMatchDTO.Builder assists(Integer assists) {
            this.assists = assists;
            return this;
        }

        public ParticipantMatchDTO.Builder kills(Integer kills) {
            this.kills = kills;
            return this;
        }

        public ParticipantMatchDTO.Builder deaths(Integer deaths) {
            this.deaths = deaths;
            return this;
        }

        public ParticipantMatchDTO.Builder champLevel(Integer champLevel) {
            this.champLevel = champLevel;
            return this;
        }

        public ParticipantMatchDTO.Builder championId(Integer championId) {
            this.championId = championId;
            return this;
        }

        public ParticipantMatchDTO.Builder goldEarned(Integer goldEarned) {
            this.goldEarned = goldEarned;
            return this;
        }

        public ParticipantMatchDTO.Builder item0(Integer item0) {
            this.item0 = item0;
            return this;
        }

        public ParticipantMatchDTO.Builder item1(Integer item1) {
            this.item1 = item1;
            return this;
        }

        public ParticipantMatchDTO.Builder item2(Integer item2) {
            this.item2 = item2;
            return this;
        }

        public ParticipantMatchDTO.Builder item3(Integer item3) {
            this.item3 = item3;
            return this;
        }

        public ParticipantMatchDTO.Builder item4(Integer item4) {
            this.item4 = item4;
            return this;
        }

        public ParticipantMatchDTO.Builder item5(Integer item5) {
            this.item5 = item5;
            return this;
        }

        public ParticipantMatchDTO.Builder item6(Integer item6) {
            this.item6 = item6;
            return this;
        }

        public ParticipantMatchDTO.Builder championTransform(Integer championTransform) {
            this.championTransform = championTransform;
            return this;
        }

        public ParticipantMatchDTO.Builder pentaKills(Integer pentaKills) {
            this.pentaKills = pentaKills;
            return this;
        }

        public ParticipantMatchDTO.Builder quadraKills(Integer quadraKills) {
            this.quadraKills = quadraKills;
            return this;
        }

        public ParticipantMatchDTO.Builder tripleKills(Integer tripleKills) {
            this.tripleKills = tripleKills;
            return this;
        }

        public ParticipantMatchDTO.Builder doubleKills(Integer doubleKills) {
            this.doubleKills = doubleKills;
            return this;
        }

        public ParticipantMatchDTO.Builder summoner1Id(Integer summoner1Id) {
            this.summoner1Id = summoner1Id;
            return this;
        }

        public ParticipantMatchDTO.Builder summoner2Id(Integer summoner2Id) {
            this.summoner2Id = summoner2Id;
            return this;
        }

        public ParticipantMatchDTO.Builder teamId(Integer teamId) {
            this.teamId = teamId;
            return this;
        }

        public ParticipantMatchDTO.Builder totalMinionsKilled(Integer totalMinionsKilled) {
            this.totalMinionsKilled = totalMinionsKilled;
            return this;
        }

        public ParticipantMatchDTO.Builder neutralMinionsKilled(Integer neutralMinionsKilled) {
            this.neutralMinionsKilled = neutralMinionsKilled;
            return this;
        }

        public ParticipantMatchDTO.Builder championName(String championName) {
            this.championName = championName;
            return this;
        }

        public ParticipantMatchDTO.Builder summonerName(String summonerName) {
            this.summonerName = summonerName;
            return this;
        }

        public ParticipantMatchDTO.Builder lane(String lane) {
            this.lane = lane;
            return this;
        }

        public ParticipantMatchDTO.Builder role(String role) {
            this.role = role;
            return this;
        }

        public ParticipantMatchDTO.Builder puuid(String puuid) {
            this.puuid = puuid;
            return this;
        }

        public ParticipantMatchDTO.Builder win(Boolean win) {
            this.win = win;
            return this;
        }

        public ParticipantMatchDTO.Builder challenges(ChallengesDTO challenges) {
            this.challenges = challenges;
            return this;
        }

        public ParticipantMatchDTO build() {
            ParticipantMatchDTO dto = new ParticipantMatchDTO();
            dto.setParticipantId(this.participantId);
            dto.setProfileIcon(this.profileIcon);
            dto.setAssists(this.assists);
            dto.setKills(this.kills);
            dto.setDeaths(this.deaths);
            dto.setChampLevel(this.champLevel);
            dto.setChampionId(this.championId);
            dto.setGoldEarned(this.goldEarned);
            dto.setItem0(this.item0);
            dto.setItem1(this.item1);
            dto.setItem2(this.item2);
            dto.setItem3(this.item3);
            dto.setItem4(this.item4);
            dto.setItem5(this.item5);
            dto.setItem6(this.item6);
            dto.setChampionTransform(this.championTransform);
            dto.setPentaKills(this.pentaKills);
            dto.setQuadraKills(this.quadraKills);
            dto.setTripleKills(this.tripleKills);
            dto.setDoubleKills(this.doubleKills);
            dto.setSummoner1Id(this.summoner1Id);
            dto.setSummoner2Id(this.summoner2Id);
            dto.setTeamId(this.teamId);
            dto.setTotalMinionsKilled(this.totalMinionsKilled);
            dto.setNeutralMinionsKilled(this.neutralMinionsKilled);
            dto.setChampionName(this.championName);
            dto.setSummonerName(this.summonerName);
            dto.setLane(this.lane);
            dto.setRole(this.role);
            dto.setPuuid(this.puuid);
            dto.setWin(this.win);
            dto.setChallenges(this.challenges);
            return dto;
        }
    }
}
