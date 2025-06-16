import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayerDetailsComponent } from './player-details.component';
import { ActivatedRoute, convertToParamMap, Router } from '@angular/router';
import { BehaviorSubject, of, throwError } from 'rxjs';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { PlayersService } from '../../services/players/players.service';
import { GetVersionsService } from '../../services/versions/get-versions.service';
import { AccountDTO } from '../../common/models/accountDTO';
import { SummonerDTO } from '../../common/models/summonerDTO';

describe('PlayerDetailsComponent', () => {
  let component: PlayerDetailsComponent;
  let fixture: ComponentFixture<PlayerDetailsComponent>;
  let playerService: PlayersService;
  let paramMap$: any;
  let router: Router;

  beforeEach(async () => {
    paramMap$ = new BehaviorSubject(convertToParamMap({ summoner: 'test#euw' }));
    await TestBed.configureTestingModule({
      imports: [PlayerDetailsComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            paramMap: paramMap$.asObservable(),
          },
        },
        provideHttpClient(),
        provideHttpClientTesting(),
        PlayersService,
        GetVersionsService,
        Router,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(PlayerDetailsComponent);
    playerService = TestBed.inject(PlayersService);
    router = TestBed.inject(Router);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call services with correct params and return data for a given player', () => {
    // GIVEN
    const mockAccountDTO = {
      puuid: 'mock-puuid',
      gameName: 'test',
      tagLine: 'euw',
      summonerId: 'mock-summoner-id',
      profileIconId: 1234,
      summonerLevel: 30,
      revisionDate: 1680000000000,
      id: 'mock-id',
    } as AccountDTO;

    const mockSummonerDTO = {
      id: 'mock-summoner-id',
      accountId: 'mock-account-id',
      puuid: 'mock-puuid',
      name: 'test',
      profileIconId: 1234,
      revisionDate: 1680000000000,
      summonerLevel: 30,
    } as SummonerDTO;

    const getAccountByRiotIdSpy = spyOn(playerService, 'getAccountByRiotId').and.returnValue(of(mockAccountDTO));
    const getSummonerByPuuidSpy = spyOn(playerService, 'getSummonerByPuuid').and.returnValue(of(mockSummonerDTO));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getAccountByRiotIdSpy).toHaveBeenCalledWith('test', 'euw');
    expect(getSummonerByPuuidSpy).toHaveBeenCalledWith('mock-puuid');
    expect(component.gameName).toEqual('test');
    expect(component.summonerDto).toEqual(mockSummonerDTO);
  });

  it('should call playerService.getAccountByRiotId with correct params, return empty data and not call playerService.getSummonerByPuuid in case of error', () => {
    // GIVEN
    const getAccountByRiotIdSpy = spyOn(playerService, 'getAccountByRiotId').and.returnValue(throwError(() => new Error()));
    const getSummonerByPuuidSpy = spyOn(playerService, 'getSummonerByPuuid').and.returnValue(throwError(() => new Error()));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getAccountByRiotIdSpy).toHaveBeenCalledWith('test', 'euw');
    expect(getSummonerByPuuidSpy).not.toHaveBeenCalled();
    expect(component.gameName).toEqual('');
    expect(component.summonerDto).toEqual(new SummonerDTO());
  });

  it('should call playerService.getAccountByRiotId and playerService.getSummonerByPuuid with correct params and return empty data in case of error from playerService.getSummonerByPuuid', () => {
    // GIVEN
    const mockAccountDTO = {
      puuid: 'mock-puuid',
      gameName: 'test',
      tagLine: 'euw',
      summonerId: 'mock-summoner-id',
      profileIconId: 1234,
      summonerLevel: 30,
      revisionDate: 1680000000000,
      id: 'mock-id',
    } as AccountDTO;

    const getAccountByRiotIdSpy = spyOn(playerService, 'getAccountByRiotId').and.returnValue(of(mockAccountDTO));
    const getSummonerByPuuidSpy = spyOn(playerService, 'getSummonerByPuuid').and.returnValue(throwError(() => new Error()));
    const routerSpy = spyOn(router, 'navigate').and.stub();

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getAccountByRiotIdSpy).toHaveBeenCalledWith('test', 'euw');
    expect(getSummonerByPuuidSpy).toHaveBeenCalledWith('mock-puuid');
    expect(component.gameName).toEqual('');
    expect(component.summonerDto).toEqual(new SummonerDTO());
    expect(routerSpy).toHaveBeenCalledWith(['/NotFound']);
  });

  it('should not call any services and reset data gameName and summonerDTO in case of no tagline', () => {
    // GIVEN
    paramMap$.next(convertToParamMap({ summoner: 'test' }));
    const getAccountByRiotIdSpy = spyOn(playerService, 'getAccountByRiotId').and.returnValue(of());
    const getSummonerByPuuidSpy = spyOn(playerService, 'getSummonerByPuuid').and.returnValue(of());

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getAccountByRiotIdSpy).not.toHaveBeenCalled();
    expect(getSummonerByPuuidSpy).not.toHaveBeenCalled();
    expect(component.gameName).toEqual('');
    expect(component.summonerDto).toEqual(new SummonerDTO());
  });
});
