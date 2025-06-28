import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';

import { HeroComponent } from './hero.component';
import { PlayersService } from '../../services/players/players.service';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { of } from 'rxjs';
import { LeagueListDTO } from '../../common/models/leagueListDTO';
import { AccountDTO } from '../../common/models/accountDTO';
import { SummonerDTO } from '../../common/models/summonerDTO';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ChampionMasteryDto } from '../../common/models/ChampionMasteryDto';
import { clickButtonByDataTestAttr, getByDataTestAttr } from '../../common/utils/utils-tests';
import { Router } from '@angular/router';
import { GetVersionsService } from '../../services/versions/get-versions.service';

describe('HeroComponentComponent', () => {
  let component: HeroComponent;
  let fixture: ComponentFixture<HeroComponent>;
  let playerservice: PlayersService;
  let getVersionService: GetVersionsService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeroComponent],
      providers: [GetVersionsService, PlayersService, provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    playerservice = TestBed.inject(PlayersService);
    getVersionService = TestBed.inject(GetVersionsService);
    fixture = TestBed.createComponent(HeroComponent);
    router = TestBed.inject(Router);
    component = fixture.componentInstance;
  });

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

  const mockLeagueListDTOFlex = {
    leagueId: '23456',
    tier: 'CHALLENGER',
    entries: [
      {
        summonerId: 'summoner345',
        puuid: '678',
        leaguePoints: 1370,
        rank: 'I',
        wins: 120,
        losses: 77,
        veteran: false,
        inactive: false,
        freshBlood: true,
        hotStreak: false,
      },
    ],
    queue: 'RANKED_FLEX_5x5',
    name: 'Challenger League',
  } as LeagueListDTO;

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call getLeagueChallengerDataSoloQ', () => {
    // GIVEN
    const getLeagueChallengerDataSoloQSpy = spyOn(playerservice, 'getLeagueChallengerDataSoloQ').and.returnValue(of(mockLeagueListDTO));
    const leagueChallengerListDTOSignalSpy = spyOn(playerservice.leagueChallengerSoloQListDTOSignal, 'set').and.callThrough();

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getLeagueChallengerDataSoloQSpy).toHaveBeenCalled();
    expect(leagueChallengerListDTOSignalSpy).toHaveBeenCalledWith(mockLeagueListDTO);
    expect(component.firstChallengerPlayerSoloQSignal()).toBe(mockLeagueListDTO.entries[0]);
    expect(component.leagueBestPlayerSoloQ).toEqual('CHALLENGER');
  });

  it('should call getAccountByPuuid and get the gameName of the best player Challenger soloQ', () => {
    // GIVEN
    spyOn(playerservice, 'getLeagueChallengerDataSoloQ').and.returnValue(of(mockLeagueListDTO));
    const accountDTOMock = { puuid: '12345', gameName: 'test name', tagLine: 'EUW' } as AccountDTO;
    const getAccountByPuuidSpy = spyOn(playerservice, 'getAccountByPuuid').and.returnValue(of(accountDTOMock));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getAccountByPuuidSpy).toHaveBeenCalledWith('1234');
    expect(component.gameNameBestChalPlayerSoloQ).toEqual('test name');
  });

  it('should call getSummonerByPuuid and get the summonerDTO of the best player Challenger soloQ', () => {
    // GIVEN
    spyOn(playerservice, 'getLeagueChallengerDataSoloQ').and.returnValue(of(mockLeagueListDTO));
    const accountDTOMock = { puuid: '12345', gameName: 'test name', tagLine: 'EUW' } as AccountDTO;
    const summonerDTOMock = {
      puuid: '12345',
      id: 'summoner123',
      profileIconId: 123,
      summonerLevel: 30,
    } as SummonerDTO;
    spyOn(playerservice, 'getChampionMasteriesDTO').and.returnValue(of([]));
    spyOn(playerservice, 'getAccountByPuuid').and.returnValue(of(accountDTOMock));
    const getSummonerSpy = spyOn(playerservice, 'getSummonerByPuuid').and.returnValue(of(summonerDTOMock));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getSummonerSpy).toHaveBeenCalled();
    expect(component.firstChallengerSummonerSoloQSignal()).toEqual(summonerDTOMock);
  });

  it('should call getLeagueChallengerDataFlex', () => {
    // GIVEN
    const getLeagueChallengerDataFlexSpy = spyOn(playerservice, 'getLeagueChallengerDataFlexQ').and.returnValue(of(mockLeagueListDTOFlex));
    const leagueChallengerListDTOSignalSpy = spyOn(playerservice.leagueChallengerFlexListDTOSignal, 'set').and.callThrough();

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getLeagueChallengerDataFlexSpy).toHaveBeenCalled();
    expect(leagueChallengerListDTOSignalSpy).toHaveBeenCalledWith(mockLeagueListDTOFlex);
    expect(component.firstChallengerPlayerFlexSignal()).toBe(mockLeagueListDTOFlex.entries[0]);
    expect(component.leagueBestPlayerFlex).toEqual('CHALLENGER');
  });

  it('should call getAccountByPuuid and get the gameName of the best player Challenger flexQ', () => {
    // GIVEN
    spyOn(playerservice, 'getLeagueChallengerDataFlexQ').and.returnValue(of(mockLeagueListDTOFlex));
    const accountDTOMock = { puuid: '12345', gameName: 'test name', tagLine: 'EUW' } as AccountDTO;
    const getAccountByPuuidSpy = spyOn(playerservice, 'getAccountByPuuid').and.returnValue(of(accountDTOMock));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getAccountByPuuidSpy).toHaveBeenCalledWith('678');
    expect(component.gameNameBestChalPlayerFlex).toEqual('test name');
  });

  it('should call getChampionMasteriesDTO and compute url background bannner case flexQ', () => {
    // GIVEN
    const championMasteriesDTOMock = {
      puuid: '12345',
      championId: 266,
      championLevel: 7,
      championPoints: 123456,
      lastPlayTime: 1680000000000,
      championPointsSinceLastLevel: 0,
      championPointsUntilNextLevel: 0,
      markRequiredForNextLevel: 12,
      chestGranted: true,
    } as ChampionMasteryDto;

    spyOn(playerservice, 'getLeagueChallengerDataFlexQ').and.returnValue(of(mockLeagueListDTOFlex));
    const accountDTOMock = { puuid: '12345', gameName: 'test name', tagLine: 'EUW' } as AccountDTO;
    const summonerDTOMock = {
      puuid: '12345',
      id: 'summoner123',
      profileIconId: 123,
      summonerLevel: 30,
    } as SummonerDTO;
    spyOn(playerservice, 'getAccountByPuuid').and.returnValue(of(accountDTOMock));
    spyOn(playerservice, 'getSummonerByPuuid').and.returnValue(of(summonerDTOMock));
    const getChampionMasteriesDTOSpy = spyOn(playerservice, 'getChampionMasteriesDTO').and.returnValue(of([championMasteriesDTOMock]));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getChampionMasteriesDTOSpy).toHaveBeenCalledWith('12345');
    expect(component.urlBackgroundBannerFlexQSignal()).toEqual('url(https://lolg-cdn.porofessor.gg/img/d/champion-banners/266.jpg)');
  });

  it('should call getChampionMasteriesDTO and compute url background bannner case soloQ', () => {
    // GIVEN
    const championMasteriesDTOMock = {
      puuid: '12345',
      championId: 266,
      championLevel: 7,
      championPoints: 123456,
      lastPlayTime: 1680000000000,
      championPointsSinceLastLevel: 0,
      championPointsUntilNextLevel: 0,
      markRequiredForNextLevel: 12,
      chestGranted: true,
    } as ChampionMasteryDto;

    spyOn(playerservice, 'getLeagueChallengerDataSoloQ').and.returnValue(of(mockLeagueListDTO));
    const accountDTOMock = { puuid: '12345', gameName: 'test name', tagLine: 'EUW' } as AccountDTO;
    const summonerDTOMock = {
      puuid: '12345',
      id: 'summoner123',
      profileIconId: 123,
      summonerLevel: 30,
    } as SummonerDTO;
    spyOn(playerservice, 'getAccountByPuuid').and.returnValue(of(accountDTOMock));
    spyOn(playerservice, 'getSummonerByPuuid').and.returnValue(of(summonerDTOMock));
    const getChampionMasteriesDTOSpy = spyOn(playerservice, 'getChampionMasteriesDTO').and.returnValue(of([championMasteriesDTOMock]));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getChampionMasteriesDTOSpy).toHaveBeenCalledWith('12345');
    expect(component.urlBackgroundBannerSoloQSignal()).toEqual('url(https://lolg-cdn.porofessor.gg/img/d/champion-banners/266.jpg)');
  });

  it('should call getChampionMasteriesDTO and compute a default url background bannner if no data case soloQ', () => {
    // GIVEN
    spyOn(playerservice, 'getLeagueChallengerDataSoloQ').and.returnValue(of(mockLeagueListDTO));
    const accountDTOMock = { puuid: '12345', gameName: 'test name', tagLine: 'EUW' } as AccountDTO;
    const summonerDTOMock = {
      puuid: '12345',
      id: 'summoner123',
      profileIconId: 123,
      summonerLevel: 30,
    } as SummonerDTO;
    spyOn(playerservice, 'getAccountByPuuid').and.returnValue(of(accountDTOMock));
    spyOn(playerservice, 'getSummonerByPuuid').and.returnValue(of(summonerDTOMock));
    const getChampionMasteriesDTOSpy = spyOn(playerservice, 'getChampionMasteriesDTO').and.returnValue(of([]));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getChampionMasteriesDTOSpy).toHaveBeenCalledWith('12345');
    expect(component.urlBackgroundBannerSoloQSignal()).toEqual('url(images/default-banner.png)');
  });

  it('should call getChampionMasteriesDTO and compute a default url background bannner if no data case flexQ', () => {
    // GIVEN
    spyOn(playerservice, 'getLeagueChallengerDataFlexQ').and.returnValue(of(mockLeagueListDTOFlex));
    const accountDTOMock = { puuid: '12345', gameName: 'test name', tagLine: 'EUW' } as AccountDTO;
    const summonerDTOMock = {
      puuid: '12345',
      id: 'summoner123',
      profileIconId: 123,
      summonerLevel: 30,
    } as SummonerDTO;
    spyOn(playerservice, 'getAccountByPuuid').and.returnValue(of(accountDTOMock));
    spyOn(playerservice, 'getSummonerByPuuid').and.returnValue(of(summonerDTOMock));
    const getChampionMasteriesDTOSpy = spyOn(playerservice, 'getChampionMasteriesDTO').and.returnValue(of([]));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getChampionMasteriesDTOSpy).toHaveBeenCalledWith('12345');
    expect(component.urlBackgroundBannerFlexQSignal()).toEqual('url(images/default-banner.png)');
  });

  ['bloc-best-challenger-player-soloQ', 'bloc-best-challenger-player-flexQ'].forEach((dataTest) => {
    it('should redirect user to DetailComponent when click on best challenger player from slides', () => {
      // GIVEN
      const accountDTOMock = { puuid: '12345', gameName: 'test name', tagLine: 'EUW' } as AccountDTO;
      const summonerDTOMock = {
        puuid: '12345',
        id: 'summoner123',
        profileIconId: 123,
        summonerLevel: 30,
      } as SummonerDTO;

      const championMasteriesDTOMock = {
        puuid: '12345',
        championId: 266,
        championLevel: 7,
        championPoints: 123456,
        lastPlayTime: 1680000000000,
        championPointsSinceLastLevel: 0,
        championPointsUntilNextLevel: 0,
        markRequiredForNextLevel: 12,
        chestGranted: true,
      } as ChampionMasteryDto;

      getVersionService.lastVersionlolDTOSignal.set('0');
      spyOn(playerservice, 'getLeagueChallengerDataSoloQ').and.returnValue(of(mockLeagueListDTO));
      spyOn(playerservice, 'getLeagueChallengerDataFlexQ').and.returnValue(of(mockLeagueListDTOFlex));
      spyOn(playerservice, 'getAccountByPuuid').and.returnValue(of(accountDTOMock));
      spyOn(playerservice, 'getSummonerByPuuid').and.returnValue(of(summonerDTOMock));
      spyOn(playerservice, 'getChampionMasteriesDTO').and.returnValue(of([championMasteriesDTOMock]));

      const routerSpy = spyOn(router, 'navigate').and.stub();
      fixture.detectChanges();
      const slideClickable = getByDataTestAttr(fixture.debugElement, dataTest);

      // WHEN
      slideClickable?.click();

      // THEN
      expect(routerSpy).toHaveBeenCalledWith(['Detail/test name#EUW']);
      fixture.destroy();
    });
  });
});
