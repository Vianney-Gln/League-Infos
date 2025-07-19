import { TestBed } from '@angular/core/testing';
import { GetChampionsService } from './get-champions.service';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { FreeChampionsDTO } from '../../common/models/freeChampionsDTO';
import { ChampionData } from '../../common/models/championsInfos';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';

describe('GetChampionsService', () => {
  let service: GetChampionsService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GetChampionsService, provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();
    service = TestBed.inject(GetChampionsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call getFreeChampions with correct URL and headers', () => {
    // GIVEN
    const mockResponse: FreeChampionsDTO = {
      freeChampionIds: [1, 2, 3],
      maxNewPlayerLevel: 10,
      freeChampionIdsForNewPlayers: [4, 5, 6],
    } as FreeChampionsDTO;

    // WHEN
    service.getFreeChampions().subscribe((res) => {
      expect(res.freeChampionIds).toEqual([1, 2, 3]);
      expect(res.maxNewPlayerLevel).toEqual(10);
      expect(res.freeChampionIdsForNewPlayers).toEqual([4, 5, 6]);
    });

    // THEN
    const url = environment.apiBaseUrl + '/champions/free';
    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should call getAllChampionsInfos with correct URL', () => {
    // GIVEN
    const mockResponse: ChampionData = { type: 'test', format: 'format', version: '4', data: { testChampion: {} as any } } as ChampionData;

    // WHEN
    service.getAllChampionsInfos('15.11.1').subscribe((res) => {
      expect(res.type).toEqual('test');
      expect(res.format).toEqual('format');
      expect(res.version).toEqual('4');
    });

    // THEN
    const req = httpMock.expectOne('https://ddragon.leagueoflegends.com/cdn/15.11.1/data/fr_FR/champion.json');
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should have championDataSignal initialized as null', () => {
    expect(service.championDataSignal()).toBeNull();
  });

  it('should have isFreeChampErrorSignal initialized as false', () => {
    expect(service.isFreeChampErrorSignal()).toBeFalse();
  });

  it('should call getMostBannedChampionId with correct URL', () => {
    // GIVEN
    const mockResponse: number = 200;

    // WHEN
    service.getMostBannedChampionId().subscribe((res) => {
      expect(res).toEqual(200);
    });

    // THEN
    const req = httpMock.expectOne(`${environment.apiBaseUrl}/stats/most-banned-champion`);
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });
});
