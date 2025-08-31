package com.league.league_infos.dto;

import java.util.List;

public class FreeChampionsDTO {
    private int maxNewPlayerLevel;
    private List<Integer> freeChampionIdsForNewPlayers;
    private List<Integer> freeChampionIds;

    public int getMaxNewPlayerLevel() {
        return maxNewPlayerLevel;
    }

    public void setMaxNewPlayerLevel(int maxNewPlayerLevel) {
        this.maxNewPlayerLevel = maxNewPlayerLevel;
    }

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
        private int maxNewPlayerLevel;
        private List<Integer> freeChampionIdsForNewPlayers;
        private List<Integer> freeChampionIds;

        public Builder maxNewPlayerLevel(int maxNewPlayerLevel) {
            this.maxNewPlayerLevel = maxNewPlayerLevel;
            return this;
        }

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
            dto.setMaxNewPlayerLevel(this.maxNewPlayerLevel);
            dto.setFreeChampionIdsForNewPlayers(this.freeChampionIdsForNewPlayers);
            dto.setFreeChampionIds(this.freeChampionIds);
            return dto;
        }
    }

}
