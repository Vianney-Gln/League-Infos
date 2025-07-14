import { ComponentFixture, TestBed } from '@angular/core/testing';
import { App } from './app';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { GetChampionsService } from './services/champions/get-champions.service';
import { of, throwError } from 'rxjs';
import { GetVersionsService } from './services/versions/get-versions.service';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('App', () => {
  let fixture: ComponentFixture<App>;
  let component: App;
  let getChampionService: GetChampionsService;
  let getVersionsService: GetVersionsService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [App],
      providers: [GetChampionsService, GetVersionsService, provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();
    getChampionService = TestBed.inject(GetChampionsService);
    getVersionsService = TestBed.inject(GetVersionsService);
    fixture = TestBed.createComponent(App);
    component = fixture.componentInstance;
  });

  it('should create the app', () => {
    expect(component).toBeTruthy();
  });

  it('should render tab title', () => {
    expect(component.title).toEqual('league-infos');
  });

  it('should call getAllVersionsLol , update lastVersionlolDTOSignal and call getAllChampionInfos with the last version', () => {
    // GIVEN
    const ChampionDataMock = {
      type: 'tank',
      format: 'test',
      version: '4',
      data: {
        Garen: {
          version: '4',
          id: 'Garen',
          key: '86',
          name: 'Garen',
          title: 'The Might of Demacia',
          blurb: 'A proud and noble warrior...',
          info: { attack: 7, defense: 7, magic: 1, difficulty: 5 },
          image: { full: 'Garen.png', sprite: 'champion0.png', group: 'champion', x: 0, y: 0, w: 48, h: 48 },
          tags: ['Fighter', 'Tank'],
          partype: 'None',
          stats: {
            hp: 620,
            hpperlevel: 84,
            mp: 0,
            mpperlevel: 0,
            movespeed: 340,
            armor: 36,
            armorperlevel: 3,
            spellblock: 32,
            spellblockperlevel: 2.05,
            attackrange: 175,
            hpregen: 8,
            hpregenperlevel: 0.5,
            mpregen: 0,
            mpregenperlevel: 0,
            crit: 0,
            critperlevel: 0,
            attackdamage: 66,
            attackdamageperlevel: 4.5,
            attackspeedperlevel: 2.9,
            attackspeed: 0.625,
          },
        },
      },
    };

    const getChampionServiceSpy = spyOn(getChampionService, 'getAllChampionsInfos').and.returnValue(of(ChampionDataMock));
    const getVersionsServiceSpy = spyOn(getVersionsService, 'getAllVersionsLol').and.returnValue(
      of([
        '14.10.2',
        '13.10.2',
        '13.9.2',
        '13.9.1',
        '13.9.0',
        '13.8.6',
        '13.8.5',
        '13.8.4',
        '13.8.3',
        '13.8.2',
        '13.8.1',
        '13.8.0',
        '13.7.11',
        '13.7.10',
        '13.7.9',
        '13.7.8',
        '13.7.7',
        '13.7.6',
        '13.7.5',
        '13.7.4',
        '13.7.3',
      ])
    );
    const championDataSignalSpy = spyOn(getChampionService.championDataSignal, 'set').and.stub();
    const lastVersionlolDTOSignalSpy = spyOn(getVersionsService.lastVersionlolDTOSignal, 'set').and.stub();
    const lastTenVersionlolSignalSpy = spyOn(getVersionsService.lastTwentyVersionslolSignal, 'set').and.stub();

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getVersionsServiceSpy).toHaveBeenCalled();
    expect(getChampionServiceSpy).toHaveBeenCalledWith('14.10.2');
    expect(championDataSignalSpy).toHaveBeenCalledWith(ChampionDataMock);
    expect(lastVersionlolDTOSignalSpy).toHaveBeenCalledWith('14.10.2');
    expect(lastTenVersionlolSignalSpy).toHaveBeenCalledWith([
      '14.10.2',
      '13.10.2',
      '13.9.2',
      '13.9.1',
      '13.9.0',
      '13.8.6',
      '13.8.5',
      '13.8.4',
      '13.8.3',
      '13.8.2',
      '13.8.1',
      '13.8.0',
      '13.7.11',
      '13.7.10',
      '13.7.9',
      '13.7.8',
      '13.7.7',
      '13.7.6',
      '13.7.5',
      '13.7.4',
    ]);
  });

  it('Should call set method from isFreeChampErrorSignal with true as value if back end throw an error', () => {
    // GIVEN
    spyOn(getChampionService, 'getAllChampionsInfos').and.returnValue(throwError(() => new Error()));
    spyOn(getVersionsService, 'getAllVersionsLol').and.returnValue(of(['14.10.2', '13.10.2']));
    spyOn(getChampionService.championDataSignal, 'set').and.stub();
    const isFreeChampErrorSignalSpy = spyOn(getChampionService.isFreeChampErrorSignal, 'set').and.stub();

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(isFreeChampErrorSignalSpy).toHaveBeenCalledWith(true);
  });
});
