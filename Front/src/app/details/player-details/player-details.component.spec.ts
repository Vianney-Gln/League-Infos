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
import { LeagueEntryDTO } from '../../common/models/LeagueEntryDTO';
import { ChampionMasteryDto } from '../../common/models/ChampionMasteryDto';
import { getByDataTestAttr } from '../../common/utils/utils-tests';

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

    const mockLeagueEntryDTO = {
      leagueId: 'mock-league-id',
      puuid: 'mock-puuid',
      queueType: 'RANKED_SOLO_5x5',
      tier: 'GOLD',
      rank: 'IV',
      summonerId: 'mock-summoner-id',
      summonerName: 'test',
      leaguePoints: 50,
      wins: 20,
      losses: 15,
      veteran: false,
      inactive: false,
      freshBlood: true,
      hotStreak: false,
    } as LeagueEntryDTO;

    const getAccountByRiotIdSpy = spyOn(playerService, 'getAccountByRiotId').and.returnValue(of(mockAccountDTO));
    const getSummonerByPuuidSpy = spyOn(playerService, 'getSummonerByPuuid').and.returnValue(of(mockSummonerDTO));
    const getLeagueEntryByPuuidSpy = spyOn(playerService, 'getLeagueEntryByPuuid').and.returnValue(of([mockLeagueEntryDTO]));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getAccountByRiotIdSpy).toHaveBeenCalledWith('test', 'euw');
    expect(getSummonerByPuuidSpy).toHaveBeenCalledWith('mock-puuid');
    expect(component.gameName).toEqual('test');
    expect(component.summonerDto).toEqual(mockSummonerDTO);

    expect(getLeagueEntryByPuuidSpy).toHaveBeenCalledWith('mock-puuid');
    expect(component.leagueEntriesSignal()).toHaveSize(1);
    expect(component.leagueEntriesSignal()[0]).toEqual(mockLeagueEntryDTO);
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
    expect(component.tagLine).toEqual('euw');
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

  [
    { tier: 'IRON', url: 'images/Emblems/emblem-iron.png' },
    { tier: 'BRONZE', url: 'images/Emblems/emblem-bronze.png' },
    { tier: 'SILVER', url: 'images/Emblems/emblem-silver.png' },
    { tier: 'GOLD', url: 'images/Emblems/emblem-gold.png' },
    { tier: 'PLATINUM', url: 'images/Emblems/emblem-platinum.png' },
    { tier: 'EMERALD', url: 'images/Emblems/emblem-emerald.png' },
    { tier: 'DIAMOND', url: 'images/Emblems/emblem-diamond.png' },
    { tier: 'MASTER', url: 'images/Emblems/emblem-master.png' },
    { tier: 'GRANDMASTER', url: 'images/Emblems/emblem-grandmaster.png' },
    { tier: 'CHALLENGER', url: 'images/Emblems/emblem-challenger.png' },
  ].forEach((rank) => {
    it('should correctly compute url of emblem img', () => {
      // GIVEN + WHEN
      const url = component.computeTierEmblem(rank.tier);

      // THEN
      expect(url).toEqual(rank.url);
    });
  });

  it("should call playerService.getChampionMasteriesDTO and update compute url's background-image", () => {
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

    const championMasteriesDTOMock = {
      puuid: 'mock-puuid',
      championId: 266,
      championLevel: 7,
      championPoints: 123456,
      lastPlayTime: 1680000000000,
      championPointsSinceLastLevel: 0,
      championPointsUntilNextLevel: 0,
      markRequiredForNextLevel: 12,
      chestGranted: true,
    } as ChampionMasteryDto;

    spyOn(playerService, 'getAccountByRiotId').and.returnValue(of(mockAccountDTO));
    spyOn(playerService, 'getSummonerByPuuid').and.returnValue(of(mockSummonerDTO));
    spyOn(playerService, 'getLeagueEntryByPuuid').and.returnValue(of([new LeagueEntryDTO()]));
    spyOn(router, 'navigate').and.stub();

    const getChampionMasteriesDTOSpy = spyOn(playerService, 'getChampionMasteriesDTO').and.returnValue(of([championMasteriesDTOMock]));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getChampionMasteriesDTOSpy).toHaveBeenCalledOnceWith('mock-puuid');
    expect(component.championMasteriesSignal()).toHaveSize(1);
    expect(component.championMasteriesSignal()[0]).toEqual(championMasteriesDTOMock);
    expect(component.urlBackgroundBannerSignal()).toEqual(`url(https://lolg-cdn.porofessor.gg/img/d/champion-banners/266.jpg)`);
  });

  it('should call playerService.getChampionMasteriesDTO and display a default banner if no data', () => {
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

    spyOn(playerService, 'getAccountByRiotId').and.returnValue(of(mockAccountDTO));
    spyOn(playerService, 'getSummonerByPuuid').and.returnValue(of(mockSummonerDTO));
    spyOn(playerService, 'getLeagueEntryByPuuid').and.returnValue(of([new LeagueEntryDTO()]));
    spyOn(router, 'navigate').and.stub();

    const getChampionMasteriesDTOSpy = spyOn(playerService, 'getChampionMasteriesDTO').and.returnValue(of([]));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getChampionMasteriesDTOSpy).toHaveBeenCalledOnceWith('mock-puuid');
    expect(component.championMasteriesSignal()).toHaveSize(0);
    expect(component.urlBackgroundBannerSignal()).toEqual(`url(images/default-banner.png)`);
  });

  it('should call playerService.getChampionMasteriesDTO and display a default bloc message if not data from getleagueEntries', () => {
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

    spyOn(playerService, 'getAccountByRiotId').and.returnValue(of(mockAccountDTO));
    spyOn(playerService, 'getSummonerByPuuid').and.returnValue(of(mockSummonerDTO));
    spyOn(playerService, 'getLeagueEntryByPuuid').and.returnValue(of([]));
    spyOn(router, 'navigate').and.stub();

    spyOn(playerService, 'getChampionMasteriesDTO').and.returnValue(of([]));

    // WHEN
    fixture.detectChanges();

    // THEN
    const blocdefaultMessage = getByDataTestAttr(fixture.debugElement, 'bloc-default-no-data-league-entry');
    const blocDataLeagueEntry = getByDataTestAttr(fixture.debugElement, 'bloc-data-league-entry');
    expect(blocdefaultMessage).toBeTruthy();
    expect(blocdefaultMessage?.textContent).toEqual(
      "Il n'y a pas d'informations sur ce joueur.Il n'a peut être pas encore joué de parties classées ou est peut être inactif."
    );
    expect(blocDataLeagueEntry).toBeFalsy();
  });
});
