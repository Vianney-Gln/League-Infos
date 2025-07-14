import { Injectable, WritableSignal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { FreeChampionsDTO } from '../../common/models/freeChampionsDTO';
import { ChampionData } from '../../common/models/championsInfos';
import { signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class GetChampionsService {
  constructor(private http: HttpClient) {}

  championDataSignal: WritableSignal<ChampionData | null> = signal<ChampionData | null>(null);
  isFreeChampErrorSignal: WritableSignal<boolean> = signal<boolean>(false);

  getFreeChampions(): Observable<FreeChampionsDTO> {
    const url = `${environment.apiBaseUrl}/champions/free`;
    return this.http.get<FreeChampionsDTO>(url);
  }

  getAllChampionsInfos(version: string): Observable<ChampionData> {
    return this.http.get<ChampionData>(`https://ddragon.leagueoflegends.com/cdn/${version}/data/fr_FR/champion.json`);
  }

  getMostBannedChampionId(): Observable<number> {
    const url = `${environment.apiBaseUrl}/stats/most-banned-champion`;
    return this.http.get<number>(url);
  }
}
