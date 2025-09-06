import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavigationComponent } from './navigation-component';
import { Router } from '@angular/router';
import { clickButtonByDataTestAttr, getByDataTestAttr } from '../common/utils/utils-tests';
import { INVALID_SEARCH_SUMMONER_ERROR } from '../common/constants/errors';

describe('NavigationComponent', () => {
  let component: NavigationComponent;
  let fixture: ComponentFixture<NavigationComponent>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [NavigationComponent],
      providers: [{ provide: Router, useValue: routerSpy }],
    }).compileComponents();

    fixture = TestBed.createComponent(NavigationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to /Accueil when logo is clicked', () => {
    // GIVEN + WHEN
    clickButtonByDataTestAttr(fixture.debugElement, 'logo-league-infos');

    // THEN
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/Accueil']);
  });

  it('should navigate to /Detail/:summoner when user search a player', () => {
    // GIVEN
    const inputHtmlElement = getByDataTestAttr(fixture.debugElement, 'search-summoner-input') as HTMLInputElement;
    inputHtmlElement.value = 'summonerName#EUW';

    // WHEN
    clickButtonByDataTestAttr(fixture.debugElement, 'search-summoner-icone');
    fixture.detectChanges();

    // THEN
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/Detail-summoner', 'summonerName#EUW']);
    const errorMessageHtmlElement = getByDataTestAttr(fixture.debugElement, 'message-error-search-summoner');
    expect(component.INVALID_SEARCH_SUMMONER_ERROR).toEqual('');
    expect(errorMessageHtmlElement).toBeFalsy;
  });

  it('should not navigate when user search a player with invalid value and display an error message', () => {
    // GIVEN
    const inputHtmlElement = getByDataTestAttr(fixture.debugElement, 'search-summoner-input') as HTMLInputElement;
    inputHtmlElement.value = 'summonerName';

    // WHEN
    clickButtonByDataTestAttr(fixture.debugElement, 'search-summoner-icone');
    fixture.detectChanges();

    // THEN
    expect(routerSpy.navigate).not.toHaveBeenCalled();
    expect(component.INVALID_SEARCH_SUMMONER_ERROR).toEqual(INVALID_SEARCH_SUMMONER_ERROR);

    const errorMessageHtmlElement = getByDataTestAttr(fixture.debugElement, 'message-error-search-summoner');
    expect(errorMessageHtmlElement).toBeTruthy;
    expect(errorMessageHtmlElement?.textContent).toEqual(INVALID_SEARCH_SUMMONER_ERROR);
  });

  it('should not navigate when user search a player with empty value', () => {
    // GIVEN
    const inputHtmlElement = getByDataTestAttr(fixture.debugElement, 'search-summoner-input') as HTMLInputElement;
    inputHtmlElement.value = '';

    // WHEN
    clickButtonByDataTestAttr(fixture.debugElement, 'search-summoner-icone');

    // THEN
    expect(routerSpy.navigate).not.toHaveBeenCalled();
  });
});
