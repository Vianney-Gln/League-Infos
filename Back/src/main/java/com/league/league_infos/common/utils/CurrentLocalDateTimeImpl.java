package com.league.league_infos.common.utils;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CurrentLocalDateTimeImpl implements CurrentLocalDateTime {

    @Override
    public LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }
}
