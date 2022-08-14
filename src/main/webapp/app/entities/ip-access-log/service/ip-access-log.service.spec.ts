import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { YesNo } from 'app/entities/enumerations/yes-no.model';
import { IIpAccessLog, IpAccessLog } from '../ip-access-log.model';

import { IpAccessLogService } from './ip-access-log.service';

describe('IpAccessLog Service', () => {
  let service: IpAccessLogService;
  let httpMock: HttpTestingController;
  let elemDefault: IIpAccessLog;
  let expectedResult: IIpAccessLog | IIpAccessLog[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IpAccessLogService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      ip: 0,
      timestamp: currentDate,
      login: YesNo.Y,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          timestamp: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a IpAccessLog', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          timestamp: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          timestamp: currentDate,
        },
        returnedFromService
      );

      service.create(new IpAccessLog()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a IpAccessLog', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          ip: 1,
          timestamp: currentDate.format(DATE_TIME_FORMAT),
          login: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          timestamp: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a IpAccessLog', () => {
      const patchObject = Object.assign(
        {
          ip: 1,
          timestamp: currentDate.format(DATE_TIME_FORMAT),
          login: 'BBBBBB',
        },
        new IpAccessLog()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          timestamp: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of IpAccessLog', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          ip: 1,
          timestamp: currentDate.format(DATE_TIME_FORMAT),
          login: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          timestamp: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a IpAccessLog', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addIpAccessLogToCollectionIfMissing', () => {
      it('should add a IpAccessLog to an empty array', () => {
        const ipAccessLog: IIpAccessLog = { id: 123 };
        expectedResult = service.addIpAccessLogToCollectionIfMissing([], ipAccessLog);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ipAccessLog);
      });

      it('should not add a IpAccessLog to an array that contains it', () => {
        const ipAccessLog: IIpAccessLog = { id: 123 };
        const ipAccessLogCollection: IIpAccessLog[] = [
          {
            ...ipAccessLog,
          },
          { id: 456 },
        ];
        expectedResult = service.addIpAccessLogToCollectionIfMissing(ipAccessLogCollection, ipAccessLog);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a IpAccessLog to an array that doesn't contain it", () => {
        const ipAccessLog: IIpAccessLog = { id: 123 };
        const ipAccessLogCollection: IIpAccessLog[] = [{ id: 456 }];
        expectedResult = service.addIpAccessLogToCollectionIfMissing(ipAccessLogCollection, ipAccessLog);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ipAccessLog);
      });

      it('should add only unique IpAccessLog to an array', () => {
        const ipAccessLogArray: IIpAccessLog[] = [{ id: 123 }, { id: 456 }, { id: 56972 }];
        const ipAccessLogCollection: IIpAccessLog[] = [{ id: 123 }];
        expectedResult = service.addIpAccessLogToCollectionIfMissing(ipAccessLogCollection, ...ipAccessLogArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ipAccessLog: IIpAccessLog = { id: 123 };
        const ipAccessLog2: IIpAccessLog = { id: 456 };
        expectedResult = service.addIpAccessLogToCollectionIfMissing([], ipAccessLog, ipAccessLog2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ipAccessLog);
        expect(expectedResult).toContain(ipAccessLog2);
      });

      it('should accept null and undefined values', () => {
        const ipAccessLog: IIpAccessLog = { id: 123 };
        expectedResult = service.addIpAccessLogToCollectionIfMissing([], null, ipAccessLog, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ipAccessLog);
      });

      it('should return initial array if no IpAccessLog is added', () => {
        const ipAccessLogCollection: IIpAccessLog[] = [{ id: 123 }];
        expectedResult = service.addIpAccessLogToCollectionIfMissing(ipAccessLogCollection, undefined, null);
        expect(expectedResult).toEqual(ipAccessLogCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
