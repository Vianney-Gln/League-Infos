package com.league.league_infos.dto.ia;

public class PositionEventDTO {

    private Integer x;
    private Integer y;

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public static class Builder {
        private Integer x;
        private Integer y;

        public PositionEventDTO.Builder x(Integer x) {
            this.x = x;
            return this;
        }

        public PositionEventDTO.Builder y(Integer y) {
            this.y = y;
            return this;
        }

        public PositionEventDTO build() {
            PositionEventDTO positionEventDTO = new PositionEventDTO();
            positionEventDTO.setX(this.x);
            positionEventDTO.setY(this.y);
            return positionEventDTO;
        }
    }
}
