package com.league.league_infos.dto;

import java.util.List;

public class LeagueListDTO {
    private String leagueId;
    private String tier;
    private String queue;
    private String name;
    private List<LeagueItemDTO> entries;

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LeagueItemDTO> getEntries() {
        return entries;
    }

    public void setEntries(List<LeagueItemDTO> entries) {
        this.entries = entries;
    }

    public static class Builder {
        private String leagueId;
        private String tier;
        private String queue;
        private String name;
        private List<LeagueItemDTO> entries;

        public LeagueListDTO.Builder leagueId(String leagueId) {
            this.leagueId = leagueId;
            return this;
        }

        public LeagueListDTO.Builder tier(String tier) {
            this.tier = tier;
            return this;
        }

        public LeagueListDTO.Builder queue(String queue) {
            this.queue = queue;
            return this;
        }

        public LeagueListDTO.Builder name(String name) {
            this.name = name;
            return this;
        }

        public LeagueListDTO.Builder entries(List<LeagueItemDTO> entries) {
            this.entries = entries;
            return this;
        }

        public LeagueListDTO build() {
            LeagueListDTO dto = new LeagueListDTO();
            dto.setLeagueId(this.leagueId);
            dto.setTier(this.tier);
            dto.setQueue(this.queue);
            dto.setName(this.name);
            dto.setEntries(this.entries);
            return dto;
        }
    }
}
