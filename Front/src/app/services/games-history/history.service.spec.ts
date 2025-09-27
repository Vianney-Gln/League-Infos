import { TestBed } from '@angular/core/testing';

import { HistoryService } from './history.service';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { MatchDTO } from '../../common/models/games-history/matchDTO';
import { environment } from '../../../environments/environment';

describe('HistoryService', () => {
  let service: HistoryService;
  let httpMock: HttpTestingController;
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    service = TestBed.inject(HistoryService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  function matchDTOMock() {
    return {
      metadata: {
        matchId: '1234567890',
        participants: ['puuid-1'],
        dataVersion: '1',
      },
      info: {
        gameDuration: 1800,
        gameCreation: 1620000000000,
        queueId: 420,
        participants: [
          {
            puuid: 'puuid-1',
            championId: 11,
            kills: 10,
            deaths: 2,
            assists: 8,
            win: true,
            summonerName: 'PlayerOne',
            teamId: 100,
            role: 'TOP',
            lane: 'TOP',
            totalMinionsKilled: 200,
            goldEarned: 15000,
            champLevel: 18,
            items: [1055, 3111, 3078, 3053, 3026, 3156],
            spells: [4, 12],
            perks: {
              primary: [8005, 9111, 9104, 8014],
              secondary: [8304, 8345],
              statPerks: [5008, 5008, 5002],
            },
          },
        ],
        teams: [
          {
            teamId: 100,
            win: true,
            bans: [11, 22, 33, 44, 55],
          },
          {
            teamId: 200,
            win: false,
            bans: [66, 77, 88, 99, 101],
          },
        ],
      },
    } as unknown as MatchDTO;
  }

  it('should call getHistoryByPuuidAndQueueType with correct URL', () => {
    // GIVEN
    const mockResponse = [matchDTOMock()];

    // WHEN
    service.getHistoryByPuuidAndQueueType('puuid', 420).subscribe((res) => {
      expect(res).toHaveSize(1);
      expect(res[0].metadata).toEqual(mockResponse[0].metadata);
      expect(res[0].info).toEqual(mockResponse[0].info);
    });

    // THEN
    const url = environment.apiBaseUrl + '/games-history/puuid?queue=420';
    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should call getMoreHistory with correct URL', () => {
    // GIVEN
    const mockResponse = [matchDTOMock()];
    const gamecreation: number = 1758490634927;
    const queueType: number = 420;

    // WHEN
    service.getMoreHistory('puuid', gamecreation, queueType).subscribe((res) => {
      expect(res).toHaveSize(1);
      expect(res[0].metadata).toEqual(mockResponse[0].metadata);
      expect(res[0].info).toEqual(mockResponse[0].info);
    });

    // THEN
    const url = environment.apiBaseUrl + '/games-history-before-creation-date/puuid?gameCreation=1758490634927&queue=420';
    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should have listMatchDataSignal initialized as empty', () => {
    expect(service.listMatchDataSignal()).toHaveSize(0);
  });
});
