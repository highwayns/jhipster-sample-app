import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Locale } from 'app/entities/enumerations/locale.model';
import { IErrorReport, ErrorReport } from '../error-report.model';

import { ErrorReportService } from './error-report.service';

describe('ErrorReport Service', () => {
  let service: ErrorReportService;
  let httpMock: HttpTestingController;
  let elemDefault: IErrorReport;
  let expectedResult: IErrorReport | IErrorReport[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ErrorReportService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      language: Locale.EL_GR,
      isFatalError: false,
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

    it('should create a ErrorReport', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ErrorReport()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ErrorReport', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          language: 'BBBBBB',
          isFatalError: true,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ErrorReport', () => {
      const patchObject = Object.assign(
        {
          language: 'BBBBBB',
        },
        new ErrorReport()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ErrorReport', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          language: 'BBBBBB',
          isFatalError: true,
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

    it('should delete a ErrorReport', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addErrorReportToCollectionIfMissing', () => {
      it('should add a ErrorReport to an empty array', () => {
        const errorReport: IErrorReport = { id: 123 };
        expectedResult = service.addErrorReportToCollectionIfMissing([], errorReport);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(errorReport);
      });

      it('should not add a ErrorReport to an array that contains it', () => {
        const errorReport: IErrorReport = { id: 123 };
        const errorReportCollection: IErrorReport[] = [
          {
            ...errorReport,
          },
          { id: 456 },
        ];
        expectedResult = service.addErrorReportToCollectionIfMissing(errorReportCollection, errorReport);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ErrorReport to an array that doesn't contain it", () => {
        const errorReport: IErrorReport = { id: 123 };
        const errorReportCollection: IErrorReport[] = [{ id: 456 }];
        expectedResult = service.addErrorReportToCollectionIfMissing(errorReportCollection, errorReport);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(errorReport);
      });

      it('should add only unique ErrorReport to an array', () => {
        const errorReportArray: IErrorReport[] = [{ id: 123 }, { id: 456 }, { id: 10199 }];
        const errorReportCollection: IErrorReport[] = [{ id: 123 }];
        expectedResult = service.addErrorReportToCollectionIfMissing(errorReportCollection, ...errorReportArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const errorReport: IErrorReport = { id: 123 };
        const errorReport2: IErrorReport = { id: 456 };
        expectedResult = service.addErrorReportToCollectionIfMissing([], errorReport, errorReport2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(errorReport);
        expect(expectedResult).toContain(errorReport2);
      });

      it('should accept null and undefined values', () => {
        const errorReport: IErrorReport = { id: 123 };
        expectedResult = service.addErrorReportToCollectionIfMissing([], null, errorReport, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(errorReport);
      });

      it('should return initial array if no ErrorReport is added', () => {
        const errorReportCollection: IErrorReport[] = [{ id: 123 }];
        expectedResult = service.addErrorReportToCollectionIfMissing(errorReportCollection, undefined, null);
        expect(expectedResult).toEqual(errorReportCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
