import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAbuseReport, AbuseReport } from '../abuse-report.model';

import { AbuseReportService } from './abuse-report.service';

describe('AbuseReport Service', () => {
  let service: AbuseReportService;
  let httpMock: HttpTestingController;
  let elemDefault: IAbuseReport;
  let expectedResult: IAbuseReport | IAbuseReport[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AbuseReportService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      score: 0,
      createdDateTimeUtc: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          createdDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a AbuseReport', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          createdDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDateTimeUtc: currentDate,
        },
        returnedFromService
      );

      service.create(new AbuseReport()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AbuseReport', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          score: 1,
          createdDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDateTimeUtc: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AbuseReport', () => {
      const patchObject = Object.assign(
        {
          score: 1,
          createdDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
        },
        new AbuseReport()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          createdDateTimeUtc: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AbuseReport', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          score: 1,
          createdDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDateTimeUtc: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a AbuseReport', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAbuseReportToCollectionIfMissing', () => {
      it('should add a AbuseReport to an empty array', () => {
        const abuseReport: IAbuseReport = { id: 123 };
        expectedResult = service.addAbuseReportToCollectionIfMissing([], abuseReport);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(abuseReport);
      });

      it('should not add a AbuseReport to an array that contains it', () => {
        const abuseReport: IAbuseReport = { id: 123 };
        const abuseReportCollection: IAbuseReport[] = [
          {
            ...abuseReport,
          },
          { id: 456 },
        ];
        expectedResult = service.addAbuseReportToCollectionIfMissing(abuseReportCollection, abuseReport);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AbuseReport to an array that doesn't contain it", () => {
        const abuseReport: IAbuseReport = { id: 123 };
        const abuseReportCollection: IAbuseReport[] = [{ id: 456 }];
        expectedResult = service.addAbuseReportToCollectionIfMissing(abuseReportCollection, abuseReport);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(abuseReport);
      });

      it('should add only unique AbuseReport to an array', () => {
        const abuseReportArray: IAbuseReport[] = [{ id: 123 }, { id: 456 }, { id: 66802 }];
        const abuseReportCollection: IAbuseReport[] = [{ id: 123 }];
        expectedResult = service.addAbuseReportToCollectionIfMissing(abuseReportCollection, ...abuseReportArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const abuseReport: IAbuseReport = { id: 123 };
        const abuseReport2: IAbuseReport = { id: 456 };
        expectedResult = service.addAbuseReportToCollectionIfMissing([], abuseReport, abuseReport2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(abuseReport);
        expect(expectedResult).toContain(abuseReport2);
      });

      it('should accept null and undefined values', () => {
        const abuseReport: IAbuseReport = { id: 123 };
        expectedResult = service.addAbuseReportToCollectionIfMissing([], null, abuseReport, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(abuseReport);
      });

      it('should return initial array if no AbuseReport is added', () => {
        const abuseReportCollection: IAbuseReport[] = [{ id: 123 }];
        expectedResult = service.addAbuseReportToCollectionIfMissing(abuseReportCollection, undefined, null);
        expect(expectedResult).toEqual(abuseReportCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
