import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMonthlyReports, MonthlyReports } from '../monthly-reports.model';

import { MonthlyReportsService } from './monthly-reports.service';

describe('MonthlyReports Service', () => {
  let service: MonthlyReportsService;
  let httpMock: HttpTestingController;
  let elemDefault: IMonthlyReports;
  let expectedResult: IMonthlyReports | IMonthlyReports[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MonthlyReportsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      date: currentDate,
      transactionsBtc: 0,
      avgTransactionSizeBtc: 0,
      transactionVolumePerUser: 0,
      totalFeesBtc: 0,
      feesPerUserBtc: 0,
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

    it('should create a MonthlyReports', () => {
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

      service.create(new MonthlyReports()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MonthlyReports', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
          transactionsBtc: 1,
          avgTransactionSizeBtc: 1,
          transactionVolumePerUser: 1,
          totalFeesBtc: 1,
          feesPerUserBtc: 1,
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

    it('should partial update a MonthlyReports', () => {
      const patchObject = Object.assign(
        {
          transactionsBtc: 1,
          transactionVolumePerUser: 1,
        },
        new MonthlyReports()
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

    it('should return a list of MonthlyReports', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
          transactionsBtc: 1,
          avgTransactionSizeBtc: 1,
          transactionVolumePerUser: 1,
          totalFeesBtc: 1,
          feesPerUserBtc: 1,
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

    it('should delete a MonthlyReports', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMonthlyReportsToCollectionIfMissing', () => {
      it('should add a MonthlyReports to an empty array', () => {
        const monthlyReports: IMonthlyReports = { id: 123 };
        expectedResult = service.addMonthlyReportsToCollectionIfMissing([], monthlyReports);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(monthlyReports);
      });

      it('should not add a MonthlyReports to an array that contains it', () => {
        const monthlyReports: IMonthlyReports = { id: 123 };
        const monthlyReportsCollection: IMonthlyReports[] = [
          {
            ...monthlyReports,
          },
          { id: 456 },
        ];
        expectedResult = service.addMonthlyReportsToCollectionIfMissing(monthlyReportsCollection, monthlyReports);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MonthlyReports to an array that doesn't contain it", () => {
        const monthlyReports: IMonthlyReports = { id: 123 };
        const monthlyReportsCollection: IMonthlyReports[] = [{ id: 456 }];
        expectedResult = service.addMonthlyReportsToCollectionIfMissing(monthlyReportsCollection, monthlyReports);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(monthlyReports);
      });

      it('should add only unique MonthlyReports to an array', () => {
        const monthlyReportsArray: IMonthlyReports[] = [{ id: 123 }, { id: 456 }, { id: 33442 }];
        const monthlyReportsCollection: IMonthlyReports[] = [{ id: 123 }];
        expectedResult = service.addMonthlyReportsToCollectionIfMissing(monthlyReportsCollection, ...monthlyReportsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const monthlyReports: IMonthlyReports = { id: 123 };
        const monthlyReports2: IMonthlyReports = { id: 456 };
        expectedResult = service.addMonthlyReportsToCollectionIfMissing([], monthlyReports, monthlyReports2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(monthlyReports);
        expect(expectedResult).toContain(monthlyReports2);
      });

      it('should accept null and undefined values', () => {
        const monthlyReports: IMonthlyReports = { id: 123 };
        expectedResult = service.addMonthlyReportsToCollectionIfMissing([], null, monthlyReports, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(monthlyReports);
      });

      it('should return initial array if no MonthlyReports is added', () => {
        const monthlyReportsCollection: IMonthlyReports[] = [{ id: 123 }];
        expectedResult = service.addMonthlyReportsToCollectionIfMissing(monthlyReportsCollection, undefined, null);
        expect(expectedResult).toEqual(monthlyReportsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
