import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';

import { HistoryItemsComponent } from './history-items.component';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { MatchDTO, ParticipantMatchDTO } from '../../common/models/games-history/matchDTO';
import { HistoryService } from '../../services/games-history/history.service';
import { GetVersionsService } from '../../services/versions/get-versions.service';
import { clickButtonByDataTestAttr, getByDataTestAttr } from '../../common/utils/utils-tests';
import { signal } from '@angular/core';
import { provideRouter, Router } from '@angular/router';

describe('HistoryItemsComponent', () => {
  let component: HistoryItemsComponent;
  let fixture: ComponentFixture<HistoryItemsComponent>;
  let historyService: HistoryService;
  let versionService: GetVersionsService;
  let routerService: Router;

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
      challenges: {
        kda: 9.0,
        gameLength: 2555,
      },
    };
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HistoryItemsComponent],
      providers: [provideHttpClient(), provideHttpClientTesting(), provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(HistoryItemsComponent);
    historyService = TestBed.inject(HistoryService);
    versionService = TestBed.inject(GetVersionsService);
    versionService.lastVersionlolDTOSignal.set('14.1');
    routerService = TestBed.inject(Router);
    fixture.componentRef.setInput('listMatchDataSignal', signal([matchDTOMock()]));
    component = fixture.componentInstance;
    component.currMatchParticipant = mockMatchParticipant() as ParticipantMatchDTO;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should correctly initialize all items url', () => {
    expect(component.listUrlItems).toHaveSize(7);
    expect(component.listUrlItems).toEqual([
      { url: 'https://ddragon.leagueoflegends.com/cdn/14.1/img/item/1055.png', itemId: 1055 },
      { url: 'https://ddragon.leagueoflegends.com/cdn/14.1/img/item/3006.png', itemId: 3006 },
      { url: 'https://ddragon.leagueoflegends.com/cdn/14.1/img/item/6672.png', itemId: 6672 },
      { url: 'https://ddragon.leagueoflegends.com/cdn/14.1/img/item/3046.png', itemId: 3046 },
      { url: 'https://ddragon.leagueoflegends.com/cdn/14.1/img/item/3031.png', itemId: 3031 },
      { url: 'https://ddragon.leagueoflegends.com/cdn/14.1/img/item/3072.png', itemId: 3072 },
      { url: 'https://ddragon.leagueoflegends.com/cdn/14.1/img/item/3363.png', itemId: 3363 },
    ]);
  });

  it('should correctly compute and display nb total cs killed', () => {
    expect(component.nbCsKilled).toEqual(230);
    expect(getByDataTestAttr(fixture.debugElement, 'nb-cs-kill')?.innerText).toEqual('230');
  });

  it('should correctly compute and display kda', () => {
    expect(component.currMatchParticipant.kills).toEqual(10);
    expect(component.currMatchParticipant.deaths).toEqual(2);
    expect(component.currMatchParticipant.assists).toEqual(8);
    expect(component.currMatchParticipant.challenges.kda).toEqual(9.0);
    expect(getByDataTestAttr(fixture.debugElement, 'kda')?.innerText).toEqual('10/2/8');
  });

  it('should correctly compute and display gold earned', () => {
    expect(component.currMatchParticipant.goldEarned).toEqual(15000);
    expect(getByDataTestAttr(fixture.debugElement, 'gold-earned')?.innerText).toEqual('15000');
  });

  it("should correctly compute and display champion's name selected", () => {
    expect(getByDataTestAttr(fixture.debugElement, 'champion-name-selected')?.innerText).toEqual('MockChampion');
  });

  it("should correctly compute and display champion's level", () => {
    expect(getByDataTestAttr(fixture.debugElement, 'champ-level')?.innerText).toEqual('18');
  });

  [
    { queueType: 'RANKED_SOLO_5x5', expectedView: 'Classée solo' },
    { queueType: 'RANKED_FLEX_SR', expectedView: 'Classée flexible' },
  ].forEach((queue) => {
    it(`should correctly display the queue type ${queue.queueType}`, () => {
      // GIVEN
      component.currentQueue = queue.queueType;

      // WHEN
      fixture.detectChanges();

      // THEN
      expect(getByDataTestAttr(fixture.debugElement, 'current-queue')?.innerText).toEqual(queue.expectedView);
    });
  });

  [
    { code: 'TOP', libelle: 'Top' },
    { code: 'JUNGLE', libelle: 'Jungle' },
    { code: 'MIDDLE', libelle: 'Mid' },
    { code: 'BOTTOM', libelle: 'ADC' },
    { code: 'UTILITY', libelle: 'Support' },
  ].forEach((position) => {
    it(`should correctly initialize and compute the role played with position: ${position.code}`, () => {
      // GIVEN
      component.currMatchParticipant.teamPosition = position.code;

      // WHEN
      fixture.detectChanges();

      // THEN
      expect(component.role).toEqual(position.libelle);
      expect(getByDataTestAttr(fixture.debugElement, 'role')?.innerText).toEqual(position.libelle);
    });
  });

  it('should correctly initialize and compute champion url', () => {
    expect(component.iconChampionUrl).toEqual('https://ddragon.leagueoflegends.com/cdn/14.1/img/champion/MockChampion.png');
  });

  it('should correctly initialize and compute icon summonerSpells url', () => {
    expect(component.summoner1IconUrl).toEqual('https://ddragon.leagueoflegends.com/cdn/14.1/img/spell/SummonerFlash.png');
    expect(component.summoner2IconUrl).toEqual('https://ddragon.leagueoflegends.com/cdn/14.1/img/spell/SummonerHeal.png');
  });

  it('should redirect user on click', () => {
    // GIVEN
    component.currMatchParticipant = mockMatchParticipant();
    fixture.detectChanges();

    const routerSpy = spyOn(routerService, 'navigate');

    // WHEN
    clickButtonByDataTestAttr(fixture.debugElement, 'game-item');
    fixture.detectChanges();

    // THEN
    expect(routerSpy).toHaveBeenCalledWith(['/game/detail/', 'mock-puuid-123', 'mock-match-id-456'], { state: { from: '/' } });
    expect(component.currentMatch).toEqual(
      jasmine.objectContaining({
        metadata: {
          matchId: 'mock-match-id-456',
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
      })
    );
  });
});
