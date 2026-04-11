import { TestBed } from '@angular/core/testing';

import { CommentaryService } from './commentary.service';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { CommentaryDTO } from '../../common/models/CommentaryDTO';
import { environment } from '../../../environments/environment';

describe('CommentaryService', () => {
  let service: CommentaryService;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [],
      providers: [provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();
  });

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommentaryService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call getCommentary with correct URL', () => {
    // GIVEN
    const commentaryMock = new CommentaryDTO();
    commentaryMock.id = '10';
    commentaryMock.object = 'object';
    commentaryMock.created = 10155884466;
    commentaryMock.model = 'GPT-4';
    commentaryMock.content = "Un commentaire d'une partie de lol";

    // WHEN
    service.getCommentary('matchId', 3038, 'puuid', 'pseudo').subscribe((res) => {
      expect(res).not.toBeNull();
      expect(res).toEqual(commentaryMock);
    });

    // THEN
    const url = environment.apiBaseUrl + '/matchCommentary/matchId?gameId=3038&puuid=puuid&pseudo=pseudo';
    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(commentaryMock);
  });
});
