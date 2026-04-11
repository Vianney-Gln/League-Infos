package com.league.league_infos.dto.ia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventMatchDTO {
    private String type;
    private long timestamp;
    private String time;
    private Integer killerId;
    private Integer victimId;
    private List<Integer> assistingParticipantIds = new ArrayList<>();
    private String team;
    private String monsterType;
    private String monsterSubType;
    private String towerType;
    private String dragonType;
    private String buildingType;
    private String laneType;
    private PositionEventDTO position;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getKillerId() {
        return killerId;
    }

    public void setKillerId(Integer killerId) {
        this.killerId = killerId;
    }

    public Integer getVictimId() {
        return victimId;
    }

    public void setVictimId(Integer victimId) {
        this.victimId = victimId;
    }

    public List<Integer> getAssistingParticipantIds() {
        return assistingParticipantIds;
    }

    public void setAssistingParticipantIds(List<Integer> assistingParticipantIds) {
        this.assistingParticipantIds = assistingParticipantIds;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public void setMonsterType(String monsterType) {
        this.monsterType = monsterType;
    }

    public String getTowerType() {
        return towerType;
    }

    public void setTowerType(String towerType) {
        this.towerType = towerType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMonsterSubType() {
        return monsterSubType;
    }

    public void setMonsterSubType(String monsterSubType) {
        this.monsterSubType = monsterSubType;
    }

    public String getDragonType() {
        return dragonType;
    }

    public void setDragonType(String dragonType) {
        this.dragonType = dragonType;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public String getLaneType() {
        return laneType;
    }

    public void setLaneType(String laneType) {
        this.laneType = laneType;
    }

    public PositionEventDTO getPosition() {
        return position;
    }

    public void setPosition(PositionEventDTO position) {
        this.position = position;
    }

    public static class Builder {
        private String type;
        private long timestamp;
        private String time;
        private Integer killerId;
        private Integer victimId;
        private List<Integer> assistingParticipantIds = new ArrayList<>();
        private String team;
        private String monsterType;
        private String monsterSubType;
        private String towerType;
        private String dragonType;
        private String buildingType;
        private String laneType;
        private PositionEventDTO position;


        public EventMatchDTO.Builder type(String type) {
            this.type = type;
            return this;
        }

        public EventMatchDTO.Builder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public EventMatchDTO.Builder time(String time) {
            this.time = time;
            return this;
        }

        public EventMatchDTO.Builder killerId(Integer killerId) {
            this.killerId = killerId;
            return this;
        }

        public EventMatchDTO.Builder victimId(Integer victimId) {
            this.victimId = victimId;
            return this;
        }

        public EventMatchDTO.Builder assistingParticipantIds(List<Integer> assistingParticipantIds) {
            this.assistingParticipantIds = assistingParticipantIds;
            return this;
        }

        public EventMatchDTO.Builder team(String team) {
            this.team = team;
            return this;
        }

        public EventMatchDTO.Builder monsterType(String monsterType) {
            this.monsterType = monsterType;
            return this;
        }

        public EventMatchDTO.Builder monsterSubType(String monsterSubType) {
            this.monsterSubType = monsterSubType;
            return this;
        }

        public EventMatchDTO.Builder towerType(String towerType) {
            this.towerType = towerType;
            return this;
        }

        public EventMatchDTO.Builder dragonType(String dragonType) {
            this.dragonType = dragonType;
            return this;
        }

        public EventMatchDTO.Builder buildingType(String buildingType) {
            this.buildingType = buildingType;
            return this;
        }

        public EventMatchDTO.Builder laneType(String laneType) {
            this.laneType = laneType;
            return this;
        }

        public EventMatchDTO.Builder position(PositionEventDTO position) {
            this.position = position;
            return this;
        }


        public EventMatchDTO build() {
            EventMatchDTO eventMatchDTO = new EventMatchDTO();
            eventMatchDTO.setType(this.type);
            eventMatchDTO.setTimestamp(this.timestamp);
            eventMatchDTO.setTime(this.time);
            eventMatchDTO.setKillerId(this.killerId);
            eventMatchDTO.setVictimId(this.victimId);
            eventMatchDTO.setAssistingParticipantIds(this.assistingParticipantIds);
            eventMatchDTO.setTeam(this.team);
            eventMatchDTO.setMonsterType(this.monsterType);
            eventMatchDTO.setMonsterSubType(this.monsterSubType);
            eventMatchDTO.setTowerType(this.towerType);
            eventMatchDTO.setDragonType(this.dragonType);
            eventMatchDTO.setBuildingType(this.buildingType);
            eventMatchDTO.setLaneType(this.laneType);
            eventMatchDTO.setPosition(this.position);
            return eventMatchDTO;
        }
    }
}
