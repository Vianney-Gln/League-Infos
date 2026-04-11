import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { FreeChampionsDTO } from '../../common/models/freeChampionsDTO';
import { HttpClient } from '@angular/common/http';
import { CommentaryDTO } from '../../common/models/CommentaryDTO';

@Injectable({
  providedIn: 'root',
})
export class CommentaryService {
  constructor(private httpClient: HttpClient) {}

  getCommentary(matchId: string, gameId: number, puuid: string, pseudo: string): Observable<CommentaryDTO> {
    const url = `${environment.apiBaseUrl}/matchCommentary/${matchId}?gameId=${gameId}&puuid=${puuid}&pseudo=${pseudo}`;
    return this.httpClient.get<CommentaryDTO>(url);
  }
}
