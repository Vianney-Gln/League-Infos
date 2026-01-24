package com.league.league_infos.common.enums;

public enum MatchEventEnum {
    CHAMPION_KILL("CHAMPION_KILL"),
    BUILDING_KILL("BUILDING_KILL"),
    ELITE_MONSTER_KILL("ELITE_MONSTER_KILL");

    private final String libelle;

    MatchEventEnum(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
