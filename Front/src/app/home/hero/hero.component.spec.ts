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
import { GetChampionsService } from '../../services/champions/get-champions.service';
import { ChampionData } from '../../common/models/championsInfos';

describe('HeroComponentComponent', () => {
  let component: HeroComponent;
  let fixture: ComponentFixture<HeroComponent>;
  let playerservice: PlayersService;
  let getVersionService: GetVersionsService;
  let getChampionsService: GetChampionsService;
  let router: Router;

  const version1 = '14.10.2';
  const version2 = '13.10.2';
  const version3 = '13.9.2';
  const version4 = '13.9.1';
  const version5 = '13.9.0';
  const version6 = '13.8.6';
  const version7 = '13.8.5';
  const version8 = '13.8.4';
  const version9 = '13.8.3';
  const version10 = '13.8.2';
  const version11 = '13.8.1';
  const version12 = '13.8.0';
  const version13 = '13.7.11';
  const version14 = '13.10.10';
  const version15 = '13.7.9';
  const version16 = '13.7.8';
  const version17 = '13.7.7';
  const version18 = '13.7.6';
  const version19 = '13.7.5';
  const version20 = '13.7.4';

  const champDataV1 = {
    data: {
      Aatrox: { name: 'Aatrox' },
      Ahri: { name: 'Ahri' },
    },
    version: version1,
  };

  const champDataV2 = {
    data: {
      Aatrox: { name: 'Aatrox' },
      Ahri: { name: 'Ahri' },
    },
    version: version2,
  };

  const champDataV3 = {
    data: {
      Aatrox: { name: 'Aatrox' },
      Ahri: { name: 'Ahri' },
    },
    version: version3,
  };

  const champDataV4 = {
    data: {
      Aatrox: { name: 'Aatrox' },
      Ahri: { name: 'Ahri' },
    },
    version: version4,
  };

  const champDataV5 = {
    data: {
      Aatrox: { name: 'Aatrox' },
      Ahri: { name: 'Ahri' },
    },
    version: version5,
  };

  const champDataV6 = {
    data: {
      Aatrox: { name: 'Aatrox' },
      Ahri: { name: 'Ahri' },
    },
    version: version6,
  };

  const champDataV7 = {
    data: {
      Aatrox: { name: 'Aatrox' },
      Ahri: { name: 'Ahri' },
    },
    version: version7,
  };

  const champDataV8 = {
    data: {
      Aatrox: { name: 'Aatrox' },
      Ahri: { name: 'Ahri' },
    },
    version: version8,
  };

  const champDataV9 = {
    data: {
      Aatrox: { name: 'Aatrox' },
      Ahri: { name: 'Ahri' },
    },
    version: version9,
  };

  const champDataV10 = {
    data: {
      Aatrox: { name: 'Aatrox' },
      Ahri: { name: 'Ahri' },
    },
    version: version10,
  };

  const champDataV11 = {
    data: {
      Aatrox: { name: 'Aatrox' },
      Ahri: { name: 'Ahri' },
    },
    version: version11,
  };

  const champDataV12 = {
    data: {
      Aatrox: { name: 'Aatrox' },
      Ahri: { name: 'Ahri' },
    },
    version: version12,
  };

  const champDataV13 = {
    data: {
      Aatrox: { name: 'Aatrox' },
      Ahri: { name: 'Ahri' },
    },
    version: version13,
  };

  const champDataV14 = {
    data: {
      Aatrox: { name: 'Aatrox' },
      Ahri: { name: 'Ahri' },
    },
    version: version14,
  };

  const champDataV15 = {
    data: {
      Aatrox: { name: 'Aatrox' },
      Ahri: { name: 'Ahri' },
    },
    version: version15,
  };

  const champDataV16 = {
    data: {
      Aatrox: { name: 'Aatrox' },
      Ahri: { name: 'Ahri' },
    },
    version: version16,
  };

  const champDataV17 = {
    data: {
      Aatrox: { name: 'Aatrox' },
      Ahri: { name: 'Ahri' },
    },
    version: version17,
  };

  const champDataV18 = {
    data: {
      Aatrox: { name: 'Aatrox' },
      Ahri: { name: 'Ahri' },
    },
    version: version18,
  };

  const champDataV19 = {
    data: {
      Aatrox: { name: 'Aatrox' },
      Ahri: { name: 'Ahri' },
    },
    version: version19,
  };

  const champDataV20 = {
    data: {
      Aatrox: { name: 'Aatrox' },
      Ahri: { name: 'Ahri' },
    },
    version: version20,
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeroComponent],
      providers: [GetChampionsService, GetVersionsService, PlayersService, provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    playerservice = TestBed.inject(PlayersService);
    getVersionService = TestBed.inject(GetVersionsService);
    getChampionsService = TestBed.inject(GetChampionsService);
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
      expect(routerSpy).toHaveBeenCalledWith(['Detail-summoner/test name#EUW']);
      fixture.destroy();
    });
  });

  it('should call getAllChampionsInfos as much as there is versions in the list and find the most recent champion', () => {
    // GIVEN
    const champDataV20WithNewChamp = {
      data: {
        Aatrox: { name: 'Aatrox' },
        Ahri: { name: 'Ahri' },
        Aurora: { name: 'Aurora' },
      },
      version: version20,
    };

    getVersionService.lastTwentyVersionslolSignal.set([
      version20,
      version19,
      version18,
      version17,
      version16,
      version15,
      version14,
      version13,
      version12,
      version11,
      version10,
      version9,
      version8,
      version7,
      version6,
      version5,
      version4,
      version3,
      version2,
      version1,
    ]);

    // Spy sur le service
    spyOn(getChampionsService, 'getAllChampionsInfos').and.callFake((version: string) => {
      if (version === version1) return of({ ...champDataV1, type: 'champion', format: 'standAloneComplex' });
      if (version === version2) return of({ ...champDataV2, type: 'champion', format: 'standAloneComplex' });
      if (version === version3) return of({ ...champDataV3, type: 'champion', format: 'standAloneComplex' });
      if (version === version4) return of({ ...champDataV4, type: 'champion', format: 'standAloneComplex' });
      if (version === version5) return of({ ...champDataV5, type: 'champion', format: 'standAloneComplex' });
      if (version === version6) return of({ ...champDataV6, type: 'champion', format: 'standAloneComplex' });
      if (version === version7) return of({ ...champDataV7, type: 'champion', format: 'standAloneComplex' });
      if (version === version8) return of({ ...champDataV8, type: 'champion', format: 'standAloneComplex' });
      if (version === version9) return of({ ...champDataV9, type: 'champion', format: 'standAloneComplex' });
      if (version === version10) return of({ ...champDataV10, type: 'champion', format: 'standAloneComplex' });
      if (version === version11) return of({ ...champDataV11, type: 'champion', format: 'standAloneComplex' });
      if (version === version12) return of({ ...champDataV12, type: 'champion', format: 'standAloneComplex' });
      if (version === version13) return of({ ...champDataV13, type: 'champion', format: 'standAloneComplex' });
      if (version === version14) return of({ ...champDataV14, type: 'champion', format: 'standAloneComplex' });
      if (version === version15) return of({ ...champDataV15, type: 'champion', format: 'standAloneComplex' });
      if (version === version16) return of({ ...champDataV16, type: 'champion', format: 'standAloneComplex' });
      if (version === version17) return of({ ...champDataV17, type: 'champion', format: 'standAloneComplex' });
      if (version === version18) return of({ ...champDataV18, type: 'champion', format: 'standAloneComplex' });
      if (version === version19) return of({ ...champDataV19, type: 'champion', format: 'standAloneComplex' });
      if (version === version20) return of({ ...champDataV20WithNewChamp, type: 'champion', format: 'standAloneComplex' });
      return of({ type: 'champion', format: 'standAloneComplex', data: {}, version });
    });

    // Spy sur la mise à jour du signal
    const signalSpy = spyOn(component.mostRecentChampionDtoSignal, 'set');

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(signalSpy).toHaveBeenCalledOnceWith(jasmine.objectContaining({ name: 'Aurora' }));
  });

  it('should call getAllChampionsInfos as much as there is versions in the list and dont set any signal if no champ finded', () => {
    // GIVEN
    getVersionService.lastTwentyVersionslolSignal.set([
      version20,
      version19,
      version18,
      version17,
      version16,
      version15,
      version14,
      version13,
      version12,
      version11,
      version10,
      version9,
      version8,
      version7,
      version6,
      version5,
      version4,
      version3,
      version2,
      version1,
    ]);

    // Spy sur le service
    spyOn(getChampionsService, 'getAllChampionsInfos').and.callFake((version: string) => {
      if (version === version1) return of({ ...champDataV1, type: 'champion', format: 'standAloneComplex' });
      if (version === version2) return of({ ...champDataV2, type: 'champion', format: 'standAloneComplex' });
      if (version === version3) return of({ ...champDataV3, type: 'champion', format: 'standAloneComplex' });
      if (version === version4) return of({ ...champDataV4, type: 'champion', format: 'standAloneComplex' });
      if (version === version5) return of({ ...champDataV5, type: 'champion', format: 'standAloneComplex' });
      if (version === version6) return of({ ...champDataV6, type: 'champion', format: 'standAloneComplex' });
      if (version === version7) return of({ ...champDataV7, type: 'champion', format: 'standAloneComplex' });
      if (version === version8) return of({ ...champDataV8, type: 'champion', format: 'standAloneComplex' });
      if (version === version9) return of({ ...champDataV9, type: 'champion', format: 'standAloneComplex' });
      if (version === version10) return of({ ...champDataV10, type: 'champion', format: 'standAloneComplex' });
      if (version === version11) return of({ ...champDataV11, type: 'champion', format: 'standAloneComplex' });
      if (version === version12) return of({ ...champDataV12, type: 'champion', format: 'standAloneComplex' });
      if (version === version13) return of({ ...champDataV13, type: 'champion', format: 'standAloneComplex' });
      if (version === version14) return of({ ...champDataV14, type: 'champion', format: 'standAloneComplex' });
      if (version === version15) return of({ ...champDataV15, type: 'champion', format: 'standAloneComplex' });
      if (version === version16) return of({ ...champDataV16, type: 'champion', format: 'standAloneComplex' });
      if (version === version17) return of({ ...champDataV17, type: 'champion', format: 'standAloneComplex' });
      if (version === version18) return of({ ...champDataV18, type: 'champion', format: 'standAloneComplex' });
      if (version === version19) return of({ ...champDataV19, type: 'champion', format: 'standAloneComplex' });
      if (version === version20) return of({ ...champDataV20, type: 'champion', format: 'standAloneComplex' });
      return of({ type: 'champion', format: 'standAloneComplex', data: {}, version });
    });

    // Spy sur la mise à jour du signal
    const signalSpy = spyOn(component.mostRecentChampionDtoSignal, 'set');

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(signalSpy).not.toHaveBeenCalled();
  });

  it('should call getMostBannedChampionId and get the most banned champion', () => {
    // GIVEN
    const mockChampionData = {
      data: {
        Aatrox: {
          id: '266',
          name: 'Aatrox',
          key: '266',
          version: '1.0',
          title: 'the Darkin Blade',
          blurb: 'Once honored defenders of Shurima...',
          info: { attack: 8, defense: 4, magic: 3, difficulty: 4 },
          image: { full: 'Aatrox.png', sprite: 'champion0.png', group: 'champion', x: 0, y: 0, w: 48, h: 48 },
          tags: ['Fighter', 'Tank'],
          partype: 'Blood Well',
          stats: {
            hp: 580,
            hpperlevel: 90,
            mp: 0,
            mpperlevel: 0,
            movespeed: 345,
            armor: 38,
            armorperlevel: 4.5,
            spellblock: 32,
            spellblockperlevel: 2.05,
            attackrange: 175,
            hpregen: 3,
            hpregenperlevel: 1,
            mpregen: 0,
            mpregenperlevel: 0,
            crit: 0,
            critperlevel: 0,
            attackdamage: 60,
            attackdamageperlevel: 5,
            attackspeedperlevel: 2.5,
            attackspeed: 0.651,
          },
        },
        Ahri: {
          id: '103',
          name: 'Ahri',
          key: '103',
          version: '1.0',
          title: 'the Nine-Tailed Fox',
          blurb: 'Innately connected to the magic...',
          info: { attack: 3, defense: 4, magic: 8, difficulty: 5 },
          image: { full: 'Ahri.png', sprite: 'champion0.png', group: 'champion', x: 48, y: 0, w: 48, h: 48 },
          tags: ['Mage', 'Assassin'],
          partype: 'Mana',
          stats: {
            hp: 526,
            hpperlevel: 92,
            mp: 418,
            mpperlevel: 25,
            movespeed: 330,
            armor: 21,
            armorperlevel: 3.5,
            spellblock: 30,
            spellblockperlevel: 0.5,
            attackrange: 550,
            hpregen: 6.5,
            hpregenperlevel: 0.6,
            mpregen: 8,
            mpregenperlevel: 0.8,
            crit: 0,
            critperlevel: 0,
            attackdamage: 53,
            attackdamageperlevel: 3,
            attackspeedperlevel: 2,
            attackspeed: 0.668,
          },
        },
        Aurora: {
          id: '123',
          name: 'Aurora',
          key: '123',
          version: '1.0',
          title: 'the New Champion',
          blurb: 'A mysterious new champion...',
          info: { attack: 5, defense: 5, magic: 5, difficulty: 5 },
          image: { full: 'Aurora.png', sprite: 'champion0.png', group: 'champion', x: 96, y: 0, w: 48, h: 48 },
          tags: ['Mage'],
          partype: 'Mana',
          stats: {
            hp: 500,
            hpperlevel: 80,
            mp: 400,
            mpperlevel: 20,
            movespeed: 340,
            armor: 25,
            armorperlevel: 3,
            spellblock: 30,
            spellblockperlevel: 0.5,
            attackrange: 525,
            hpregen: 6,
            hpregenperlevel: 0.5,
            mpregen: 7,
            mpregenperlevel: 0.7,
            crit: 0,
            critperlevel: 0,
            attackdamage: 50,
            attackdamageperlevel: 3,
            attackspeedperlevel: 2,
            attackspeed: 0.65,
          },
        },
      },
      version: version1,
      type: 'champion',
      format: 'standAloneComplex',
    };

    getChampionsService.championDataSignal.set(mockChampionData);
    const getMostBannedChampIdSpy = spyOn(getChampionsService, 'getMostBannedChampionId').and.returnValue(of(123));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getMostBannedChampIdSpy).toHaveBeenCalled();
    expect(component.mostBannedChampSignal()).toEqual(mockChampionData.data['Aurora']);
    expect(component.urlBackgroundMostBannedChampion()).toEqual('url(https://lolg-cdn.porofessor.gg/img/d/champion-banners/123.jpg)');
  });
});
