import { TestBed } from '@angular/core/testing';

import { PlayersService } from './players.service';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { LeagueListDTO } from '../../common/models/leagueListDTO';
import { environment } from '../../../environments/environment';
import { AccountDTO } from '../../common/models/accountDTO';
import { SummonerDTO } from '../../common/models/summonerDTO';

describe('PlayersService', () => {
  let service: PlayersService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(withInterceptorsFromDi()), provideHttpClientTesting()],
    });
    service = TestBed.inject(PlayersService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call getChallengersData and should get a list of data for soloQ 5v5', () => {
    // GIVEN
    const mockLeagueListDTO = {
      leagueId: '12345',
      tier: 'CHALLENGER',
      entries: [
        {
          summonerId: 'summoner123',
          puuid: '1234',
          leaguePoints: 1000,
          rank: 'I',
          wins: 100,
          losses: 50,
          veteran: false,
          inactive: false,
          freshBlood: true,
          hotStreak: false,
        },
      ],
      queue: 'RANKED_SOLO_5x5',
      name: 'Challenger League',
    } as LeagueListDTO;

    // WHEN
    service.getLeagueChallengerDataSoloQ().subscribe((res) => {
      expect(res).toBe(mockLeagueListDTO);
    });

    // THEN
    const url = environment.apiBaseUrl + '/league-challengers-solo-queue';
    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(mockLeagueListDTO);
  });

  it('should call getChallengersData and should get a list of data for flexQ 5v5', () => {
    // GIVEN
    const mockLeagueListDTO = {
      leagueId: '12345',
      tier: 'CHALLENGER',
      entries: [
        {
          summonerId: 'summoner123',
          puuid: '1234',
          leaguePoints: 1000,
          rank: 'I',
          wins: 100,
          losses: 50,
          veteran: false,
          inactive: false,
          freshBlood: true,
          hotStreak: false,
        },
      ],
      queue: 'RANKED_SOLO_5x5',
      name: 'Challenger League',
    } as LeagueListDTO;

    // WHEN
    service.getLeagueChallengerDataFlexQ().subscribe((res) => {
      expect(res).toBe(mockLeagueListDTO);
    });

    // THEN
    const url = environment.apiBaseUrl + '/league-challengers-flex-queue';
    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(mockLeagueListDTO);
  });

  it('should have leagueChallengerSoloQListDTOSignal initialized as null', () => {
    expect(service.leagueChallengerSoloQListDTOSignal()).toBeNull();
  });

  it('should have leagueChallengerFlexListDTOSignal initialized as null', () => {
    expect(service.leagueChallengerFlexListDTOSignal()).toBeNull();
  });

  it('should call getAccountByPuuid', () => {
    // GIVEN
    const accountDTOMock = { puuid: '12345', gameName: 'test name', tagLine: 'EUW' } as AccountDTO;

    // WHEN
    service.getAccountByPuuid('12345').subscribe((res) => {
      expect(res).toBe(accountDTOMock);
    });

    // THEN
    const url = environment.apiBaseUrl + '/account/12345';
    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(accountDTOMock);
  });

  it('should call getSUmmonerByPuuid', () => {
    // GIVEN
    const summonerDTOMock = {
      id: 'summonerId123',
      accountId: 'accountId123',
      puuid: '12345',
      name: 'SummonerName',
      profileIconId: 1234,
      revisionDate: 1680000000000,
      summonerLevel: 100,
    } as SummonerDTO;

    // WHEN
    service.getSummonerByPuuid('12345').subscribe((res) => {
      expect(res).toBe(summonerDTOMock);
    });

    // THEN
    const url = environment.apiBaseUrl + '/summoner/12345';
    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(summonerDTOMock);
  });
});
