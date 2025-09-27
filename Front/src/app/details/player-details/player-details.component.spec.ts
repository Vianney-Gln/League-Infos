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
import { clickButtonByDataTestAttr, getByDataTestAttr } from '../../common/utils/utils-tests';
import { HistoryService } from '../../services/games-history/history.service';
import { MatchDTO } from '../../common/models/games-history/matchDTO';

describe('PlayerDetailsComponent', () => {
  let component: PlayerDetailsComponent;
  let fixture: ComponentFixture<PlayerDetailsComponent>;
  let playerService: PlayersService;
  let historyService: HistoryService;
  let paramMap$: any;
  let router: Router;

  function matchDTOMock() {
    return {
      metadata: {
        matchId: '12345',
        participants: ['123'],
        dataVersion: '1',
      },
      info: {
        gameDuration: 1800,
        gameCreation: 1620000000000,
        queueId: 420,
        participants: [mockMatchParticipant()],
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

  function matchDTOOlderMock() {
    return {
      metadata: {
        matchId: '567',
        participants: ['1234'],
        dataVersion: '1',
      },
      info: {
        gameDuration: 1800,
        gameCreation: 1619999999000,
        queueId: 440,
        participants: [mockMatchParticipant()],
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

  function mockMatchParticipant() {
    return {
      participantId: 1,
      profileIcon: 1234,
      assists: 8,
      kills: 10,
      deaths: 2,
      champLevel: 18,
      championId: 157,
      goldEarned: 15000,
      item0: 1055,
      item1: 3006,
      item2: 6672,
      item3: 3046,
      item4: 3031,
      item5: 3072,
      item6: 3363,
      championTransform: 0,
      pentaKills: 0,
      quadraKills: 1,
      tripleKills: 2,
      doubleKills: 3,
      summoner1Id: 4,
      summoner2Id: 7,
      teamId: 100,
      totalMinionsKilled: 200,
      neutralMinionsKilled: 30,
      championName: 'MockChampion',
      summonerName: 'MockSummoner',
      lane: 'MIDDLE',
      role: 'SOLO',
      puuid: '123',
      teamPosition: '',
      win: true,
      matchId: '12345',
      challenges: {
        kda: 9.0,
        gameLength: 2555,
      },
    };
  }

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
    historyService = TestBed.inject(HistoryService);
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

  it('should display a message if data is missing for RANKED_SOLO_5x5', () => {
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
      queueType: 'RANKED_FLEX_SR',
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

    spyOn(playerService, 'getAccountByRiotId').and.returnValue(of(mockAccountDTO));
    spyOn(playerService, 'getSummonerByPuuid').and.returnValue(of(mockSummonerDTO));
    spyOn(playerService, 'getLeagueEntryByPuuid').and.returnValue(of([mockLeagueEntryDTO]));
    spyOn(playerService, 'getChampionMasteriesDTO').and.returnValue(of([]));

    // WHEN
    fixture.detectChanges();

    // THEN
    const blocUnrankedSolo = getByDataTestAttr(fixture.debugElement, 'bloc-unranked-solo');
    expect(blocUnrankedSolo).toBeTruthy();
    expect(blocUnrankedSolo?.textContent).toEqual("Ce joueur n'est pas encore classé en Ranked Solo");
    expect(component.isUnrankedSoloQ).toBeTrue();
    expect(component.isUnrankedFlex).toBeFalse();
  });

  it('should display a message if data is missing for RANKED_FLEX_SR', () => {
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

    spyOn(playerService, 'getAccountByRiotId').and.returnValue(of(mockAccountDTO));
    spyOn(playerService, 'getSummonerByPuuid').and.returnValue(of(mockSummonerDTO));
    spyOn(playerService, 'getLeagueEntryByPuuid').and.returnValue(of([mockLeagueEntryDTO]));
    spyOn(playerService, 'getChampionMasteriesDTO').and.returnValue(of([]));

    // WHEN
    fixture.detectChanges();

    // THEN
    const blocUnrankedFlex = getByDataTestAttr(fixture.debugElement, 'bloc_unranked_flex');
    expect(blocUnrankedFlex).toBeTruthy();
    expect(blocUnrankedFlex?.textContent).toEqual("Ce joueur n'est pas encore classé en Ranked Flex");
    expect(component.isUnrankedSoloQ).toBeFalse();
    expect(component.isUnrankedFlex).toBeTrue();
  });

  it('should reset itemsHistory', () => {
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

    const currentMatchsParticipantsFlexQSignalSpy = spyOn(component.currentMatchsParticipantsFlexQSignal, 'set');
    const currentMatchsParticipantsSoloQSignalSpy = spyOn(component.currentMatchsParticipantsSoloQSignal, 'set');

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(currentMatchsParticipantsFlexQSignalSpy).toHaveBeenCalledWith([]);
    expect(currentMatchsParticipantsSoloQSignalSpy).toHaveBeenCalledWith([]);

    expect(component.currentMatchsParticipantsFlexQSignal()).toHaveSize(0);
    expect(component.currentMatchsParticipantsSoloQSignal()).toHaveSize(0);
  });

  it('should call historyService.getHistoryByPuuidAndQueueType on click on Historique_RANKED_SOLO_5x5 button', () => {
    // GIVEN
    const mockAccountDTO = {
      puuid: '123',
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
      puuid: '123',
      name: 'test',
      profileIconId: 1234,
      revisionDate: 1680000000000,
      summonerLevel: 30,
    } as SummonerDTO;

    const mockLeagueEntryDTO = {
      leagueId: 'mock-league-id',
      puuid: '123',
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

    spyOn(playerService, 'getAccountByRiotId').and.returnValue(of(mockAccountDTO));
    spyOn(playerService, 'getSummonerByPuuid').and.returnValue(of(mockSummonerDTO));
    spyOn(playerService, 'getLeagueEntryByPuuid').and.returnValue(of([mockLeagueEntryDTO]));
    spyOn(playerService, 'getChampionMasteriesDTO').and.returnValue(of([]));
    const listMatchDataSignalSetSpy = spyOn(historyService.listMatchDataSignal, 'set');
    const getHistoryByPuuidAndQueueTypeSpy = spyOn(historyService, 'getHistoryByPuuidAndQueueType').and.returnValue(of([matchDTOMock()]));
    fixture.detectChanges();

    // WHEN
    clickButtonByDataTestAttr(fixture.debugElement, 'button historique RANKED_SOLO_5x5');

    // THEN
    expect(getHistoryByPuuidAndQueueTypeSpy).toHaveBeenCalledWith('123', 420);
    expect(listMatchDataSignalSetSpy).toHaveBeenCalledWith([matchDTOMock()]);
    expect(component.currentMatchsParticipantsFlexQSignal()).toHaveSize(0);
    expect(component.currentMatchsParticipantsSoloQSignal()).toHaveSize(1);
    expect(component.currentMatchsParticipantsSoloQSignal()[0]).toEqual(mockMatchParticipant());
  });

  it('should call historyService.getHistoryByPuuidAndQueueType on click on Historique_Ranked_FLEX_SR button', () => {
    // GIVEN
    const mockAccountDTO = {
      puuid: '123',
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
      puuid: '123',
      name: 'test',
      profileIconId: 1234,
      revisionDate: 1680000000000,
      summonerLevel: 30,
    } as SummonerDTO;

    const mockLeagueEntryDTO = {
      leagueId: 'mock-league-id',
      puuid: '123',
      queueType: 'RANKED_FLEX_SR',
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

    spyOn(playerService, 'getAccountByRiotId').and.returnValue(of(mockAccountDTO));
    spyOn(playerService, 'getSummonerByPuuid').and.returnValue(of(mockSummonerDTO));
    spyOn(playerService, 'getLeagueEntryByPuuid').and.returnValue(of([mockLeagueEntryDTO]));
    spyOn(playerService, 'getChampionMasteriesDTO').and.returnValue(of([]));
    const listMatchDataSignalSetSpy = spyOn(historyService.listMatchDataSignal, 'set');

    const history = [matchDTOMock()];
    history[0].info.queueId = 440;

    const getHistoryByPuuidAndQueueTypeSpy = spyOn(historyService, 'getHistoryByPuuidAndQueueType').and.returnValue(of(history));
    fixture.detectChanges();

    // WHEN
    clickButtonByDataTestAttr(fixture.debugElement, 'button historique RANKED_FLEX_SR');

    // THEN
    expect(getHistoryByPuuidAndQueueTypeSpy).toHaveBeenCalledWith('123', 440);
    expect(listMatchDataSignalSetSpy).toHaveBeenCalledWith(history);
    expect(component.currentMatchsParticipantsSoloQSignal()).toHaveSize(0);
    expect(component.currentMatchsParticipantsFlexQSignal()).toHaveSize(1);
    expect(component.currentMatchsParticipantsFlexQSignal()[0]).toEqual(mockMatchParticipant());
  });

  it('should display get more button when current length list of game is equal to 10 case soloQ', () => {
    // GIVEN
    const mockAccountDTO = {
      puuid: '123',
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
      puuid: '123',
      name: 'test',
      profileIconId: 1234,
      revisionDate: 1680000000000,
      summonerLevel: 30,
    } as SummonerDTO;

    const mockLeagueEntryDTO = {
      leagueId: 'mock-league-id',
      puuid: '123',
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
    const history = [
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
    ];

    history.forEach((hist) => (hist.info.queueId = 420));

    spyOn(historyService, 'getHistoryByPuuidAndQueueType').and.returnValue(of(history));
    spyOn(playerService, 'getAccountByRiotId').and.returnValue(of(mockAccountDTO));
    spyOn(playerService, 'getSummonerByPuuid').and.returnValue(of(mockSummonerDTO));
    spyOn(playerService, 'getLeagueEntryByPuuid').and.returnValue(of([mockLeagueEntryDTO]));
    spyOn(playerService, 'getChampionMasteriesDTO').and.returnValue(of([]));

    // WHEN
    fixture.detectChanges();
    clickButtonByDataTestAttr(fixture.debugElement, 'button historique RANKED_SOLO_5x5');
    fixture.detectChanges();

    // THEN
    const getMoreHistoryButton = getByDataTestAttr(fixture.debugElement, 'more-history-soloQ-button');
    expect(getMoreHistoryButton).toBeTruthy();
  });

  it('should not display get more button when current length list of game is equal to 0 case soloQ', () => {
    // GIVEN
    const mockAccountDTO = {
      puuid: '123',
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
      puuid: '123',
      name: 'test',
      profileIconId: 1234,
      revisionDate: 1680000000000,
      summonerLevel: 30,
    } as SummonerDTO;

    const mockLeagueEntryDTO = {
      leagueId: 'mock-league-id',
      puuid: '123',
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

    spyOn(historyService, 'getHistoryByPuuidAndQueueType').and.returnValue(of([]));
    spyOn(playerService, 'getAccountByRiotId').and.returnValue(of(mockAccountDTO));
    spyOn(playerService, 'getSummonerByPuuid').and.returnValue(of(mockSummonerDTO));
    spyOn(playerService, 'getLeagueEntryByPuuid').and.returnValue(of([mockLeagueEntryDTO]));
    spyOn(playerService, 'getChampionMasteriesDTO').and.returnValue(of([]));

    // WHEN
    fixture.detectChanges();
    clickButtonByDataTestAttr(fixture.debugElement, 'button historique RANKED_SOLO_5x5');
    fixture.detectChanges();

    // THEN
    const getMoreHistoryButton = getByDataTestAttr(fixture.debugElement, 'more-history-soloQ-button');
    expect(getMoreHistoryButton).toBeFalsy();
  });

  it('should display get more button when current length list of game is a multiple to 10 case flexQ', () => {
    // GIVEN
    const mockAccountDTO = {
      puuid: '123',
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
      puuid: '123',
      name: 'test',
      profileIconId: 1234,
      revisionDate: 1680000000000,
      summonerLevel: 30,
    } as SummonerDTO;

    const mockLeagueEntryDTO = {
      leagueId: 'mock-league-id',
      puuid: '123',
      queueType: 'RANKED_FLEX_SR',
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
    const history = [
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
    ];

    history.forEach((hist) => (hist.info.queueId = 440));

    spyOn(historyService, 'getHistoryByPuuidAndQueueType').and.returnValue(of(history));
    spyOn(playerService, 'getAccountByRiotId').and.returnValue(of(mockAccountDTO));
    spyOn(playerService, 'getSummonerByPuuid').and.returnValue(of(mockSummonerDTO));
    spyOn(playerService, 'getLeagueEntryByPuuid').and.returnValue(of([mockLeagueEntryDTO]));
    spyOn(playerService, 'getChampionMasteriesDTO').and.returnValue(of([]));

    // WHEN
    fixture.detectChanges();
    clickButtonByDataTestAttr(fixture.debugElement, 'button historique RANKED_FLEX_SR');
    fixture.detectChanges();

    // THEN
    const getMoreHistoryButton = getByDataTestAttr(fixture.debugElement, 'more-history-flexQ-button');
    expect(getMoreHistoryButton).toBeTruthy();
  });

  it('should not display get more button when current length list of game is equal to 0 case flexQ', () => {
    // GIVEN
    const mockAccountDTO = {
      puuid: '123',
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
      puuid: '123',
      name: 'test',
      profileIconId: 1234,
      revisionDate: 1680000000000,
      summonerLevel: 30,
    } as SummonerDTO;

    const mockLeagueEntryDTO = {
      leagueId: 'mock-league-id',
      puuid: '123',
      queueType: 'RANKED_FLEX_SR',
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

    spyOn(historyService, 'getHistoryByPuuidAndQueueType').and.returnValue(of([]));
    spyOn(playerService, 'getAccountByRiotId').and.returnValue(of(mockAccountDTO));
    spyOn(playerService, 'getSummonerByPuuid').and.returnValue(of(mockSummonerDTO));
    spyOn(playerService, 'getLeagueEntryByPuuid').and.returnValue(of([mockLeagueEntryDTO]));
    spyOn(playerService, 'getChampionMasteriesDTO').and.returnValue(of([]));

    // WHEN
    fixture.detectChanges();
    clickButtonByDataTestAttr(fixture.debugElement, 'button historique RANKED_FLEX_SR');
    fixture.detectChanges();

    // THEN
    const getMoreHistoryButton = getByDataTestAttr(fixture.debugElement, 'more-history-flexQ-button');
    expect(getMoreHistoryButton).toBeFalsy();
  });

  it('should call historyService.getMoreHistory on click on Plus de parties button, case flex q', () => {
    // GIVEN
    const mockAccountDTO = {
      puuid: '123',
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
      puuid: '123',
      name: 'test',
      profileIconId: 1234,
      revisionDate: 1680000000000,
      summonerLevel: 30,
    } as SummonerDTO;

    const mockLeagueEntryDTO = {
      leagueId: 'mock-league-id',
      puuid: '123',
      queueType: 'RANKED_FLEX_SR',
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

    spyOn(playerService, 'getAccountByRiotId').and.returnValue(of(mockAccountDTO));
    spyOn(playerService, 'getSummonerByPuuid').and.returnValue(of(mockSummonerDTO));
    spyOn(playerService, 'getLeagueEntryByPuuid').and.returnValue(of([mockLeagueEntryDTO]));
    spyOn(playerService, 'getChampionMasteriesDTO').and.returnValue(of([]));

    const listMatchDataSignalSetSpy = spyOn(historyService.listMatchDataSignal, 'set').and.callThrough();
    const listMatchDataSignalUpdateSpy = spyOn(historyService.listMatchDataSignal, 'update').and.callThrough();

    const history = [
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
    ];
    const historyOlder = [
      matchDTOOlderMock(),
      matchDTOOlderMock(),
      matchDTOOlderMock(),
      matchDTOOlderMock(),
      matchDTOOlderMock(),
      matchDTOOlderMock(),
      matchDTOOlderMock(),
      matchDTOOlderMock(),
      matchDTOOlderMock(),
      matchDTOOlderMock(),
    ];

    historyOlder.forEach((hist) => {
      hist.info.queueId = 440;
    });

    history.forEach((hist) => {
      hist.info.queueId = 440;
      hist.info.gameCreation = 175125125125;
    });

    const getHistoryByPuuidAndQueueTypeSpy = spyOn(historyService, 'getHistoryByPuuidAndQueueType').and.returnValue(of(history));
    const getMoreHistorySpy = spyOn(historyService, 'getMoreHistory').and.returnValue(of(historyOlder));
    fixture.detectChanges();

    // WHEN
    clickButtonByDataTestAttr(fixture.debugElement, 'button historique RANKED_FLEX_SR');
    fixture.detectChanges();
    clickButtonByDataTestAttr(fixture.debugElement, 'more-history-flexQ-button');
    fixture.detectChanges();

    // THEN
    expect(getHistoryByPuuidAndQueueTypeSpy).toHaveBeenCalledWith('123', 440);
    expect(listMatchDataSignalSetSpy).toHaveBeenCalledWith(history);
    expect(getMoreHistorySpy).toHaveBeenCalledWith('123', 175125125125, 440);
    expect(listMatchDataSignalUpdateSpy).toHaveBeenCalled();
    expect(historyService.listMatchDataSignal()).toHaveSize(20);
  });

  it('should call historyService.getMoreHistory on click on Plus de parties button, case solo q', () => {
    // GIVEN
    const mockAccountDTO = {
      puuid: '123',
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
      puuid: '123',
      name: 'test',
      profileIconId: 1234,
      revisionDate: 1680000000000,
      summonerLevel: 30,
    } as SummonerDTO;

    const mockLeagueEntryDTO = {
      leagueId: 'mock-league-id',
      puuid: '123',
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

    spyOn(playerService, 'getAccountByRiotId').and.returnValue(of(mockAccountDTO));
    spyOn(playerService, 'getSummonerByPuuid').and.returnValue(of(mockSummonerDTO));
    spyOn(playerService, 'getLeagueEntryByPuuid').and.returnValue(of([mockLeagueEntryDTO]));
    spyOn(playerService, 'getChampionMasteriesDTO').and.returnValue(of([]));

    const listMatchDataSignalSetSpy = spyOn(historyService.listMatchDataSignal, 'set').and.callThrough();
    const listMatchDataSignalUpdateSpy = spyOn(historyService.listMatchDataSignal, 'update').and.callThrough();

    const history = [
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
    ];
    const historyOlder = [
      matchDTOOlderMock(),
      matchDTOOlderMock(),
      matchDTOOlderMock(),
      matchDTOOlderMock(),
      matchDTOOlderMock(),
      matchDTOOlderMock(),
      matchDTOOlderMock(),
      matchDTOOlderMock(),
      matchDTOOlderMock(),
      matchDTOOlderMock(),
    ];

    historyOlder.forEach((hist) => {
      hist.info.queueId = 420;
    });

    history.forEach((hist) => {
      hist.info.queueId = 420;
      hist.info.gameCreation = 175125125125;
    });

    const getHistoryByPuuidAndQueueTypeSpy = spyOn(historyService, 'getHistoryByPuuidAndQueueType').and.returnValue(of(history));
    const getMoreHistorySpy = spyOn(historyService, 'getMoreHistory').and.returnValue(of(historyOlder));
    fixture.detectChanges();

    // WHEN
    clickButtonByDataTestAttr(fixture.debugElement, 'button historique RANKED_SOLO_5x5');
    fixture.detectChanges();
    clickButtonByDataTestAttr(fixture.debugElement, 'more-history-soloQ-button');
    fixture.detectChanges();

    // THEN
    expect(getHistoryByPuuidAndQueueTypeSpy).toHaveBeenCalledWith('123', 420);
    expect(listMatchDataSignalSetSpy).toHaveBeenCalledWith(history);
    expect(getMoreHistorySpy).toHaveBeenCalledWith('123', 175125125125, 420);
    expect(listMatchDataSignalUpdateSpy).toHaveBeenCalled();
    expect(historyService.listMatchDataSignal()).toHaveSize(20);
  });

  it('should hide the getMore button if no result, and show a message instead, case solo q', () => {
    // GIVEN
    const mockAccountDTO = {
      puuid: '123',
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
      puuid: '123',
      name: 'test',
      profileIconId: 1234,
      revisionDate: 1680000000000,
      summonerLevel: 30,
    } as SummonerDTO;

    const mockLeagueEntryDTO = {
      leagueId: 'mock-league-id',
      puuid: '123',
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

    spyOn(playerService, 'getAccountByRiotId').and.returnValue(of(mockAccountDTO));
    spyOn(playerService, 'getSummonerByPuuid').and.returnValue(of(mockSummonerDTO));
    spyOn(playerService, 'getLeagueEntryByPuuid').and.returnValue(of([mockLeagueEntryDTO]));
    spyOn(playerService, 'getChampionMasteriesDTO').and.returnValue(of([]));

    spyOn(historyService.listMatchDataSignal, 'set').and.callThrough();
    spyOn(historyService.listMatchDataSignal, 'update').and.callThrough();

    const history = [
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
    ];

    history.forEach((hist) => {
      hist.info.queueId = 420;
    });

    spyOn(historyService, 'getHistoryByPuuidAndQueueType').and.returnValue(of(history));
    spyOn(historyService, 'getMoreHistory').and.returnValue(of([]));
    fixture.detectChanges();

    // WHEN
    clickButtonByDataTestAttr(fixture.debugElement, 'button historique RANKED_SOLO_5x5');
    fixture.detectChanges();
    clickButtonByDataTestAttr(fixture.debugElement, 'more-history-soloQ-button');
    fixture.detectChanges();

    // THEN
    expect(getByDataTestAttr(fixture.debugElement, 'more-history-soloQ-button')).toBeFalsy();
    expect(getByDataTestAttr(fixture.debugElement, 'no-more-history-message-soloQ')?.innerText).toEqual("Plus d'autre partie historique disponible");
  });

  it('should hide the getMore button if no result, and show a message instead, case flex q', () => {
    // GIVEN
    const mockAccountDTO = {
      puuid: '123',
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
      puuid: '123',
      name: 'test',
      profileIconId: 1234,
      revisionDate: 1680000000000,
      summonerLevel: 30,
    } as SummonerDTO;

    const mockLeagueEntryDTO = {
      leagueId: 'mock-league-id',
      puuid: '123',
      queueType: 'RANKED_FLEX_SR',
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

    spyOn(playerService, 'getAccountByRiotId').and.returnValue(of(mockAccountDTO));
    spyOn(playerService, 'getSummonerByPuuid').and.returnValue(of(mockSummonerDTO));
    spyOn(playerService, 'getLeagueEntryByPuuid').and.returnValue(of([mockLeagueEntryDTO]));
    spyOn(playerService, 'getChampionMasteriesDTO').and.returnValue(of([]));

    spyOn(historyService.listMatchDataSignal, 'set').and.callThrough();
    spyOn(historyService.listMatchDataSignal, 'update').and.callThrough();

    const history = [
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
      matchDTOMock(),
    ];

    history.forEach((hist) => {
      hist.info.queueId = 440;
    });

    spyOn(historyService, 'getHistoryByPuuidAndQueueType').and.returnValue(of(history));
    spyOn(historyService, 'getMoreHistory').and.returnValue(of([]));
    fixture.detectChanges();

    // WHEN
    clickButtonByDataTestAttr(fixture.debugElement, 'button historique RANKED_FLEX_SR');
    fixture.detectChanges();
    clickButtonByDataTestAttr(fixture.debugElement, 'more-history-flexQ-button');
    fixture.detectChanges();

    // THEN
    expect(getByDataTestAttr(fixture.debugElement, 'more-history-flexQ-button')).toBeFalsy();
    expect(getByDataTestAttr(fixture.debugElement, 'no-more-history-message-flexQ')?.innerText).toEqual("Plus d'autre partie historique disponible");
  });

  it('Should call initGetMoreButtonAndMessageState if url param changed ', () => {
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
    spyOn(playerService, 'getChampionMasteriesDTO').and.returnValue(of([]));
    spyOn(router, 'navigate').and.stub();

    fixture.detectChanges();

    // WHEN
    paramMap$.next(convertToParamMap({ summoner: 'another#euw' }));
    fixture.detectChanges();

    // THEN
    expect(component.isGetMoreButtonFlexQVisibleSignal()).toBeTrue();
    expect(component.isGetMoreButtonSoloQVisibleSignal()).toBeTrue();
    expect(component.noMoreHistoriqueMessageFlexQSignal()).toEqual('');
    expect(component.noMoreHistoriqueMessageSoloQSignal()).toEqual('');
  });
});
