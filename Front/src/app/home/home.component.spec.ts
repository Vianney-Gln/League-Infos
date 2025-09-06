import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HomeComponent } from './home.component';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { GetChampionsService } from '../services/champions/get-champions.service';
import { clickButtonByDataTestAttr, getAllByDataTestAttr, getByDataTestAttr } from '../common/utils/utils-tests';
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
        tags: ['Fighter'],
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
        tags: ['Mage'],
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
      Ashe: {
        version: '14.10.1',
        id: 'Ashe',
        key: '3',
        name: 'Ashe',
        title: 'the Frost Archer',
        blurb: 'Iceborn warmother of the Avarosan tribe...',
        info: { attack: 7, defense: 3, magic: 2, difficulty: 4 },
        image: { full: 'Ashe.png', sprite: 'champion0.png', group: 'champion', x: 96, y: 0, w: 48, h: 48 },
        tags: ['Marksman'],
        partype: 'Mana',
        stats: {
          hp: 640,
          hpperlevel: 101,
          mp: 280,
          mpperlevel: 32,
          movespeed: 325,
          armor: 26,
          armorperlevel: 4.6,
          spellblock: 30,
          spellblockperlevel: 1.3,
          attackrange: 600,
          hpregen: 3.5,
          hpregenperlevel: 0.55,
          mpregen: 7,
          mpregenperlevel: 0.7,
          crit: 0,
          critperlevel: 0,
          attackdamage: 61,
          attackdamageperlevel: 2.96,
          attackspeedperlevel: 3.33,
          attackspeed: 0.658,
        },
      },
      Zed: {
        version: '14.10.1',
        id: 'Zed',
        key: '4',
        name: 'Zed',
        title: 'the Master of Shadows',
        blurb: 'Utterly ruthless and without mercy...',
        info: { attack: 9, defense: 2, magic: 1, difficulty: 7 },
        image: { full: 'Zed.png', sprite: 'champion1.png', group: 'champion', x: 0, y: 0, w: 48, h: 48 },
        tags: ['Assassin'],
        partype: 'Energy',
        stats: {
          hp: 654,
          hpperlevel: 99,
          mp: 200,
          mpperlevel: 0,
          movespeed: 345,
          armor: 32,
          armorperlevel: 4.7,
          spellblock: 29,
          spellblockperlevel: 2.05,
          attackrange: 125,
          hpregen: 7,
          hpregenperlevel: 0.65,
          mpregen: 50,
          mpregenperlevel: 0,
          crit: 0,
          critperlevel: 0,
          attackdamage: 63,
          attackdamageperlevel: 3.4,
          attackspeedperlevel: 2.1,
          attackspeed: 0.651,
        },
      },
      Janna: {
        version: '14.10.1',
        id: 'Janna',
        key: '5',
        name: 'Janna',
        title: "the Storm's Fury",
        blurb: "Armed with the power of Runeterra's winds...",
        info: { attack: 3, defense: 5, magic: 7, difficulty: 7 },
        image: { full: 'Janna.png', sprite: 'champion1.png', group: 'champion', x: 48, y: 0, w: 48, h: 48 },
        tags: ['Support'],
        partype: 'Mana',
        stats: {
          hp: 570,
          hpperlevel: 74,
          mp: 350,
          mpperlevel: 64,
          movespeed: 330,
          armor: 28,
          armorperlevel: 5,
          spellblock: 30,
          spellblockperlevel: 1.3,
          attackrange: 550,
          hpregen: 5.5,
          hpregenperlevel: 0.5,
          mpregen: 11.5,
          mpregenperlevel: 0.4,
          crit: 0,
          critperlevel: 0,
          attackdamage: 46,
          attackdamageperlevel: 1.5,
          attackspeedperlevel: 2.95,
          attackspeed: 0.625,
        },
      },
      Malphite: {
        version: '14.10.1',
        id: 'Malphite',
        key: '6',
        name: 'Malphite',
        title: 'Shard of the Monolith',
        blurb: 'A massive creature of living stone...',
        info: { attack: 5, defense: 9, magic: 7, difficulty: 2 },
        image: { full: 'Malphite.png', sprite: 'champion1.png', group: 'champion', x: 96, y: 0, w: 48, h: 48 },
        tags: ['Tank'],
        partype: 'Mana',
        stats: {
          hp: 644,
          hpperlevel: 104,
          mp: 280,
          mpperlevel: 60,
          movespeed: 335,
          armor: 37,
          armorperlevel: 4.95,
          spellblock: 28,
          spellblockperlevel: 1.3,
          attackrange: 125,
          hpregen: 7,
          hpregenperlevel: 0.55,
          mpregen: 7.3,
          mpregenperlevel: 0.55,
          crit: 0,
          critperlevel: 0,
          attackdamage: 62,
          attackdamageperlevel: 4,
          attackspeedperlevel: 3.4,
          attackspeed: 0.736,
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

  it('Should display an error message in the template if isFreeChampErrorSignal is true', () => {
    // GIVEN
    getChampionService.isFreeChampErrorSignal.set(true);

    // WHEN
    fixture.detectChanges();

    // THEN
    const errorMessageHtmlElement = getByDataTestAttr(fixture.debugElement, 'error-message-free-champs');
    expect(errorMessageHtmlElement).toBeTruthy();
    expect(errorMessageHtmlElement?.textContent).toContain("Une erreur s'est produite pendant le chargement des champions");
  });

  it('Should display a select input to sort champions by type', () => {
    // GIVEN
    const selectInputChamps = getByDataTestAttr(fixture.debugElement, 'select-champ-by-type');
    expect(selectInputChamps).toBeTruthy();

    const optionTexts = ['Tous', 'Assassin', 'Combattant', 'Mage', 'Tireur', 'Support', 'Tank', 'Gratuits', 'Gratuits jusque lv10'];

    // WHEN
    selectInputChamps?.click();
    fixture.detectChanges();

    // THEN
    const options = getAllByDataTestAttr(fixture.debugElement, 'type-champion-option');
    expect(options).toHaveSize(9);
    options?.forEach((option, index) => {
      expect(option.textContent!.trim()).toEqual(optionTexts[index]);
    });
  });

  [
    { libelle: 'Tous', nbChamp: 6 },
    { libelle: 'Assassin', nbChamp: 1 },
    { libelle: 'Combattant', nbChamp: 1 },
    { libelle: 'Mage', nbChamp: 1 },
    { libelle: 'Tireur', nbChamp: 1 },
    { libelle: 'Support', nbChamp: 1 },
    { libelle: 'Tank', nbChamp: 1 },
  ].forEach((select) => {
    it('Should sort champions by type', () => {
      // GIVEN
      const selectInputChamps = getByDataTestAttr(fixture.debugElement, 'select-champ-by-type') as HTMLSelectElement;
      expect(selectInputChamps).toBeTruthy();
      getChampionService.championDataSignal.set(championDataMock);
      selectInputChamps?.click();
      fixture.detectChanges();

      // WHEN
      const matchingOption = [...selectInputChamps.options].find((o) => o.textContent!.trim() === select.libelle);
      selectInputChamps.value = matchingOption!.value;
      selectInputChamps.dispatchEvent(new Event('change'));

      // THEN
      expect(component.listChampions()).toHaveSize(select.nbChamp);
      if (select.libelle === 'Tous') {
        expect(component.listChampions()).toEqual([
          {
            version: '14.10.1',
            id: 'Aatrox',
            key: '1',
            name: 'Aatrox',
            title: 'the Darkin Blade',
            blurb: 'Once honored defenders...',
            info: { attack: 8, defense: 4, magic: 3, difficulty: 4 },
            image: { full: 'Aatrox.png', sprite: 'champion0.png', group: 'champion', x: 0, y: 0, w: 48, h: 48 },
            tags: ['Fighter'],
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
          {
            version: '14.10.1',
            id: 'Ahri',
            key: '2',
            name: 'Ahri',
            title: 'the Nine-Tailed Fox',
            blurb: 'Innately connected to the magic...',
            info: { attack: 3, defense: 4, magic: 8, difficulty: 5 },
            image: { full: 'Ahri.png', sprite: 'champion0.png', group: 'champion', x: 48, y: 0, w: 48, h: 48 },
            tags: ['Mage'],
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
          {
            version: '14.10.1',
            id: 'Ashe',
            key: '3',
            name: 'Ashe',
            title: 'the Frost Archer',
            blurb: 'Iceborn warmother of the Avarosan tribe...',
            info: { attack: 7, defense: 3, magic: 2, difficulty: 4 },
            image: { full: 'Ashe.png', sprite: 'champion0.png', group: 'champion', x: 96, y: 0, w: 48, h: 48 },
            tags: ['Marksman'],
            partype: 'Mana',
            stats: {
              hp: 640,
              hpperlevel: 101,
              mp: 280,
              mpperlevel: 32,
              movespeed: 325,
              armor: 26,
              armorperlevel: 4.6,
              spellblock: 30,
              spellblockperlevel: 1.3,
              attackrange: 600,
              hpregen: 3.5,
              hpregenperlevel: 0.55,
              mpregen: 7,
              mpregenperlevel: 0.7,
              crit: 0,
              critperlevel: 0,
              attackdamage: 61,
              attackdamageperlevel: 2.96,
              attackspeedperlevel: 3.33,
              attackspeed: 0.658,
            },
          },
          {
            version: '14.10.1',
            id: 'Zed',
            key: '4',
            name: 'Zed',
            title: 'the Master of Shadows',
            blurb: 'Utterly ruthless and without mercy...',
            info: { attack: 9, defense: 2, magic: 1, difficulty: 7 },
            image: { full: 'Zed.png', sprite: 'champion1.png', group: 'champion', x: 0, y: 0, w: 48, h: 48 },
            tags: ['Assassin'],
            partype: 'Energy',
            stats: {
              hp: 654,
              hpperlevel: 99,
              mp: 200,
              mpperlevel: 0,
              movespeed: 345,
              armor: 32,
              armorperlevel: 4.7,
              spellblock: 29,
              spellblockperlevel: 2.05,
              attackrange: 125,
              hpregen: 7,
              hpregenperlevel: 0.65,
              mpregen: 50,
              mpregenperlevel: 0,
              crit: 0,
              critperlevel: 0,
              attackdamage: 63,
              attackdamageperlevel: 3.4,
              attackspeedperlevel: 2.1,
              attackspeed: 0.651,
            },
          },
          {
            version: '14.10.1',
            id: 'Janna',
            key: '5',
            name: 'Janna',
            title: "the Storm's Fury",
            blurb: "Armed with the power of Runeterra's winds...",
            info: { attack: 3, defense: 5, magic: 7, difficulty: 7 },
            image: { full: 'Janna.png', sprite: 'champion1.png', group: 'champion', x: 48, y: 0, w: 48, h: 48 },
            tags: ['Support'],
            partype: 'Mana',
            stats: {
              hp: 570,
              hpperlevel: 74,
              mp: 350,
              mpperlevel: 64,
              movespeed: 330,
              armor: 28,
              armorperlevel: 5,
              spellblock: 30,
              spellblockperlevel: 1.3,
              attackrange: 550,
              hpregen: 5.5,
              hpregenperlevel: 0.5,
              mpregen: 11.5,
              mpregenperlevel: 0.4,
              crit: 0,
              critperlevel: 0,
              attackdamage: 46,
              attackdamageperlevel: 1.5,
              attackspeedperlevel: 2.95,
              attackspeed: 0.625,
            },
          },
          {
            version: '14.10.1',
            id: 'Malphite',
            key: '6',
            name: 'Malphite',
            title: 'Shard of the Monolith',
            blurb: 'A massive creature of living stone...',
            info: { attack: 5, defense: 9, magic: 7, difficulty: 2 },
            image: { full: 'Malphite.png', sprite: 'champion1.png', group: 'champion', x: 96, y: 0, w: 48, h: 48 },
            tags: ['Tank'],
            partype: 'Mana',
            stats: {
              hp: 644,
              hpperlevel: 104,
              mp: 280,
              mpperlevel: 60,
              movespeed: 335,
              armor: 37,
              armorperlevel: 4.95,
              spellblock: 28,
              spellblockperlevel: 1.3,
              attackrange: 125,
              hpregen: 7,
              hpregenperlevel: 0.55,
              mpregen: 7.3,
              mpregenperlevel: 0.55,
              crit: 0,
              critperlevel: 0,
              attackdamage: 62,
              attackdamageperlevel: 4,
              attackspeedperlevel: 3.4,
              attackspeed: 0.736,
            },
          },
        ]);
      }

      if (select.libelle === 'Assassin') {
        expect(component.listChampions()).toEqual([
          {
            version: '14.10.1',
            id: 'Zed',
            key: '4',
            name: 'Zed',
            title: 'the Master of Shadows',
            blurb: 'Utterly ruthless and without mercy...',
            info: { attack: 9, defense: 2, magic: 1, difficulty: 7 },
            image: { full: 'Zed.png', sprite: 'champion1.png', group: 'champion', x: 0, y: 0, w: 48, h: 48 },
            tags: ['Assassin'],
            partype: 'Energy',
            stats: {
              hp: 654,
              hpperlevel: 99,
              mp: 200,
              mpperlevel: 0,
              movespeed: 345,
              armor: 32,
              armorperlevel: 4.7,
              spellblock: 29,
              spellblockperlevel: 2.05,
              attackrange: 125,
              hpregen: 7,
              hpregenperlevel: 0.65,
              mpregen: 50,
              mpregenperlevel: 0,
              crit: 0,
              critperlevel: 0,
              attackdamage: 63,
              attackdamageperlevel: 3.4,
              attackspeedperlevel: 2.1,
              attackspeed: 0.651,
            },
          },
        ]);
      }
      if (select.libelle === 'Combattant') {
        expect(component.listChampions()).toEqual([
          {
            version: '14.10.1',
            id: 'Aatrox',
            key: '1',
            name: 'Aatrox',
            title: 'the Darkin Blade',
            blurb: 'Once honored defenders...',
            info: { attack: 8, defense: 4, magic: 3, difficulty: 4 },
            image: { full: 'Aatrox.png', sprite: 'champion0.png', group: 'champion', x: 0, y: 0, w: 48, h: 48 },
            tags: ['Fighter'],
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
        ]);
      }

      if (select.libelle === 'Mage') {
        expect(component.listChampions()).toEqual([
          {
            version: '14.10.1',
            id: 'Ahri',
            key: '2',
            name: 'Ahri',
            title: 'the Nine-Tailed Fox',
            blurb: 'Innately connected to the magic...',
            info: { attack: 3, defense: 4, magic: 8, difficulty: 5 },
            image: { full: 'Ahri.png', sprite: 'champion0.png', group: 'champion', x: 48, y: 0, w: 48, h: 48 },
            tags: ['Mage'],
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
        ]);
      }

      if (select.libelle === 'Tireur') {
        expect(component.listChampions()).toEqual([
          {
            version: '14.10.1',
            id: 'Ashe',
            key: '3',
            name: 'Ashe',
            title: 'the Frost Archer',
            blurb: 'Iceborn warmother of the Avarosan tribe...',
            info: { attack: 7, defense: 3, magic: 2, difficulty: 4 },
            image: { full: 'Ashe.png', sprite: 'champion0.png', group: 'champion', x: 96, y: 0, w: 48, h: 48 },
            tags: ['Marksman'],
            partype: 'Mana',
            stats: {
              hp: 640,
              hpperlevel: 101,
              mp: 280,
              mpperlevel: 32,
              movespeed: 325,
              armor: 26,
              armorperlevel: 4.6,
              spellblock: 30,
              spellblockperlevel: 1.3,
              attackrange: 600,
              hpregen: 3.5,
              hpregenperlevel: 0.55,
              mpregen: 7,
              mpregenperlevel: 0.7,
              crit: 0,
              critperlevel: 0,
              attackdamage: 61,
              attackdamageperlevel: 2.96,
              attackspeedperlevel: 3.33,
              attackspeed: 0.658,
            },
          },
        ]);
      }
      if (select.libelle === 'Support') {
        expect(component.listChampions()).toEqual([
          {
            version: '14.10.1',
            id: 'Janna',
            key: '5',
            name: 'Janna',
            title: "the Storm's Fury",
            blurb: "Armed with the power of Runeterra's winds...",
            info: { attack: 3, defense: 5, magic: 7, difficulty: 7 },
            image: { full: 'Janna.png', sprite: 'champion1.png', group: 'champion', x: 48, y: 0, w: 48, h: 48 },
            tags: ['Support'],
            partype: 'Mana',
            stats: {
              hp: 570,
              hpperlevel: 74,
              mp: 350,
              mpperlevel: 64,
              movespeed: 330,
              armor: 28,
              armorperlevel: 5,
              spellblock: 30,
              spellblockperlevel: 1.3,
              attackrange: 550,
              hpregen: 5.5,
              hpregenperlevel: 0.5,
              mpregen: 11.5,
              mpregenperlevel: 0.4,
              crit: 0,
              critperlevel: 0,
              attackdamage: 46,
              attackdamageperlevel: 1.5,
              attackspeedperlevel: 2.95,
              attackspeed: 0.625,
            },
          },
        ]);
      }
      if (select.libelle === 'Tank') {
        expect(component.listChampions()).toEqual([
          {
            version: '14.10.1',
            id: 'Malphite',
            key: '6',
            name: 'Malphite',
            title: 'Shard of the Monolith',
            blurb: 'A massive creature of living stone...',
            info: { attack: 5, defense: 9, magic: 7, difficulty: 2 },
            image: { full: 'Malphite.png', sprite: 'champion1.png', group: 'champion', x: 96, y: 0, w: 48, h: 48 },
            tags: ['Tank'],
            partype: 'Mana',
            stats: {
              hp: 644,
              hpperlevel: 104,
              mp: 280,
              mpperlevel: 60,
              movespeed: 335,
              armor: 37,
              armorperlevel: 4.95,
              spellblock: 28,
              spellblockperlevel: 1.3,
              attackrange: 125,
              hpregen: 7,
              hpregenperlevel: 0.55,
              mpregen: 7.3,
              mpregenperlevel: 0.55,
              crit: 0,
              critperlevel: 0,
              attackdamage: 62,
              attackdamageperlevel: 4,
              attackspeedperlevel: 3.4,
              attackspeed: 0.736,
            },
          },
        ]);
      }
    });
  });

  ['Gratuits', 'Gratuits jusque lv10'].forEach((type) => {
    it('Should sort champions by type case free', () => {
      // GIVEN
      const selectInputChamps = getByDataTestAttr(fixture.debugElement, 'select-champ-by-type') as HTMLSelectElement;
      expect(selectInputChamps).toBeTruthy();

      getChampionService.championDataSignal.set(championDataMock);
      selectInputChamps?.click();
      spyOn(getChampionService, 'getFreeChampions').and.returnValue(of(mockFreeChampions));
      fixture.detectChanges();

      // WHEN
      const matchingOption = [...selectInputChamps.options].find((o) => o.textContent!.trim() === type);
      selectInputChamps.value = matchingOption!.value;
      selectInputChamps.dispatchEvent(new Event('change'));

      // THEN
      expect(component.listChampions()).toHaveSize(1);
      if (type === 'Gratuits') {
        expect(component.listChampions()).toEqual([
          {
            version: '14.10.1',
            id: 'Aatrox',
            key: '1',
            name: 'Aatrox',
            title: 'the Darkin Blade',
            blurb: 'Once honored defenders...',
            info: { attack: 8, defense: 4, magic: 3, difficulty: 4 },
            image: { full: 'Aatrox.png', sprite: 'champion0.png', group: 'champion', x: 0, y: 0, w: 48, h: 48 },
            tags: ['Fighter'],
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
        ]);
      }
      if (type === 'Gratuits jusque lv10') {
        expect(component.listChampions()).toEqual([
          {
            version: '14.10.1',
            id: 'Ahri',
            key: '2',
            name: 'Ahri',
            title: 'the Nine-Tailed Fox',
            blurb: 'Innately connected to the magic...',
            info: { attack: 3, defense: 4, magic: 8, difficulty: 5 },
            image: { full: 'Ahri.png', sprite: 'champion0.png', group: 'champion', x: 48, y: 0, w: 48, h: 48 },
            tags: ['Mage'],
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
        ]);
      }
    });
  });

  it('free chapions should be selected by default', () => {
    // GIVEN
    getChampionService.championDataSignal.set(championDataMock);
    spyOn(getChampionService, 'getFreeChampions').and.returnValue(of(mockFreeChampions));

    fixture.detectChanges();
    // THEN
    const selectInputChamps = getByDataTestAttr(fixture.debugElement, 'select-champ-by-type') as HTMLSelectElement;
    const options = selectInputChamps.options;
    const optionSelected = options[options.selectedIndex];
    expect(optionSelected.textContent!.trim()).toEqual('Gratuits');
    expect(component.listChampions()).toHaveSize(1);
    expect(component.listChampions()).toEqual([
      {
        version: '14.10.1',
        id: 'Aatrox',
        key: '1',
        name: 'Aatrox',
        title: 'the Darkin Blade',
        blurb: 'Once honored defenders...',
        info: { attack: 8, defense: 4, magic: 3, difficulty: 4 },
        image: { full: 'Aatrox.png', sprite: 'champion0.png', group: 'champion', x: 0, y: 0, w: 48, h: 48 },
        tags: ['Fighter'],
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
    ]);
  });
});
