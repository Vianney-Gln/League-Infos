package com.league.league_infos.dto;

public class SummonerDTO {

    private String accountId;
    private String id;
    private String puuid;
    private long summonerLevel;
    private long revisionDate;
    private int profileIconId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public long getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(long summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public long getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(long revisionDate) {
        this.revisionDate = revisionDate;
    }

    public int getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(int profileIconId) {
        this.profileIconId = profileIconId;
    }

    public static class Builder {
        private String accountId;
        private String id;
        private String puuid;
        private long summonerLevel;
        private long revisionDate;
        private int profileIconId;

        public SummonerDTO.Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public SummonerDTO.Builder id(String id) {
            this.id = id;
            return this;
        }

        public SummonerDTO.Builder puuid(String puuid) {
            this.puuid = puuid;
            return this;
        }

        public SummonerDTO.Builder summonerLevel(long summonerLevel) {
            this.summonerLevel = summonerLevel;
            return this;
        }

        public SummonerDTO.Builder revisionDate(long revisionDate) {
            this.revisionDate = revisionDate;
            return this;
        }

        public SummonerDTO.Builder profileIconId(int profileIconId) {
            this.profileIconId = profileIconId;
            return this;
        }

        public SummonerDTO build() {
            SummonerDTO dto = new SummonerDTO();
            dto.setAccountId(this.accountId);
            dto.setId(this.id);
            dto.setPuuid(this.puuid);
            dto.setSummonerLevel(this.summonerLevel);
            dto.setRevisionDate(this.revisionDate);
            dto.setProfileIconId(this.profileIconId);
            return dto;
        }
    }
}
