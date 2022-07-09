import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { RefundStepAction } from 'app/entities/enumerations/refund-step-action.model';
import { RefundStatus } from 'app/entities/enumerations/refund-status.model';
import { IRefundStep, RefundStep } from '../refund-step.model';

import { RefundStepService } from './refund-step.service';

describe('RefundStep Service', () => {
  let service: RefundStepService;
  let httpMock: HttpTestingController;
  let elemDefault: IRefundStep;
  let expectedResult: IRefundStep | IRefundStep[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RefundStepService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      reference: 0,
      createDateTimeUtc: currentDate,
      action: RefundStepAction.START,
      status: RefundStatus.PENDING,
      description: 'AAAAAAA',
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

    it('should create a RefundStep', () => {
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

      service.create(new RefundStep()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RefundStep', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          reference: 1,
          createDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          action: 'BBBBBB',
          status: 'BBBBBB',
          description: 'BBBBBB',
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

    it('should partial update a RefundStep', () => {
      const patchObject = Object.assign(
        {
          reference: 1,
          action: 'BBBBBB',
        },
        new RefundStep()
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

    it('should return a list of RefundStep', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          reference: 1,
          createDateTimeUtc: currentDate.format(DATE_TIME_FORMAT),
          action: 'BBBBBB',
          status: 'BBBBBB',
          description: 'BBBBBB',
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

    it('should delete a RefundStep', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRefundStepToCollectionIfMissing', () => {
      it('should add a RefundStep to an empty array', () => {
        const refundStep: IRefundStep = { id: 123 };
        expectedResult = service.addRefundStepToCollectionIfMissing([], refundStep);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(refundStep);
      });

      it('should not add a RefundStep to an array that contains it', () => {
        const refundStep: IRefundStep = { id: 123 };
        const refundStepCollection: IRefundStep[] = [
          {
            ...refundStep,
          },
          { id: 456 },
        ];
        expectedResult = service.addRefundStepToCollectionIfMissing(refundStepCollection, refundStep);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RefundStep to an array that doesn't contain it", () => {
        const refundStep: IRefundStep = { id: 123 };
        const refundStepCollection: IRefundStep[] = [{ id: 456 }];
        expectedResult = service.addRefundStepToCollectionIfMissing(refundStepCollection, refundStep);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(refundStep);
      });

      it('should add only unique RefundStep to an array', () => {
        const refundStepArray: IRefundStep[] = [{ id: 123 }, { id: 456 }, { id: 62051 }];
        const refundStepCollection: IRefundStep[] = [{ id: 123 }];
        expectedResult = service.addRefundStepToCollectionIfMissing(refundStepCollection, ...refundStepArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const refundStep: IRefundStep = { id: 123 };
        const refundStep2: IRefundStep = { id: 456 };
        expectedResult = service.addRefundStepToCollectionIfMissing([], refundStep, refundStep2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(refundStep);
        expect(expectedResult).toContain(refundStep2);
      });

      it('should accept null and undefined values', () => {
        const refundStep: IRefundStep = { id: 123 };
        expectedResult = service.addRefundStepToCollectionIfMissing([], null, refundStep, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(refundStep);
      });

      it('should return initial array if no RefundStep is added', () => {
        const refundStepCollection: IRefundStep[] = [{ id: 123 }];
        expectedResult = service.addRefundStepToCollectionIfMissing(refundStepCollection, undefined, null);
        expect(expectedResult).toEqual(refundStepCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
