import { TestBed } from '@angular/core/testing';

import { GetVersionsService } from './get-versions.service';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { GET_VERSIONS_LOL_DRAGON_URL } from '../../common/constants/api-urls';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';

describe('GetVersionsService', () => {
  let service: GetVersionsService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GetVersionsService, provideHttpClient(), provideHttpClientTesting()],
    });
    httpMock = TestBed.inject(HttpTestingController);
    service = TestBed.inject(GetVersionsService);
  });

  afterEach(() => httpMock.verify());

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call getAllVersionsLol and return a list of all versions as string []', () => {
    // GIVEN
    const versions = ['15.11.1', '15.11.0'];

    // WHEN
    service.getAllVersionsLol().subscribe((res) => {
      expect(res).toBe(versions);
    });

    // THEN
    const req = httpMock.expectOne(GET_VERSIONS_LOL_DRAGON_URL);
    expect(req.request.method).toBe('GET');
    req.flush(versions);
  });
});
