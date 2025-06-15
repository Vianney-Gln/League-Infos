package com.league.league_infos.controllers;

import com.league.league_infos.models.dto.AccountDTO;
import com.league.league_infos.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("account/{puuid}")
    public ResponseEntity<AccountDTO> getAccountByPuuid(@PathVariable String puuid) {
        return accountService.getAccountByPuuid(puuid);
    }

    @GetMapping("account/by-riot-id/{gameName}/{tagLine}")
    public ResponseEntity<AccountDTO> getAccountByRiotId(@PathVariable String gameName, @PathVariable String tagLine) {
        return accountService.getAccountByRiotId(gameName, tagLine);
    }
}
