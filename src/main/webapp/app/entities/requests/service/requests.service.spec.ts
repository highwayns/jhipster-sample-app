import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { YesNo } from 'app/entities/enumerations/yes-no.model';
import { IRequests, Requests } from '../requests.model';

import { RequestsService } from './requests.service';

describe('Requests Service', () => {
  let service: RequestsService;
  let httpMock: HttpTestingController;
  let elemDefault: IRequests;
  let expectedResult: IRequests | IRequests[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RequestsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      date: currentDate,
      amount: 0,
      addressId: 0,
      account: 0,
      sendAddress: 'AAAAAAA',
      transactionId: 'AAAAAAA',
      increment: 0,
      done: YesNo.Y,
      cryptoId: 0,
      fee: 0,
      netAmount: 0,
      notified: 0,
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

    it('should create a Requests', () => {
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

      service.create(new Requests()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Requests', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
          amount: 1,
          addressId: 1,
          account: 1,
          sendAddress: 'BBBBBB',
          transactionId: 'BBBBBB',
          increment: 1,
          done: 'BBBBBB',
          cryptoId: 1,
          fee: 1,
          netAmount: 1,
          notified: 1,
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

    it('should partial update a Requests', () => {
      const patchObject = Object.assign(
        {
          date: currentDate.format(DATE_TIME_FORMAT),
          amount: 1,
          addressId: 1,
          account: 1,
          increment: 1,
          done: 'BBBBBB',
          cryptoId: 1,
          fee: 1,
          netAmount: 1,
          notified: 1,
        },
        new Requests()
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

    it('should return a list of Requests', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
          amount: 1,
          addressId: 1,
          account: 1,
          sendAddress: 'BBBBBB',
          transactionId: 'BBBBBB',
          increment: 1,
          done: 'BBBBBB',
          cryptoId: 1,
          fee: 1,
          netAmount: 1,
          notified: 1,
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

    it('should delete a Requests', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRequestsToCollectionIfMissing', () => {
      it('should add a Requests to an empty array', () => {
        const requests: IRequests = { id: 123 };
        expectedResult = service.addRequestsToCollectionIfMissing([], requests);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requests);
      });

      it('should not add a Requests to an array that contains it', () => {
        const requests: IRequests = { id: 123 };
        const requestsCollection: IRequests[] = [
          {
            ...requests,
          },
          { id: 456 },
        ];
        expectedResult = service.addRequestsToCollectionIfMissing(requestsCollection, requests);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Requests to an array that doesn't contain it", () => {
        const requests: IRequests = { id: 123 };
        const requestsCollection: IRequests[] = [{ id: 456 }];
        expectedResult = service.addRequestsToCollectionIfMissing(requestsCollection, requests);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requests);
      });

      it('should add only unique Requests to an array', () => {
        const requestsArray: IRequests[] = [{ id: 123 }, { id: 456 }, { id: 47951 }];
        const requestsCollection: IRequests[] = [{ id: 123 }];
        expectedResult = service.addRequestsToCollectionIfMissing(requestsCollection, ...requestsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const requests: IRequests = { id: 123 };
        const requests2: IRequests = { id: 456 };
        expectedResult = service.addRequestsToCollectionIfMissing([], requests, requests2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requests);
        expect(expectedResult).toContain(requests2);
      });

      it('should accept null and undefined values', () => {
        const requests: IRequests = { id: 123 };
        expectedResult = service.addRequestsToCollectionIfMissing([], null, requests, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requests);
      });

      it('should return initial array if no Requests is added', () => {
        const requestsCollection: IRequests[] = [{ id: 123 }];
        expectedResult = service.addRequestsToCollectionIfMissing(requestsCollection, undefined, null);
        expect(expectedResult).toEqual(requestsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
