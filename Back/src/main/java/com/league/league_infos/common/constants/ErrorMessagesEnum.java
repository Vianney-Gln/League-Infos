package com.league.league_infos.common.constants;

public enum ErrorMessagesEnum {
    ERROR_BUSINESS_1("ERROR_BUSINESS_1", "Aucun invocateur ne correspond à votre recherche"),
    ERROR_BUSINESS_2("ERROR_BUSINESS_2", "Erreur lors de l'appel à l'API Riot"),
    ERROR_BUSINESS_3("ERROR_BUSINESS_3", "API Riot inaccessible pour le moment");


    private final String libelle;
    private final String code;

    ErrorMessagesEnum(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getCode() {
        return code;
    }
}
