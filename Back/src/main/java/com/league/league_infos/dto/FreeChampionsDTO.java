package com.league.league_infos.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class FreeChampionsDTO {
    @JsonAlias({"newplayer", "freeChampionIdsForNewPlayers"})
    private List<Integer> freeChampionIdsForNewPlayers;

    @JsonAlias({"sr", "freeChampionIds"})
    private List<Integer> freeChampionIds;

    public List<Integer> getFreeChampionIdsForNewPlayers() {
        return freeChampionIdsForNewPlayers;
    }

    public void setFreeChampionIdsForNewPlayers(List<Integer> freeChampionIdsForNewPlayers) {
        this.freeChampionIdsForNewPlayers = freeChampionIdsForNewPlayers;
    }

    public List<Integer> getFreeChampionIds() {
        return freeChampionIds;
    }

    public void setFreeChampionIds(List<Integer> freeChampionIds) {
        this.freeChampionIds = freeChampionIds;
    }

    public static class Builder {

        private List<Integer> freeChampionIdsForNewPlayers;
        private List<Integer> freeChampionIds;

        public Builder freeChampionIdsForNewPlayers(List<Integer> ids) {
            this.freeChampionIdsForNewPlayers = ids;
            return this;
        }

        public Builder freeChampionIds(List<Integer> ids) {
            this.freeChampionIds = ids;
            return this;
        }

        public FreeChampionsDTO build() {
            FreeChampionsDTO dto = new FreeChampionsDTO();
            dto.setFreeChampionIdsForNewPlayers(this.freeChampionIdsForNewPlayers);
            dto.setFreeChampionIds(this.freeChampionIds);
            return dto;
        }
    }

}
