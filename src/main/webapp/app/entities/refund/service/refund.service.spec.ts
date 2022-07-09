import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { RefundStatus } from 'app/entities/enumerations/refund-status.model';
import { Currency } from 'app/entities/enumerations/currency.model';
import { IRefund, Refund } from '../refund.model';

import { RefundService } from './refund.service';

describe('Refund Service', () => {
  let service: RefundService;
  let httpMock: HttpTestingController;
  let elemDefault: IRefund;
  let expectedResult: IRefund | IRefund[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RefundService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      reference: 0,
      createDateTimeUtc: currentDate,
      refundNumber: 'AAAAAAA',
      status: RefundStatus.PENDING,
      amountToRefund: 0,
      convertedAmountToRefund: 0,
      convertedCurrency: Currency.CNY,
      conversionRate: 0,
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

    it('should create a Refund', () => {
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

      service.create(new Refund()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Refund', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          reference: 1,
          createDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          refundNumber: 'BBBBBB',
          status: 'BBBBBB',
          amountToRefund: 1,
          convertedAmountToRefund: 1,
          convertedCurrency: 'BBBBBB',
          conversionRate: 1,
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

    it('should partial update a Refund', () => {
      const patchObject = Object.assign(
        {
          refundNumber: 'BBBBBB',
          amountToRefund: 1,
          convertedAmountToRefund: 1,
          convertedCurrency: 'BBBBBB',
          conversionRate: 1,
        },
        new Refund()
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

    it('should return a list of Refund', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          reference: 1,
          createDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          refundNumber: 'BBBBBB',
          status: 'BBBBBB',
          amountToRefund: 1,
          convertedAmountToRefund: 1,
          convertedCurrency: 'BBBBBB',
          conversionRate: 1,
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

    it('should delete a Refund', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRefundToCollectionIfMissing', () => {
      it('should add a Refund to an empty array', () => {
        const refund: IRefund = { id: 123 };
        expectedResult = service.addRefundToCollectionIfMissing([], refund);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(refund);
      });

      it('should not add a Refund to an array that contains it', () => {
        const refund: IRefund = { id: 123 };
        const refundCollection: IRefund[] = [
          {
            ...refund,
          },
          { id: 456 },
        ];
        expectedResult = service.addRefundToCollectionIfMissing(refundCollection, refund);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Refund to an array that doesn't contain it", () => {
        const refund: IRefund = { id: 123 };
        const refundCollection: IRefund[] = [{ id: 456 }];
        expectedResult = service.addRefundToCollectionIfMissing(refundCollection, refund);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(refund);
      });

      it('should add only unique Refund to an array', () => {
        const refundArray: IRefund[] = [{ id: 123 }, { id: 456 }, { id: 92373 }];
        const refundCollection: IRefund[] = [{ id: 123 }];
        expectedResult = service.addRefundToCollectionIfMissing(refundCollection, ...refundArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const refund: IRefund = { id: 123 };
        const refund2: IRefund = { id: 456 };
        expectedResult = service.addRefundToCollectionIfMissing([], refund, refund2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(refund);
        expect(expectedResult).toContain(refund2);
      });

      it('should accept null and undefined values', () => {
        const refund: IRefund = { id: 123 };
        expectedResult = service.addRefundToCollectionIfMissing([], null, refund, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(refund);
      });

      it('should return initial array if no Refund is added', () => {
        const refundCollection: IRefund[] = [{ id: 123 }];
        expectedResult = service.addRefundToCollectionIfMissing(refundCollection, undefined, null);
        expect(expectedResult).toEqual(refundCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
