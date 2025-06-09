package com.league.league_infos.models.enums;

public enum QueueEnum {
    RANKED_SOLO_5x5("RANKED_SOLO_5x5"),
    RANKED_FLEX_SR("RANKED_FLEX_SR");

    private final String libelle;

    QueueEnum(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
