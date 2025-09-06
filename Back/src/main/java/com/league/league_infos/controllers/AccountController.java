package com.league.league_infos.controllers;

import com.league.league_infos.common.utils.StringUtils;
import com.league.league_infos.dto.AccountDTO;
import com.league.league_infos.services.riot.RiotAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private final RiotAccountService accountService;

    public AccountController(RiotAccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("account/{puuid}")
    public ResponseEntity<AccountDTO> getAccountByPuuid(@PathVariable String puuid) {

        return ResponseEntity.ok(accountService.getAccountByPuuid(puuid));
    }

    @GetMapping("account/by-riot-id/{gameName}/{tagLine}")
    public ResponseEntity<AccountDTO> getAccountByRiotId(@PathVariable String gameName, @PathVariable String tagLine) {
        return ResponseEntity.ok(accountService.getAccountByRiotId(StringUtils.cleanGameOrTagName(gameName), StringUtils.cleanGameOrTagName(tagLine)));
    }
}
