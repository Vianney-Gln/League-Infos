package com.league.league_infos.common.utils;

import java.text.Normalizer;

public class StringUtils {

    public static String cleanGameOrTagName(String gameOrTagName) {
        if (gameOrTagName == null) return null;
        String cleaned = gameOrTagName.replaceAll("[\\p{Cf}\\p{Cc}]", "");
        return Normalizer.normalize(cleaned, Normalizer.Form.NFC);
    }
}
