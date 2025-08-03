package com.league.league_infos.dto.match;

public class TeamDTO {
    private Integer teamId;
    private Boolean win;

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Boolean getWin() {
        return win;
    }

    public void setWin(Boolean win) {
        this.win = win;
    }

    public static class Builder {
        private Integer teamId;
        private Boolean win;

        public TeamDTO.Builder teamId(Integer teamId) {
            this.teamId = teamId;
            return this;
        }

        public TeamDTO.Builder win(Boolean win) {
            this.win = win;
            return this;
        }

        public TeamDTO build() {
            TeamDTO teamDTO = new TeamDTO();
            teamDTO.setTeamId(this.teamId);
            teamDTO.setWin(this.win);
            return teamDTO;
        }
    }
}
