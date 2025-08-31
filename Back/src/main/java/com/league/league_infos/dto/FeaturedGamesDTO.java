package com.league.league_infos.dto;

import java.util.List;

public class FeaturedGamesDTO {
    private List<FeaturedGameInfoDTO> gameList;
    private long clientRefreshInterval;

    public List<FeaturedGameInfoDTO> getGameList() {
        return gameList;
    }

    public void setGameList(List<FeaturedGameInfoDTO> gameList) {
        this.gameList = gameList;
    }

    public long getClientRefreshInterval() {
        return clientRefreshInterval;
    }

    public void setClientRefreshInterval(long clientRefreshInterval) {
        this.clientRefreshInterval = clientRefreshInterval;
    }

    public static class Builder {
        private List<FeaturedGameInfoDTO> gameList;
        private long clientRefreshInterval;

        public FeaturedGamesDTO.Builder gameList(List<FeaturedGameInfoDTO> gameList) {
            this.gameList = gameList;
            return this;
        }

        public FeaturedGamesDTO.Builder clientRefreshInterval(long clientRefreshInterval) {
            this.clientRefreshInterval = clientRefreshInterval;
            return this;
        }

        public FeaturedGamesDTO build() {
            FeaturedGamesDTO featuredGamesDTO = new FeaturedGamesDTO();
            featuredGamesDTO.setGameList(this.gameList);
            featuredGamesDTO.setClientRefreshInterval(this.clientRefreshInterval);
            return featuredGamesDTO;
        }
    }
}
