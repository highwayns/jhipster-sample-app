import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IBitcoindLog, BitcoindLog } from '../bitcoind-log.model';

import { BitcoindLogService } from './bitcoind-log.service';

describe('BitcoindLog Service', () => {
  let service: BitcoindLogService;
  let httpMock: HttpTestingController;
  let elemDefault: IBitcoindLog;
  let expectedResult: IBitcoindLog | IBitcoindLog[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BitcoindLogService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      transactionId: 'AAAAAAA',
      amount: 0,
      date: currentDate,
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

    it('should create a BitcoindLog', () => {
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

      service.create(new BitcoindLog()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BitcoindLog', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          transactionId: 'BBBBBB',
          amount: 1,
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

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BitcoindLog', () => {
      const patchObject = Object.assign(
        {
          date: currentDate.format(DATE_TIME_FORMAT),
        },
        new BitcoindLog()
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

    it('should return a list of BitcoindLog', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          transactionId: 'BBBBBB',
          amount: 1,
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

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a BitcoindLog', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBitcoindLogToCollectionIfMissing', () => {
      it('should add a BitcoindLog to an empty array', () => {
        const bitcoindLog: IBitcoindLog = { id: 123 };
        expectedResult = service.addBitcoindLogToCollectionIfMissing([], bitcoindLog);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bitcoindLog);
      });

      it('should not add a BitcoindLog to an array that contains it', () => {
        const bitcoindLog: IBitcoindLog = { id: 123 };
        const bitcoindLogCollection: IBitcoindLog[] = [
          {
            ...bitcoindLog,
          },
          { id: 456 },
        ];
        expectedResult = service.addBitcoindLogToCollectionIfMissing(bitcoindLogCollection, bitcoindLog);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BitcoindLog to an array that doesn't contain it", () => {
        const bitcoindLog: IBitcoindLog = { id: 123 };
        const bitcoindLogCollection: IBitcoindLog[] = [{ id: 456 }];
        expectedResult = service.addBitcoindLogToCollectionIfMissing(bitcoindLogCollection, bitcoindLog);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bitcoindLog);
      });

      it('should add only unique BitcoindLog to an array', () => {
        const bitcoindLogArray: IBitcoindLog[] = [{ id: 123 }, { id: 456 }, { id: 71979 }];
        const bitcoindLogCollection: IBitcoindLog[] = [{ id: 123 }];
        expectedResult = service.addBitcoindLogToCollectionIfMissing(bitcoindLogCollection, ...bitcoindLogArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bitcoindLog: IBitcoindLog = { id: 123 };
        const bitcoindLog2: IBitcoindLog = { id: 456 };
        expectedResult = service.addBitcoindLogToCollectionIfMissing([], bitcoindLog, bitcoindLog2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bitcoindLog);
        expect(expectedResult).toContain(bitcoindLog2);
      });

      it('should accept null and undefined values', () => {
        const bitcoindLog: IBitcoindLog = { id: 123 };
        expectedResult = service.addBitcoindLogToCollectionIfMissing([], null, bitcoindLog, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bitcoindLog);
      });

      it('should return initial array if no BitcoindLog is added', () => {
        const bitcoindLogCollection: IBitcoindLog[] = [{ id: 123 }];
        expectedResult = service.addBitcoindLogToCollectionIfMissing(bitcoindLogCollection, undefined, null);
        expect(expectedResult).toEqual(bitcoindLogCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
