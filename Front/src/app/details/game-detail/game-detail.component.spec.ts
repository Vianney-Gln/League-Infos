import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GameDetailComponent } from './game-detail.component';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ActivatedRoute, provideRouter, Router } from '@angular/router';
import { HistoryService } from '../../services/games-history/history.service';
import { of } from 'rxjs';
import { MatchDTO } from '../../common/models/games-history/matchDTO';
import { getByDataTestAttr } from '../../common/utils/utils-tests';

describe('GameDetailComponent', () => {
  let component: GameDetailComponent;
  let fixture: ComponentFixture<GameDetailComponent>;
  let historyService: HistoryService;

  function matchDTOMock() {
    return {
      metadata: {
        matchId: 'mock-match-id-456',
        participants: ['123'],
        dataVersion: '1',
      },
      info: {
        gameDuration: 1800,
        gameCreation: 1620000000000,
        queueId: 420,
        participants: [mockMatchParticipantTeam1(), mockMatchParticipantTeam2()],
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

  function mockMatchParticipantTeam1() {
    return {
      participantId: 123,
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
      puuid: 'mock-puuid-123',
      teamPosition: '',
      win: true,
      matchId: 'mock-match-id-456',
      pseudo: 'pseudo',
      challenges: {
        kda: 9.0,
        gameLength: 2555,
      },
    };
  }

  function mockMatchParticipantTeam2() {
    return {
      participantId: 1234,
      profileIcon: 12345,
      assists: 6,
      kills: 9,
      deaths: 2,
      champLevel: 17,
      championId: 156,
      goldEarned: 13336,
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
      teamId: 200,
      totalMinionsKilled: 200,
      neutralMinionsKilled: 30,
      championName: 'MockChampion2',
      summonerName: 'MockSummoner2',
      lane: 'TOP',
      role: 'SOLO',
      puuid: 'mock-puuid-124',
      teamPosition: '',
      win: true,
      matchId: 'mock-match-id-456',
      pseudo: 'pseudo',
      challenges: {
        kda: 8.0,
        gameLength: 2555,
      },
    };
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GameDetailComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([]),
        HistoryService,
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: {
                get: (key: string) => {
                  if (key === 'matchId') return 'puuid_123';
                  return null;
                },
              },
            },
          },
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(GameDetailComponent);
    historyService = TestBed.inject(HistoryService);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it("should call getGameDetailHistoryByGameId on init and update the currentMatchSignal if it's not already defined", () => {
    // GIVEN
    const getGameDetailHistoryByGameIdSpy = spyOn(historyService, 'getGameDetailHistoryByGameId').and.returnValue(of(matchDTOMock()));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getGameDetailHistoryByGameIdSpy).toHaveBeenCalledWith(123);
    expect(component.currentMatchSignal()).toBeDefined();
    expect(component.currentMatchSignal()).toEqual(matchDTOMock());
  });

  it('should not call getGameDetailHistoryByGameId on init if currentMatchSignal is already defined', () => {
    // GIVEN
    const getGameDetailHistoryByGameIdSpy = spyOn(historyService, 'getGameDetailHistoryByGameId').and.returnValue(of(matchDTOMock()));
    component.currentMatchSignal.set(matchDTOMock());

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getGameDetailHistoryByGameIdSpy).not.toHaveBeenCalled();
  });

  it('should dispatch participants from team 1 and team 2 on init', () => {
    // GIVEN
    spyOn(historyService, 'getGameDetailHistoryByGameId').and.returnValue(of(matchDTOMock()));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(component.listParticipantsTeam1Signal()).toHaveSize(1);
    expect(component.listParticipantsTeam1Signal()).toEqual([mockMatchParticipantTeam1()]);

    expect(component.listParticipantsTeam2Signal()).toHaveSize(1);
    expect(component.listParticipantsTeam2Signal()).toEqual([mockMatchParticipantTeam2()]);
  });

  it('should compute total k/d/a by team on init', () => {
    // GIVEN
    spyOn(historyService, 'getGameDetailHistoryByGameId').and.returnValue(of(matchDTOMock()));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(component.totalKillsTeam1()).toEqual(10);
    expect(component.totalDeathsTeam1()).toEqual(2);
    expect(component.totalAssistsTeam1()).toEqual(8);
    expect(component.totalKillsTeam2()).toEqual(9);
    expect(component.totalDeathsTeam2()).toEqual(2);
    expect(component.totalAssistsTeam2()).toEqual(6);
    expect(getByDataTestAttr(fixture.debugElement, 'kda-team-1')?.innerText).toEqual('10/2/8');
    expect(getByDataTestAttr(fixture.debugElement, 'kda-team-2')?.innerText).toEqual('9/2/6');
  });

  it('should compute total gold earned by team on init', () => {
    // GIVEN
    spyOn(historyService, 'getGameDetailHistoryByGameId').and.returnValue(of(matchDTOMock()));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(component.totalGoldEarnedTeam1()).toEqual(15000);
    expect(component.totalGoldEarnedTeam2()).toEqual(13336);
    expect(getByDataTestAttr(fixture.debugElement, 'gold-earned-team-1')?.innerText).toEqual('15000');
    expect(getByDataTestAttr(fixture.debugElement, 'gold-earned-team-2')?.innerText).toEqual('13336');
  });
});
