package com.league.league_infos.models.dto;

public class AccountDTO {

    private String puuid;
    private String gameName;
    private String tagLine;

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public static class Builder {
        private String puuid;
        private String gameName;
        private String tagLine;

        public Builder puuid(String puuid) {
            this.puuid = puuid;
            return this;
        }

        public Builder gameName(String gameName) {
            this.gameName = gameName;
            return this;
        }

        public Builder tagLine(String tagLine) {
            this.tagLine = tagLine;
            return this;
        }

        public AccountDTO build() {
            AccountDTO dto = new AccountDTO();
            dto.setPuuid(this.puuid);
            dto.setGameName(this.gameName);
            dto.setTagLine(this.tagLine);
            return dto;
        }
    }
}
