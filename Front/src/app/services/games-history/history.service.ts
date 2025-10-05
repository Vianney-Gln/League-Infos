import { HttpClient } from '@angular/common/http';
import { Injectable, signal, WritableSignal } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { MatchDTO } from '../../common/models/games-history/matchDTO';

@Injectable({
  providedIn: 'root',
})
export class HistoryService {
  constructor(private http: HttpClient) {}

  currentMatch: WritableSignal<MatchDTO | undefined> = signal(undefined);

  getHistoryByPuuidAndQueueType(puuid: string, queueId: number): Observable<MatchDTO[]> {
    const url = `${environment.apiBaseUrl}/games-history/${puuid}?queue=${queueId}`;
    return this.http.get<MatchDTO[]>(url);
  }

  getMoreHistory(puuid: string, gameCreation: number, queueId: number) {
    const url = `${environment.apiBaseUrl}/games-history-before-creation-date/${puuid}?gameCreation=${gameCreation}&queue=${queueId}`;
    return this.http.get<MatchDTO[]>(url);
  }
}
