import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICurrentStats, CurrentStats } from '../current-stats.model';

import { CurrentStatsService } from './current-stats.service';

describe('CurrentStats Service', () => {
  let service: CurrentStatsService;
  let httpMock: HttpTestingController;
  let elemDefault: ICurrentStats;
  let expectedResult: ICurrentStats | ICurrentStats[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CurrentStatsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      totalBtc: 0,
      marketCap: 0,
      tradeVolume: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a CurrentStats', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new CurrentStats()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CurrentStats', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          totalBtc: 1,
          marketCap: 1,
          tradeVolume: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CurrentStats', () => {
      const patchObject = Object.assign(
        {
          totalBtc: 1,
          marketCap: 1,
          tradeVolume: 1,
        },
        new CurrentStats()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CurrentStats', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          totalBtc: 1,
          marketCap: 1,
          tradeVolume: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a CurrentStats', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCurrentStatsToCollectionIfMissing', () => {
      it('should add a CurrentStats to an empty array', () => {
        const currentStats: ICurrentStats = { id: 123 };
        expectedResult = service.addCurrentStatsToCollectionIfMissing([], currentStats);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(currentStats);
      });

      it('should not add a CurrentStats to an array that contains it', () => {
        const currentStats: ICurrentStats = { id: 123 };
        const currentStatsCollection: ICurrentStats[] = [
          {
            ...currentStats,
          },
          { id: 456 },
        ];
        expectedResult = service.addCurrentStatsToCollectionIfMissing(currentStatsCollection, currentStats);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CurrentStats to an array that doesn't contain it", () => {
        const currentStats: ICurrentStats = { id: 123 };
        const currentStatsCollection: ICurrentStats[] = [{ id: 456 }];
        expectedResult = service.addCurrentStatsToCollectionIfMissing(currentStatsCollection, currentStats);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(currentStats);
      });

      it('should add only unique CurrentStats to an array', () => {
        const currentStatsArray: ICurrentStats[] = [{ id: 123 }, { id: 456 }, { id: 83001 }];
        const currentStatsCollection: ICurrentStats[] = [{ id: 123 }];
        expectedResult = service.addCurrentStatsToCollectionIfMissing(currentStatsCollection, ...currentStatsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const currentStats: ICurrentStats = { id: 123 };
        const currentStats2: ICurrentStats = { id: 456 };
        expectedResult = service.addCurrentStatsToCollectionIfMissing([], currentStats, currentStats2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(currentStats);
        expect(expectedResult).toContain(currentStats2);
      });

      it('should accept null and undefined values', () => {
        const currentStats: ICurrentStats = { id: 123 };
        expectedResult = service.addCurrentStatsToCollectionIfMissing([], null, currentStats, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(currentStats);
      });

      it('should return initial array if no CurrentStats is added', () => {
        const currentStatsCollection: ICurrentStats[] = [{ id: 123 }];
        expectedResult = service.addCurrentStatsToCollectionIfMissing(currentStatsCollection, undefined, null);
        expect(expectedResult).toEqual(currentStatsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
