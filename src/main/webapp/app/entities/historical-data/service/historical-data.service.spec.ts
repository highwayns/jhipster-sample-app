import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IHistoricalData, HistoricalData } from '../historical-data.model';

import { HistoricalDataService } from './historical-data.service';

describe('HistoricalData Service', () => {
  let service: HistoricalDataService;
  let httpMock: HttpTestingController;
  let elemDefault: IHistoricalData;
  let expectedResult: IHistoricalData | IHistoricalData[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(HistoricalDataService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      date: currentDate,
      usd: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          date: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a HistoricalData', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          date: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.create(new HistoricalData()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a HistoricalData', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          date: currentDate.format(DATE_FORMAT),
          usd: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a HistoricalData', () => {
      const patchObject = Object.assign(
        {
          usd: 1,
        },
        new HistoricalData()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of HistoricalData', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          date: currentDate.format(DATE_FORMAT),
          usd: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a HistoricalData', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addHistoricalDataToCollectionIfMissing', () => {
      it('should add a HistoricalData to an empty array', () => {
        const historicalData: IHistoricalData = { id: 123 };
        expectedResult = service.addHistoricalDataToCollectionIfMissing([], historicalData);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(historicalData);
      });

      it('should not add a HistoricalData to an array that contains it', () => {
        const historicalData: IHistoricalData = { id: 123 };
        const historicalDataCollection: IHistoricalData[] = [
          {
            ...historicalData,
          },
          { id: 456 },
        ];
        expectedResult = service.addHistoricalDataToCollectionIfMissing(historicalDataCollection, historicalData);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a HistoricalData to an array that doesn't contain it", () => {
        const historicalData: IHistoricalData = { id: 123 };
        const historicalDataCollection: IHistoricalData[] = [{ id: 456 }];
        expectedResult = service.addHistoricalDataToCollectionIfMissing(historicalDataCollection, historicalData);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(historicalData);
      });

      it('should add only unique HistoricalData to an array', () => {
        const historicalDataArray: IHistoricalData[] = [{ id: 123 }, { id: 456 }, { id: 66463 }];
        const historicalDataCollection: IHistoricalData[] = [{ id: 123 }];
        expectedResult = service.addHistoricalDataToCollectionIfMissing(historicalDataCollection, ...historicalDataArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const historicalData: IHistoricalData = { id: 123 };
        const historicalData2: IHistoricalData = { id: 456 };
        expectedResult = service.addHistoricalDataToCollectionIfMissing([], historicalData, historicalData2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(historicalData);
        expect(expectedResult).toContain(historicalData2);
      });

      it('should accept null and undefined values', () => {
        const historicalData: IHistoricalData = { id: 123 };
        expectedResult = service.addHistoricalDataToCollectionIfMissing([], null, historicalData, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(historicalData);
      });

      it('should return initial array if no HistoricalData is added', () => {
        const historicalDataCollection: IHistoricalData[] = [{ id: 123 }];
        expectedResult = service.addHistoricalDataToCollectionIfMissing(historicalDataCollection, undefined, null);
        expect(expectedResult).toEqual(historicalDataCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
