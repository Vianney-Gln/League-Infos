import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoryItemsComponent } from './history-items.component';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { MatchDTO, ParticipantMatchDTO } from '../../common/models/games-history/matchDTO';
import { HistoryService } from '../../services/games-history/history.service';
import { GetVersionsService } from '../../services/versions/get-versions.service';

describe('HistoryItemsComponent', () => {
  let component: HistoryItemsComponent;
  let fixture: ComponentFixture<HistoryItemsComponent>;
  let historyService: HistoryService;
  let versionService: GetVersionsService;

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
      providers: [provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(HistoryItemsComponent);
    historyService = TestBed.inject(HistoryService);
    versionService = TestBed.inject(GetVersionsService);
    versionService.lastVersionlolDTOSignal.set('14.1');
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

  it('should correctly initialize and compute nb total cs killed', () => {
    expect(component.nbCsKilled).toEqual(230);
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

      // THEN
      expect(component.role).toEqual(position.libelle);
    });
  });

  it('should correctly initialize and compute champion url', () => {
    expect(component.iconChampionUrl).toEqual('https://ddragon.leagueoflegends.com/cdn/14.1/img/champion/MockChampion.png');
  });

  it('should correctly initialize and compute icon summonerSpells url', () => {
    expect(component.summoner1IconUrl).toEqual('https://ddragon.leagueoflegends.com/cdn/14.1/img/spell/SummonerFlash.png');
    expect(component.summoner2IconUrl).toEqual('https://ddragon.leagueoflegends.com/cdn/14.1/img/spell/SummonerHeal.png');
  });
});
