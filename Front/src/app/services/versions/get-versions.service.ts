import { HttpClient } from '@angular/common/http';
import { Injectable, signal, WritableSignal } from '@angular/core';
import { Observable } from 'rxjs';
import { GET_VERSIONS_LOL_DRAGON_URL } from '../../common/constants/api-urls';

@Injectable({
  providedIn: 'root',
})
export class GetVersionsService {
  constructor(private http: HttpClient) {}

  lastVersionlolDTOSignal: WritableSignal<string> = signal<string>('');

  getAllVersionsLol(): Observable<string[]> {
    return this.http.get<string[]>(GET_VERSIONS_LOL_DRAGON_URL);
  }
}
