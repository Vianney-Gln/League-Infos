package com.league.league_infos.dto.ddragon;

import java.util.Map;

public class ChampionDataDTO {
    private String type;
    private String format;
    private String version;
    private Map<String, ChampionDTO> data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, ChampionDTO> getData() {
        return data;
    }

    public void setData(Map<String, ChampionDTO> data) {
        this.data = data;
    }
}
