import { HttpClient } from '@angular/common/http';
import { Injectable, signal, WritableSignal } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { LeagueListDTO } from '../../common/models/leagueListDTO';
import { AccountDTO } from '../../common/models/accountDTO';
import { SummonerDTO } from '../../common/models/summonerDTO';
import { LeagueEntryDTO } from '../../common/models/LeagueEntryDTO';
import { ChampionMasteryDto } from '../../common/models/ChampionMasteryDto';

@Injectable({
  providedIn: 'root',
})
export class PlayersService {
  constructor(private http: HttpClient) {}

  leagueChallengerSoloQListDTOSignal: WritableSignal<LeagueListDTO | null> = signal<LeagueListDTO | null>(null);
  leagueChallengerFlexListDTOSignal: WritableSignal<LeagueListDTO | null> = signal<LeagueListDTO | null>(null);

  getLeagueChallengerDataSoloQ(): Observable<LeagueListDTO> {
    const url = environment.apiBaseUrl + '/league-challengers-solo-queue';
    return this.http.get<LeagueListDTO>(url);
  }

  getLeagueChallengerDataFlexQ(): Observable<LeagueListDTO> {
    const url = environment.apiBaseUrl + '/league-challengers-flex-queue';
    return this.http.get<LeagueListDTO>(url);
  }

  getAccountByPuuid(puuid: string): Observable<AccountDTO> {
    const url = `${environment.apiBaseUrl}/account/${puuid}`;
    return this.http.get<AccountDTO>(url);
  }

  getSummonerByPuuid(puuid: string): Observable<SummonerDTO> {
    const url = `${environment.apiBaseUrl}/summoner/${puuid}`;
    return this.http.get<SummonerDTO>(url);
  }

  getAccountByRiotId(gameName: string, tagLine: string): Observable<AccountDTO> {
    const url = `${environment.apiBaseUrl}/account/by-riot-id/${gameName}/${tagLine}`;
    return this.http.get<AccountDTO>(url);
  }

  getLeagueEntryByPuuid(puuid: string): Observable<LeagueEntryDTO[]> {
    const url = `${environment.apiBaseUrl}/league-entries-by-puuid/${puuid}`;
    return this.http.get<LeagueEntryDTO[]>(url);
  }

  getChampionMasteriesDTO(puuid: string): Observable<ChampionMasteryDto[]> {
    const url = `${environment.apiBaseUrl}/champion-masteries/${puuid}/top`;
    return this.http.get<ChampionMasteryDto[]>(url);
  }
}
