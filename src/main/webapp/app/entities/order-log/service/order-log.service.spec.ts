import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { YesNo } from 'app/entities/enumerations/yes-no.model';
import { Status } from 'app/entities/enumerations/status.model';
import { IOrderLog, OrderLog } from '../order-log.model';

import { OrderLogService } from './order-log.service';

describe('OrderLog Service', () => {
  let service: OrderLogService;
  let httpMock: HttpTestingController;
  let elemDefault: IOrderLog;
  let expectedResult: IOrderLog | IOrderLog[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OrderLogService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      date: currentDate,
      btc: 0,
      marketPrice: YesNo.Y,
      btcPrice: 0,
      fiat: 0,
      pId: 0,
      stopPrice: 'AAAAAAA',
      status: Status.ACTIVE,
      btcRemaining: 0,
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

    it('should create a OrderLog', () => {
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

      service.create(new OrderLog()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrderLog', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
          btc: 1,
          marketPrice: 'BBBBBB',
          btcPrice: 1,
          fiat: 1,
          pId: 1,
          stopPrice: 'BBBBBB',
          status: 'BBBBBB',
          btcRemaining: 1,
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

    it('should partial update a OrderLog', () => {
      const patchObject = Object.assign(
        {
          btc: 1,
          stopPrice: 'BBBBBB',
        },
        new OrderLog()
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

    it('should return a list of OrderLog', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
          btc: 1,
          marketPrice: 'BBBBBB',
          btcPrice: 1,
          fiat: 1,
          pId: 1,
          stopPrice: 'BBBBBB',
          status: 'BBBBBB',
          btcRemaining: 1,
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

    it('should delete a OrderLog', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addOrderLogToCollectionIfMissing', () => {
      it('should add a OrderLog to an empty array', () => {
        const orderLog: IOrderLog = { id: 123 };
        expectedResult = service.addOrderLogToCollectionIfMissing([], orderLog);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orderLog);
      });

      it('should not add a OrderLog to an array that contains it', () => {
        const orderLog: IOrderLog = { id: 123 };
        const orderLogCollection: IOrderLog[] = [
          {
            ...orderLog,
          },
          { id: 456 },
        ];
        expectedResult = service.addOrderLogToCollectionIfMissing(orderLogCollection, orderLog);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrderLog to an array that doesn't contain it", () => {
        const orderLog: IOrderLog = { id: 123 };
        const orderLogCollection: IOrderLog[] = [{ id: 456 }];
        expectedResult = service.addOrderLogToCollectionIfMissing(orderLogCollection, orderLog);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orderLog);
      });

      it('should add only unique OrderLog to an array', () => {
        const orderLogArray: IOrderLog[] = [{ id: 123 }, { id: 456 }, { id: 12619 }];
        const orderLogCollection: IOrderLog[] = [{ id: 123 }];
        expectedResult = service.addOrderLogToCollectionIfMissing(orderLogCollection, ...orderLogArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const orderLog: IOrderLog = { id: 123 };
        const orderLog2: IOrderLog = { id: 456 };
        expectedResult = service.addOrderLogToCollectionIfMissing([], orderLog, orderLog2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orderLog);
        expect(expectedResult).toContain(orderLog2);
      });

      it('should accept null and undefined values', () => {
        const orderLog: IOrderLog = { id: 123 };
        expectedResult = service.addOrderLogToCollectionIfMissing([], null, orderLog, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orderLog);
      });

      it('should return initial array if no OrderLog is added', () => {
        const orderLogCollection: IOrderLog[] = [{ id: 123 }];
        expectedResult = service.addOrderLogToCollectionIfMissing(orderLogCollection, undefined, null);
        expect(expectedResult).toEqual(orderLogCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
