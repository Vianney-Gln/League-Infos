import { HttpClient } from '@angular/common/http';
import { Injectable, signal, WritableSignal } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { LeagueListDTO } from '../../common/models/leagueListDTO';
import { ACCOUNT_BY_PUUID_API_URL, CHALLENGERS_LEAGUE_API_URL, SUMMONER_BY_PUUID_API_URL } from '../../common/constants/api-urls';
import { RANKED_FLEX_SR, RANKED_SOLO_5x5 } from '../../common/constants/queues';
import { AccountDTO } from '../../common/models/accountDTO';
import { SummonerDTO } from '../../common/models/summonerDTO';

@Injectable({
  providedIn: 'root',
})
export class PlayersService {
  constructor(private http: HttpClient) {}
  header = { 'X-Riot-Token': environment.apiKey };
  leagueChallengerListDTOSignal: WritableSignal<LeagueListDTO | null> = signal<LeagueListDTO | null>(null);

  getLeagueChallengerDataSoloQ(): Observable<LeagueListDTO> {
    return this.http.get<LeagueListDTO>(`${CHALLENGERS_LEAGUE_API_URL}/${RANKED_SOLO_5x5}`, { headers: this.header });
  }

  getLeagueChallengerDataFlexQ(): Observable<LeagueListDTO> {
    return this.http.get<LeagueListDTO>(`${CHALLENGERS_LEAGUE_API_URL}/${RANKED_FLEX_SR}`, { headers: this.header });
  }

  getAccountByPuuid(puuid: string): Observable<AccountDTO> {
    return this.http.get<AccountDTO>(`${ACCOUNT_BY_PUUID_API_URL}/${puuid}`, { headers: this.header });
  }

  getSummonerByPuuid(puuid: string): Observable<SummonerDTO> {
    return this.http.get<SummonerDTO>(`${SUMMONER_BY_PUUID_API_URL}/${puuid}`, { headers: this.header });
  }
}
