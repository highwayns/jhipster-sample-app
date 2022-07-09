import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { PaymentStepAction } from 'app/entities/enumerations/payment-step-action.model';
import { PaymentStatus } from 'app/entities/enumerations/payment-status.model';
import { IPaymentStep, PaymentStep } from '../payment-step.model';

import { PaymentStepService } from './payment-step.service';

describe('PaymentStep Service', () => {
  let service: PaymentStepService;
  let httpMock: HttpTestingController;
  let elemDefault: IPaymentStep;
  let expectedResult: IPaymentStep | IPaymentStep[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PaymentStepService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      reference: 0,
      createDateTimeUtc: currentDate,
      action: PaymentStepAction.DISPLAYHOSTEDPAGE,
      status: PaymentStatus.PENDING,
      amountToCollect: 0,
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

    it('should create a PaymentStep', () => {
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

      service.create(new PaymentStep()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PaymentStep', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          reference: 1,
          createDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          action: 'BBBBBB',
          status: 'BBBBBB',
          amountToCollect: 1,
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

    it('should partial update a PaymentStep', () => {
      const patchObject = Object.assign(
        {
          reference: 1,
          createDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          amountToCollect: 1,
        },
        new PaymentStep()
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

    it('should return a list of PaymentStep', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          reference: 1,
          createDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          action: 'BBBBBB',
          status: 'BBBBBB',
          amountToCollect: 1,
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

    it('should delete a PaymentStep', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPaymentStepToCollectionIfMissing', () => {
      it('should add a PaymentStep to an empty array', () => {
        const paymentStep: IPaymentStep = { id: 123 };
        expectedResult = service.addPaymentStepToCollectionIfMissing([], paymentStep);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentStep);
      });

      it('should not add a PaymentStep to an array that contains it', () => {
        const paymentStep: IPaymentStep = { id: 123 };
        const paymentStepCollection: IPaymentStep[] = [
          {
            ...paymentStep,
          },
          { id: 456 },
        ];
        expectedResult = service.addPaymentStepToCollectionIfMissing(paymentStepCollection, paymentStep);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PaymentStep to an array that doesn't contain it", () => {
        const paymentStep: IPaymentStep = { id: 123 };
        const paymentStepCollection: IPaymentStep[] = [{ id: 456 }];
        expectedResult = service.addPaymentStepToCollectionIfMissing(paymentStepCollection, paymentStep);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentStep);
      });

      it('should add only unique PaymentStep to an array', () => {
        const paymentStepArray: IPaymentStep[] = [{ id: 123 }, { id: 456 }, { id: 67340 }];
        const paymentStepCollection: IPaymentStep[] = [{ id: 123 }];
        expectedResult = service.addPaymentStepToCollectionIfMissing(paymentStepCollection, ...paymentStepArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const paymentStep: IPaymentStep = { id: 123 };
        const paymentStep2: IPaymentStep = { id: 456 };
        expectedResult = service.addPaymentStepToCollectionIfMissing([], paymentStep, paymentStep2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentStep);
        expect(expectedResult).toContain(paymentStep2);
      });

      it('should accept null and undefined values', () => {
        const paymentStep: IPaymentStep = { id: 123 };
        expectedResult = service.addPaymentStepToCollectionIfMissing([], null, paymentStep, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentStep);
      });

      it('should return initial array if no PaymentStep is added', () => {
        const paymentStepCollection: IPaymentStep[] = [{ id: 123 }];
        expectedResult = service.addPaymentStepToCollectionIfMissing(paymentStepCollection, undefined, null);
        expect(expectedResult).toEqual(paymentStepCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
