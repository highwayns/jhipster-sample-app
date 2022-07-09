import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { PaymentJobType } from 'app/entities/enumerations/payment-job-type.model';
import { Locale } from 'app/entities/enumerations/locale.model';
import { Currency } from 'app/entities/enumerations/currency.model';
import { IPaymentJob, PaymentJob } from '../payment-job.model';

import { PaymentJobService } from './payment-job.service';

describe('PaymentJob Service', () => {
  let service: PaymentJobService;
  let httpMock: HttpTestingController;
  let elemDefault: IPaymentJob;
  let expectedResult: IPaymentJob | IPaymentJob[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PaymentJobService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      reference: 0,
      createDateTimeUtc: currentDate,
      type: PaymentJobType.PAYMENT,
      traceReference: 0,
      configurationId: 'AAAAAAA',
      domain: 'AAAAAAA',
      locale: Locale.EL_GR,
      timeZone: 'AAAAAAA',
      parentPaymentJobReference: 0,
      displayUrl: 'AAAAAAA',
      currency: Currency.CNY,
      amountToCollect: 0,
      amountCollected: 0,
      paidAmount: 0,
      paidDateTimeUtc: currentDate,
      expirationDateTimeUtc: currentDate,
      dueDateTimeUtc: currentDate,
      lastUpdateTimeUtc: currentDate,
      lastProcessedTimeUtc: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          createDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          paidDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          expirationDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          dueDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          lastUpdateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          lastProcessedTimeUtc: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a PaymentJob', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          createDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          paidDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          expirationDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          dueDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          lastUpdateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          lastProcessedTimeUtc: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createDateTimeUtc: currentDate,
          paidDateTimeUtc: currentDate,
          expirationDateTimeUtc: currentDate,
          dueDateTimeUtc: currentDate,
          lastUpdateTimeUtc: currentDate,
          lastProcessedTimeUtc: currentDate,
        },
        returnedFromService
      );

      service.create(new PaymentJob()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PaymentJob', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          reference: 1,
          createDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          type: 'BBBBBB',
          traceReference: 1,
          configurationId: 'BBBBBB',
          domain: 'BBBBBB',
          locale: 'BBBBBB',
          timeZone: 'BBBBBB',
          parentPaymentJobReference: 1,
          displayUrl: 'BBBBBB',
          currency: 'BBBBBB',
          amountToCollect: 1,
          amountCollected: 1,
          paidAmount: 1,
          paidDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          expirationDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          dueDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          lastUpdateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          lastProcessedTimeUtc: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createDateTimeUtc: currentDate,
          paidDateTimeUtc: currentDate,
          expirationDateTimeUtc: currentDate,
          dueDateTimeUtc: currentDate,
          lastUpdateTimeUtc: currentDate,
          lastProcessedTimeUtc: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PaymentJob', () => {
      const patchObject = Object.assign(
        {
          reference: 1,
          createDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          traceReference: 1,
          locale: 'BBBBBB',
          parentPaymentJobReference: 1,
          currency: 'BBBBBB',
          amountToCollect: 1,
          paidDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          expirationDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          dueDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
        },
        new PaymentJob()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          createDateTimeUtc: currentDate,
          paidDateTimeUtc: currentDate,
          expirationDateTimeUtc: currentDate,
          dueDateTimeUtc: currentDate,
          lastUpdateTimeUtc: currentDate,
          lastProcessedTimeUtc: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PaymentJob', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          reference: 1,
          createDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          type: 'BBBBBB',
          traceReference: 1,
          configurationId: 'BBBBBB',
          domain: 'BBBBBB',
          locale: 'BBBBBB',
          timeZone: 'BBBBBB',
          parentPaymentJobReference: 1,
          displayUrl: 'BBBBBB',
          currency: 'BBBBBB',
          amountToCollect: 1,
          amountCollected: 1,
          paidAmount: 1,
          paidDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          expirationDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          dueDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          lastUpdateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          lastProcessedTimeUtc: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createDateTimeUtc: currentDate,
          paidDateTimeUtc: currentDate,
          expirationDateTimeUtc: currentDate,
          dueDateTimeUtc: currentDate,
          lastUpdateTimeUtc: currentDate,
          lastProcessedTimeUtc: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a PaymentJob', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPaymentJobToCollectionIfMissing', () => {
      it('should add a PaymentJob to an empty array', () => {
        const paymentJob: IPaymentJob = { id: 123 };
        expectedResult = service.addPaymentJobToCollectionIfMissing([], paymentJob);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentJob);
      });

      it('should not add a PaymentJob to an array that contains it', () => {
        const paymentJob: IPaymentJob = { id: 123 };
        const paymentJobCollection: IPaymentJob[] = [
          {
            ...paymentJob,
          },
          { id: 456 },
        ];
        expectedResult = service.addPaymentJobToCollectionIfMissing(paymentJobCollection, paymentJob);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PaymentJob to an array that doesn't contain it", () => {
        const paymentJob: IPaymentJob = { id: 123 };
        const paymentJobCollection: IPaymentJob[] = [{ id: 456 }];
        expectedResult = service.addPaymentJobToCollectionIfMissing(paymentJobCollection, paymentJob);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentJob);
      });

      it('should add only unique PaymentJob to an array', () => {
        const paymentJobArray: IPaymentJob[] = [{ id: 123 }, { id: 456 }, { id: 59519 }];
        const paymentJobCollection: IPaymentJob[] = [{ id: 123 }];
        expectedResult = service.addPaymentJobToCollectionIfMissing(paymentJobCollection, ...paymentJobArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const paymentJob: IPaymentJob = { id: 123 };
        const paymentJob2: IPaymentJob = { id: 456 };
        expectedResult = service.addPaymentJobToCollectionIfMissing([], paymentJob, paymentJob2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentJob);
        expect(expectedResult).toContain(paymentJob2);
      });

      it('should accept null and undefined values', () => {
        const paymentJob: IPaymentJob = { id: 123 };
        expectedResult = service.addPaymentJobToCollectionIfMissing([], null, paymentJob, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentJob);
      });

      it('should return initial array if no PaymentJob is added', () => {
        const paymentJobCollection: IPaymentJob[] = [{ id: 123 }];
        expectedResult = service.addPaymentJobToCollectionIfMissing(paymentJobCollection, undefined, null);
        expect(expectedResult).toEqual(paymentJobCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
