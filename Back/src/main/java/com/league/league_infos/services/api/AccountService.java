package com.league.league_infos.services.api;

import com.league.league_infos.dto.AccountDTO;

public interface AccountService {
    AccountDTO getAccountByPuuid(String puuid);

    AccountDTO getAccountByRiotId(String gameName, String tagLine);
}
