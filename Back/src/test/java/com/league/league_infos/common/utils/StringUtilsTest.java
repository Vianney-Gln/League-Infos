package com.league.league_infos.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilsTest {

    @Test
    @DisplayName("Should return null if input is null")
    void cleanGameOrTagName_success_1() {
        assertThat(StringUtils.cleanGameOrTagName(null)).isNull();
    }

    @Test
    @DisplayName("should clean invisible Characters")
    void cleanGameOrTagName_success_2() {
        // GIVEN
        String expectOutput = "0o Ninja o0";
        String input = "\u20660o Ninja o0\u2069";

        // WHEN
        String result = StringUtils.cleanGameOrTagName(input);

        // THEN
        assertThat(result).isEqualTo(expectOutput);
    }

    @Test
    @DisplayName("should parse ms into a readable string like mm:ss min")
    void milliSecondsToTimeStr_success() {

        // WHEN
        String result = StringUtils.milliSecondsToTimeStr(572925);

        // THEN
        assertThat(result).isEqualTo("9:32 min");
    }


}
