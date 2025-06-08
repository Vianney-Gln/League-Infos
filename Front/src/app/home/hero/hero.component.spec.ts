import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeroComponent } from './hero.component';
import { PlayersService } from '../../services/players/players.service';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { of } from 'rxjs';
import { RANKED_SOLO_5x5 } from '../../common/constants/queues';
import { LeagueListDTO } from '../../common/models/leagueListDTO';
import { AccountDTO } from '../../common/models/accountDTO';
import { SummonerDTO } from '../../common/models/summonerDTO';

describe('HeroComponentComponent', () => {
  let component: HeroComponent;
  let fixture: ComponentFixture<HeroComponent>;
  let playerservice: PlayersService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeroComponent],
      providers: [PlayersService, provideHttpClient(withInterceptorsFromDi())],
    }).compileComponents();

    playerservice = TestBed.inject(PlayersService);
    fixture = TestBed.createComponent(HeroComponent);
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
    queue: RANKED_SOLO_5x5,
    name: 'Challenger League',
  } as LeagueListDTO;

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call getLeagueChallengerDataSoloQ', () => {
    // GIVEN
    const getLeagueChallengerDataSoloQSpy = spyOn(playerservice, 'getLeagueChallengerDataSoloQ').and.returnValue(of(mockLeagueListDTO));
    const leagueChallengerListDTOSignalSpy = spyOn(playerservice.leagueChallengerListDTOSignal, 'set').and.callThrough();

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getLeagueChallengerDataSoloQSpy).toHaveBeenCalled();
    expect(leagueChallengerListDTOSignalSpy).toHaveBeenCalledWith(mockLeagueListDTO);
    expect(component.firstChallengerPlayerSoloQSignal()).toBe(mockLeagueListDTO.entries[0]);
    expect(component.leagueBestPlayer).toEqual('CHALLENGER');
  });

  it('should call getAccountByPuuid and get the gameName of the best player Challenger', () => {
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

  it('should call getSummonerByPuuid and get the summonerDTO of the best player Challenger', () => {
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
    const getSummonerSpy = spyOn(playerservice, 'getSummonerByPuuid').and.returnValue(of(summonerDTOMock));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getSummonerSpy).toHaveBeenCalled();
    expect(component.firstChallengerSummonerSoloQSignal()).toBe(summonerDTOMock);
  });
});
