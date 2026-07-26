package com.league.league_infos.dto.ddragon;

import java.util.List;

public class ChampionDTO {
    private String version;
    private String id;
    private String key;
    private String name;
    private String title;
    private String blurb;
    private ChampionInfoDTO info;
    private ChampionImageDTO image;
    private List<String> tags;
    private String partype;
    private ChampionStatsDTO stats;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public ChampionInfoDTO getInfo() {
        return info;
    }

    public void setInfo(ChampionInfoDTO info) {
        this.info = info;
    }

    public ChampionImageDTO getImage() {
        return image;
    }

    public void setImage(ChampionImageDTO image) {
        this.image = image;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getPartype() {
        return partype;
    }

    public void setPartype(String partype) {
        this.partype = partype;
    }

    public ChampionStatsDTO getStats() {
        return stats;
    }

    public void setStats(ChampionStatsDTO stats) {
        this.stats = stats;
    }
}
