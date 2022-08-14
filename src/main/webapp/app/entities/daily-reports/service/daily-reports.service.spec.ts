import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDailyReports, DailyReports } from '../daily-reports.model';

import { DailyReportsService } from './daily-reports.service';

describe('DailyReports Service', () => {
  let service: DailyReportsService;
  let httpMock: HttpTestingController;
  let elemDefault: IDailyReports;
  let expectedResult: IDailyReports | IDailyReports[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DailyReportsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      date: currentDate,
      totalBtc: 0,
      totalFiatUsd: 0,
      openOrdersBtc: 0,
      btcPerUser: 0,
      transactionsBtc: 0,
      avgTransactionSizeBtc: 0,
      transactionVolumePerUser: 0,
      totalFeesBtc: 0,
      feesPerUserBtc: 0,
      usdPerUser: 0,
      grossProfitBtc: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          date: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a DailyReports', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          date: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.create(new DailyReports()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DailyReports', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
          totalBtc: 1,
          totalFiatUsd: 1,
          openOrdersBtc: 1,
          btcPerUser: 1,
          transactionsBtc: 1,
          avgTransactionSizeBtc: 1,
          transactionVolumePerUser: 1,
          totalFeesBtc: 1,
          feesPerUserBtc: 1,
          usdPerUser: 1,
          grossProfitBtc: 1,
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

    it('should partial update a DailyReports', () => {
      const patchObject = Object.assign(
        {
          date: currentDate.format(DATE_TIME_FORMAT),
          totalBtc: 1,
          avgTransactionSizeBtc: 1,
          totalFeesBtc: 1,
          usdPerUser: 1,
        },
        new DailyReports()
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

    it('should return a list of DailyReports', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
          totalBtc: 1,
          totalFiatUsd: 1,
          openOrdersBtc: 1,
          btcPerUser: 1,
          transactionsBtc: 1,
          avgTransactionSizeBtc: 1,
          transactionVolumePerUser: 1,
          totalFeesBtc: 1,
          feesPerUserBtc: 1,
          usdPerUser: 1,
          grossProfitBtc: 1,
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

    it('should delete a DailyReports', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDailyReportsToCollectionIfMissing', () => {
      it('should add a DailyReports to an empty array', () => {
        const dailyReports: IDailyReports = { id: 123 };
        expectedResult = service.addDailyReportsToCollectionIfMissing([], dailyReports);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dailyReports);
      });

      it('should not add a DailyReports to an array that contains it', () => {
        const dailyReports: IDailyReports = { id: 123 };
        const dailyReportsCollection: IDailyReports[] = [
          {
            ...dailyReports,
          },
          { id: 456 },
        ];
        expectedResult = service.addDailyReportsToCollectionIfMissing(dailyReportsCollection, dailyReports);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DailyReports to an array that doesn't contain it", () => {
        const dailyReports: IDailyReports = { id: 123 };
        const dailyReportsCollection: IDailyReports[] = [{ id: 456 }];
        expectedResult = service.addDailyReportsToCollectionIfMissing(dailyReportsCollection, dailyReports);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dailyReports);
      });

      it('should add only unique DailyReports to an array', () => {
        const dailyReportsArray: IDailyReports[] = [{ id: 123 }, { id: 456 }, { id: 42700 }];
        const dailyReportsCollection: IDailyReports[] = [{ id: 123 }];
        expectedResult = service.addDailyReportsToCollectionIfMissing(dailyReportsCollection, ...dailyReportsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dailyReports: IDailyReports = { id: 123 };
        const dailyReports2: IDailyReports = { id: 456 };
        expectedResult = service.addDailyReportsToCollectionIfMissing([], dailyReports, dailyReports2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dailyReports);
        expect(expectedResult).toContain(dailyReports2);
      });

      it('should accept null and undefined values', () => {
        const dailyReports: IDailyReports = { id: 123 };
        expectedResult = service.addDailyReportsToCollectionIfMissing([], null, dailyReports, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dailyReports);
      });

      it('should return initial array if no DailyReports is added', () => {
        const dailyReportsCollection: IDailyReports[] = [{ id: 123 }];
        expectedResult = service.addDailyReportsToCollectionIfMissing(dailyReportsCollection, undefined, null);
        expect(expectedResult).toEqual(dailyReportsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
