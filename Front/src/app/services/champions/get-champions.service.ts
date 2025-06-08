import { Injectable, WritableSignal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CHAMPION_ROTATIONS_API_URL } from '../../common/constants/api-urls';
import { environment } from '../../../environments/environment';
import { FreeChampionsDTO } from '../../common/models/freeChampionsDTO';
import { ChampionData } from '../../common/models/championsInfos';
import { signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class GetChampionsService {
  constructor(private http: HttpClient) {}

  header = { 'X-Riot-Token': environment.apiKey };
  championDataSignal: WritableSignal<ChampionData | null> = signal<ChampionData | null>(null);

  getFreeChampions(): Observable<FreeChampionsDTO> {
    return this.http.get<FreeChampionsDTO>(CHAMPION_ROTATIONS_API_URL, { headers: this.header });
  }

  getAllChampionsInfos(version: string): Observable<ChampionData> {
    return this.http.get<ChampionData>(`https://ddragon.leagueoflegends.com/cdn/${version}/data/fr_FR/champion.json`);
  }
}
