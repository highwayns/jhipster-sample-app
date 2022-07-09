import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { CaptureStatus } from 'app/entities/enumerations/capture-status.model';
import { Currency } from 'app/entities/enumerations/currency.model';
import { ICapture, Capture } from '../capture.model';

import { CaptureService } from './capture.service';

describe('Capture Service', () => {
  let service: CaptureService;
  let httpMock: HttpTestingController;
  let elemDefault: ICapture;
  let expectedResult: ICapture | ICapture[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CaptureService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      reference: 0,
      createDateTimeUtc: currentDate,
      status: CaptureStatus.PENDING,
      amountToCapture: 0,
      convertedAmountToCapture: 0,
      convertedCurrency: Currency.CNY,
      conversionRate: 0,
      isFinalCapture: false,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          createDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Capture', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          createDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createDateTimeUtc: currentDate,
        },
        returnedFromService
      );

      service.create(new Capture()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Capture', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          reference: 1,
          createDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          status: 'BBBBBB',
          amountToCapture: 1,
          convertedAmountToCapture: 1,
          convertedCurrency: 'BBBBBB',
          conversionRate: 1,
          isFinalCapture: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createDateTimeUtc: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Capture', () => {
      const patchObject = Object.assign(
        {
          convertedCurrency: 'BBBBBB',
          conversionRate: 1,
        },
        new Capture()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          createDateTimeUtc: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Capture', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          reference: 1,
          createDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          status: 'BBBBBB',
          amountToCapture: 1,
          convertedAmountToCapture: 1,
          convertedCurrency: 'BBBBBB',
          conversionRate: 1,
          isFinalCapture: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createDateTimeUtc: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Capture', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCaptureToCollectionIfMissing', () => {
      it('should add a Capture to an empty array', () => {
        const capture: ICapture = { id: 123 };
        expectedResult = service.addCaptureToCollectionIfMissing([], capture);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(capture);
      });

      it('should not add a Capture to an array that contains it', () => {
        const capture: ICapture = { id: 123 };
        const captureCollection: ICapture[] = [
          {
            ...capture,
          },
          { id: 456 },
        ];
        expectedResult = service.addCaptureToCollectionIfMissing(captureCollection, capture);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Capture to an array that doesn't contain it", () => {
        const capture: ICapture = { id: 123 };
        const captureCollection: ICapture[] = [{ id: 456 }];
        expectedResult = service.addCaptureToCollectionIfMissing(captureCollection, capture);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(capture);
      });

      it('should add only unique Capture to an array', () => {
        const captureArray: ICapture[] = [{ id: 123 }, { id: 456 }, { id: 38062 }];
        const captureCollection: ICapture[] = [{ id: 123 }];
        expectedResult = service.addCaptureToCollectionIfMissing(captureCollection, ...captureArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const capture: ICapture = { id: 123 };
        const capture2: ICapture = { id: 456 };
        expectedResult = service.addCaptureToCollectionIfMissing([], capture, capture2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(capture);
        expect(expectedResult).toContain(capture2);
      });

      it('should accept null and undefined values', () => {
        const capture: ICapture = { id: 123 };
        expectedResult = service.addCaptureToCollectionIfMissing([], null, capture, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(capture);
      });

      it('should return initial array if no Capture is added', () => {
        const captureCollection: ICapture[] = [{ id: 123 }];
        expectedResult = service.addCaptureToCollectionIfMissing(captureCollection, undefined, null);
        expect(expectedResult).toEqual(captureCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
