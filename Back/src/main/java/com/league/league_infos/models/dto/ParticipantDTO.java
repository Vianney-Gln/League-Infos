package com.league.league_infos.models.dto;

public class ParticipantDTO {
    private boolean bot;
    private long spell1Id;
    private long spell2Id;
    private long championId;
    private long teamId;
    private long profileIconId;
    private String puuid;

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    public long getSpell1Id() {
        return spell1Id;
    }

    public void setSpell1Id(long spell1Id) {
        this.spell1Id = spell1Id;
    }

    public long getSpell2Id() {
        return spell2Id;
    }

    public void setSpell2Id(long spell2Id) {
        this.spell2Id = spell2Id;
    }

    public long getChampionId() {
        return championId;
    }

    public void setChampionId(long championId) {
        this.championId = championId;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public long getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(long profileIconId) {
        this.profileIconId = profileIconId;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public static class Builder {
        private boolean bot;
        private long spell1Id;
        private long spell2Id;
        private long championId;
        private long teamId;
        private long profileIconId;
        private String puuid;

        public ParticipantDTO.Builder bot(boolean bot) {
            this.bot = bot;
            return this;
        }

        public ParticipantDTO.Builder spell1Id(long spell1Id) {
            this.spell1Id = spell1Id;
            return this;
        }

        public ParticipantDTO.Builder spell2Id(long spell2Id) {
            this.spell2Id = spell2Id;
            return this;
        }

        public ParticipantDTO.Builder championId(long championId) {
            this.championId = championId;
            return this;
        }

        public ParticipantDTO.Builder teamId(long teamId) {
            this.teamId = teamId;
            return this;
        }

        public ParticipantDTO.Builder profileIconId(long profileIconId) {
            this.profileIconId = profileIconId;
            return this;
        }

        public ParticipantDTO.Builder puuid(String puuid) {
            this.puuid = puuid;
            return this;
        }

        public ParticipantDTO build() {
            ParticipantDTO dto = new ParticipantDTO();
            dto.setBot(this.bot);
            dto.setSpell1Id(this.spell1Id);
            dto.setSpell2Id(this.spell2Id);
            dto.setChampionId(this.championId);
            dto.setTeamId(this.teamId);
            dto.setProfileIconId(this.profileIconId);
            dto.setPuuid(this.puuid);
            return dto;
        }
    }
}
