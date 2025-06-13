import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomeComponent } from './home.component';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { GetChampionsService } from '../services/champions/get-champions.service';
import { clickButtonByDataTestAttr, getByDataTestAttr } from '../common/utils/utils-tests';
import { of, throwError } from 'rxjs';
import { FreeChampionsDTO } from '../common/models/freeChampionsDTO';
import { GetVersionsService } from '../services/versions/get-versions.service';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('Home', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let getChampionService: GetChampionsService;
  let getVersionsService: GetVersionsService;

  const mockFreeChampions: FreeChampionsDTO = {
    freeChampionIds: [1],
    maxNewPlayerLevel: 10,
    freeChampionIdsForNewPlayers: [2],
  };

  const championDataMock = {
    type: 'champion',
    format: 'standAloneComplex',
    version: '14.10.1',
    data: {
      Aatrox: {
        version: '14.10.1',
        id: 'Aatrox',
        key: '1',
        name: 'Aatrox',
        title: 'the Darkin Blade',
        blurb: 'Once honored defenders...',
        info: { attack: 8, defense: 4, magic: 3, difficulty: 4 },
        image: { full: 'Aatrox.png', sprite: 'champion0.png', group: 'champion', x: 0, y: 0, w: 48, h: 48 },
        tags: ['Fighter', 'Tank'],
        partype: 'Blood Well',
        stats: {
          hp: 650,
          hpperlevel: 114,
          mp: 0,
          mpperlevel: 0,
          movespeed: 345,
          armor: 38,
          armorperlevel: 4.45,
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
        version: '14.10.1',
        id: 'Ahri',
        key: '2',
        name: 'Ahri',
        title: 'the Nine-Tailed Fox',
        blurb: 'Innately connected to the magic...',
        info: { attack: 3, defense: 4, magic: 8, difficulty: 5 },
        image: { full: 'Ahri.png', sprite: 'champion0.png', group: 'champion', x: 48, y: 0, w: 48, h: 48 },
        tags: ['Mage', 'Assassin'],
        partype: 'Mana',
        stats: {
          hp: 590,
          hpperlevel: 96,
          mp: 418,
          mpperlevel: 25,
          movespeed: 330,
          armor: 21,
          armorperlevel: 5,
          spellblock: 30,
          spellblockperlevel: 1.3,
          attackrange: 550,
          hpregen: 2.5,
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
    },
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [],
      declarations: [],
      providers: [GetChampionsService, GetVersionsService, provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    getChampionService = TestBed.inject(GetChampionsService);
    getVersionsService = TestBed.inject(GetVersionsService);
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  it('should get the lastVersionLolSignal up to date', () => {
    // GIVEN
    spyOn(getVersionsService, 'lastVersionlolDTOSignal').and.returnValue('15.10.1');

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(component.lastVersionLolSignal()).toEqual('15.10.1');
  });

  it('should call getChampionService', () => {
    // GIVEN
    const getFreeChampSpy = spyOn(getChampionService, 'getFreeChampions').and.returnValue(of(mockFreeChampions));

    getChampionService.championDataSignal.set(championDataMock);

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getFreeChampSpy).toHaveBeenCalled();
    expect(component.freeChampionsDataSignal()).not.toBeNull;
    expect(component.freeChampionsForBeginnersDataSignal()).not.toBeNull;
    expect(component.freeChampionsForBeginnersDataSignal()).toEqual([championDataMock.data.Ahri]);
    expect(component.freeChampionsDataSignal()).toEqual([championDataMock.data.Aatrox]);
  });

  it('Should call set method from isFreeChampErrorSignal with true as value if back end throw an error', () => {
    // GIVEN
    spyOn(getChampionService, 'getFreeChampions').and.returnValue(throwError(() => new Error()));
    const isFreeChampErrorSignalSpy = spyOn(getChampionService.isFreeChampErrorSignal, 'set').and.stub();

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(isFreeChampErrorSignalSpy).toHaveBeenCalledWith(true);
  });

  ['button-accordeon-free-champs', 'button-accordeon-free-champs-beginners'].forEach((accordeonButton) => {
    it('Should display an error message in the template if isFreeChampErrorSignal is true for the both free champs sections', () => {
      // GIVEN
      component.getChampionService.isFreeChampErrorSignal.set(true);

      // WHEN
      fixture.detectChanges();
      clickButtonByDataTestAttr(fixture.debugElement, accordeonButton);

      // THEN
      const errorMessageHtmlElement = getByDataTestAttr(fixture.debugElement, 'error-message-free-champs');
      expect(errorMessageHtmlElement).toBeTruthy();
      expect(errorMessageHtmlElement?.textContent).toContain("Une erreur s'est produite pendant le chargement des champions gratuits");
    });
  });
});
