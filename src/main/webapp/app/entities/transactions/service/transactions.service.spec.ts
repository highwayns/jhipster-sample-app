import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { YesNo } from 'app/entities/enumerations/yes-no.model';
import { ITransactions, Transactions } from '../transactions.model';

import { TransactionsService } from './transactions.service';

describe('Transactions Service', () => {
  let service: TransactionsService;
  let httpMock: HttpTestingController;
  let elemDefault: ITransactions;
  let expectedResult: ITransactions | ITransactions[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TransactionsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      date: currentDate,
      btc: 0,
      btcPrice: 0,
      fiat: 0,
      fee: 0,
      fee1: 0,
      btcNet: 0,
      btcNet1: 0,
      btcBefore1: 0,
      btcAfter1: 0,
      fiatBefore1: 0,
      fiatAfter1: 0,
      btcBefore: 0,
      btcAfter: 0,
      fiatBefore: 0,
      fiatAfter: 0,
      feeLevel: 0,
      feeLevel1: 0,
      origBtcPrice: 0,
      conversionFee: 0,
      convertAmount: 0,
      convertRateGiven: 0,
      convertSystemRate: 0,
      conversion: YesNo.Y,
      bidAtTransaction: 0,
      askAtTransaction: 0,
      factored: YesNo.Y,
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

    it('should create a Transactions', () => {
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

      service.create(new Transactions()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Transactions', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
          btc: 1,
          btcPrice: 1,
          fiat: 1,
          fee: 1,
          fee1: 1,
          btcNet: 1,
          btcNet1: 1,
          btcBefore1: 1,
          btcAfter1: 1,
          fiatBefore1: 1,
          fiatAfter1: 1,
          btcBefore: 1,
          btcAfter: 1,
          fiatBefore: 1,
          fiatAfter: 1,
          feeLevel: 1,
          feeLevel1: 1,
          origBtcPrice: 1,
          conversionFee: 1,
          convertAmount: 1,
          convertRateGiven: 1,
          convertSystemRate: 1,
          conversion: 'BBBBBB',
          bidAtTransaction: 1,
          askAtTransaction: 1,
          factored: 'BBBBBB',
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

    it('should partial update a Transactions', () => {
      const patchObject = Object.assign(
        {
          date: currentDate.format(DATE_TIME_FORMAT),
          btc: 1,
          btcPrice: 1,
          fiat: 1,
          btcNet1: 1,
          fiatAfter1: 1,
          btcBefore: 1,
          btcAfter: 1,
          feeLevel: 1,
          feeLevel1: 1,
          convertSystemRate: 1,
          bidAtTransaction: 1,
        },
        new Transactions()
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

    it('should return a list of Transactions', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
          btc: 1,
          btcPrice: 1,
          fiat: 1,
          fee: 1,
          fee1: 1,
          btcNet: 1,
          btcNet1: 1,
          btcBefore1: 1,
          btcAfter1: 1,
          fiatBefore1: 1,
          fiatAfter1: 1,
          btcBefore: 1,
          btcAfter: 1,
          fiatBefore: 1,
          fiatAfter: 1,
          feeLevel: 1,
          feeLevel1: 1,
          origBtcPrice: 1,
          conversionFee: 1,
          convertAmount: 1,
          convertRateGiven: 1,
          convertSystemRate: 1,
          conversion: 'BBBBBB',
          bidAtTransaction: 1,
          askAtTransaction: 1,
          factored: 'BBBBBB',
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

    it('should delete a Transactions', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTransactionsToCollectionIfMissing', () => {
      it('should add a Transactions to an empty array', () => {
        const transactions: ITransactions = { id: 123 };
        expectedResult = service.addTransactionsToCollectionIfMissing([], transactions);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transactions);
      });

      it('should not add a Transactions to an array that contains it', () => {
        const transactions: ITransactions = { id: 123 };
        const transactionsCollection: ITransactions[] = [
          {
            ...transactions,
          },
          { id: 456 },
        ];
        expectedResult = service.addTransactionsToCollectionIfMissing(transactionsCollection, transactions);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Transactions to an array that doesn't contain it", () => {
        const transactions: ITransactions = { id: 123 };
        const transactionsCollection: ITransactions[] = [{ id: 456 }];
        expectedResult = service.addTransactionsToCollectionIfMissing(transactionsCollection, transactions);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transactions);
      });

      it('should add only unique Transactions to an array', () => {
        const transactionsArray: ITransactions[] = [{ id: 123 }, { id: 456 }, { id: 17185 }];
        const transactionsCollection: ITransactions[] = [{ id: 123 }];
        expectedResult = service.addTransactionsToCollectionIfMissing(transactionsCollection, ...transactionsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const transactions: ITransactions = { id: 123 };
        const transactions2: ITransactions = { id: 456 };
        expectedResult = service.addTransactionsToCollectionIfMissing([], transactions, transactions2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transactions);
        expect(expectedResult).toContain(transactions2);
      });

      it('should accept null and undefined values', () => {
        const transactions: ITransactions = { id: 123 };
        expectedResult = service.addTransactionsToCollectionIfMissing([], null, transactions, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transactions);
      });

      it('should return initial array if no Transactions is added', () => {
        const transactionsCollection: ITransactions[] = [{ id: 123 }];
        expectedResult = service.addTransactionsToCollectionIfMissing(transactionsCollection, undefined, null);
        expect(expectedResult).toEqual(transactionsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
