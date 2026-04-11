import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentaryComponent } from './commentary.component';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CommentaryService } from '../../services/commentary/commentary.service';
import { CommentaryDTO } from '../../common/models/CommentaryDTO';
import { of, throwError } from 'rxjs';
import { CommentaryModalData } from '../../common/models/modal/CommentaryModalData';
import { getByDataTestAttr } from '../../common/utils/utils-tests';

describe('CommentaryComponent', () => {
  let component: CommentaryComponent;
  let fixture: ComponentFixture<CommentaryComponent>;
  let commentaryService: CommentaryService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommentaryComponent],
      providers: [provideHttpClient(), provideHttpClientTesting(), { provide: MAT_DIALOG_DATA, useValue: {} }],
    }).compileComponents();

    fixture = TestBed.createComponent(CommentaryComponent);
    commentaryService = TestBed.inject(CommentaryService);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call getCommentary and display it', () => {
    // GIVEN
    const commentaryDTO = new CommentaryDTO();
    commentaryDTO.content = 'content';
    commentaryDTO.created = 123456;
    commentaryDTO.id = '10';
    commentaryDTO.model = 'model';
    commentaryDTO.object = 'object';

    const dataModal = new CommentaryModalData();
    dataModal.gameId = 10;
    dataModal.matchId = 'LOL_10';
    dataModal.pseudo = 'ninja';
    dataModal.puuid = 'puuid';
    component.data = dataModal;
    const getCommentarySpy = spyOn(commentaryService, 'getCommentary').and.returnValue(of(commentaryDTO));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getCommentarySpy).toHaveBeenCalledWith('LOL_10', 10, 'puuid', 'ninja');
    expect(component.commentaryDTOSignal()).toEqual(commentaryDTO);
    expect(component.isLoading()).toBeFalse();

    const commentaryFromTemplate = getByDataTestAttr(fixture.debugElement, 'commentary');
    expect(commentaryFromTemplate).toBeTruthy();
    expect(commentaryFromTemplate?.textContent).toEqual('content');

    const spinnerFromTemplate = getByDataTestAttr(fixture.debugElement, 'spinner');
    expect(spinnerFromTemplate).toBeFalsy();
  });

  it('should display a spinner while is loading is true', () => {
    // GIVEN
    const dataModal = new CommentaryModalData();
    dataModal.gameId = 10;
    dataModal.matchId = 'LOL_10';
    dataModal.pseudo = 'ninja';
    dataModal.puuid = 'puuid';
    component.data = dataModal;
    spyOn(commentaryService, 'getCommentary').and.returnValue(of());

    // WHEN
    fixture.detectChanges();
    component.isLoading.set(true);
    fixture.detectChanges();

    // THEN
    expect(component.isLoading()).toBeTrue();

    const commentaryFromTemplate = getByDataTestAttr(fixture.debugElement, 'commentary');
    expect(commentaryFromTemplate).toBeFalsy();

    const spinnerFromTemplate = getByDataTestAttr(fixture.debugElement, 'spinner');
    expect(spinnerFromTemplate).toBeTruthy();
  });

  it('should call getCommentary and display an error message', () => {
    // GIVEN
    const dataModal = new CommentaryModalData();
    dataModal.gameId = 10;
    dataModal.matchId = 'LOL_10';
    dataModal.pseudo = 'ninja';
    dataModal.puuid = 'puuid';
    component.data = dataModal;
    const getCommentarySpy = spyOn(commentaryService, 'getCommentary').and.returnValue(throwError(() => new Error()));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(getCommentarySpy).toHaveBeenCalledWith('LOL_10', 10, 'puuid', 'ninja');
    expect(component.commentaryDTOSignal()).toBeNull();
    expect(component.isLoading()).toBeFalse();

    const commentaryFromTemplate = getByDataTestAttr(fixture.debugElement, 'commentary');
    expect(commentaryFromTemplate).toBeFalsy();

    const spinnerFromTemplate = getByDataTestAttr(fixture.debugElement, 'spinner');
    expect(spinnerFromTemplate).toBeFalsy();

    const errorMessageFromtemplate = getByDataTestAttr(fixture.debugElement, 'get-commentary-error-message');
    expect(errorMessageFromtemplate).toBeTruthy();
    expect(errorMessageFromtemplate?.textContent).toEqual("Une erreur est survenue durant l'analyse, veuillez réessayer plus tard");
  });
});
