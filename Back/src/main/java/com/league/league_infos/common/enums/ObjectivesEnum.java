package com.league.league_infos.common.enums;

public enum ObjectivesEnum {
    DRAGON("Dragon", "DRAGON"),
    FIRE_DRAGON("Dragon infernal", "FIRE_DRAGON"),
    WATER_DRAGON("Dragon de l'océan", "WATER_DRAGON"),
    AIR_DRAGON("Dragon de l'air", "AIR_DRAGON"),
    EARTH_DRAGON("Dragon de la terre", "EARTH_DRAGON"),
    HEXTECH_DRAGON("Dragon Hextech", "HEXTECH_DRAGON"),
    CHEMTECH_DRAGON("Dragon Techno Chimique", "CHEMTECH_DRAGON"),
    ELDER_DRAGON("Dragon ancestral", "ELDER_DRAGON"),
    BARON_NASHOR("Baron Nashor", "BARON_NASHOR"),
    HORDE("Larves du néant", "HORDE"),
    RIFTHERALD("Hérault", "RIFTHERALD"),
    ATAKHAN("Atakhan", "ATAKHAN"),
    BUILDING_KILL("Batiment", "BUILDING_KILL"),
    TOWER_BUILDING("TOWER_BUILDING", "TOWER_BUILDING"),
    OUTER_TURRET("Tourelle extérieur", "OUTER_TURRET"),
    INNER_TURRET("Tourelle intérieur", "INNER_TURRET"),
    BASE_TURRET("Tourelle de base", "BASE_TURRET"),
    NEXUS_TURRET("Tourelle de nexus", "NEXUS_TURRET"),
    INHIBITOR("Inhibiteur", "INHIBITOR"),
    INHIBITOR_BUILDING("INHIBITOR_BUILDING", "INHIBITOR_BUILDING");

    private final String libelle;
    private final String code;

    ObjectivesEnum(String libelle, String code) {
        this.libelle = libelle;
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getCode() {
        return code;
    }

    public static ObjectivesEnum fromCode(String name) {
        for (ObjectivesEnum objectives : ObjectivesEnum.values()) {
            if (objectives.name().equalsIgnoreCase(name)) {
                return objectives;
            }
        }
        throw new IllegalArgumentException("Nom inconnu: " + name);
    }

}
