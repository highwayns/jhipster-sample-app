import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { YesNo } from 'app/entities/enumerations/yes-no.model';
import { IConversions, Conversions } from '../conversions.model';

import { ConversionsService } from './conversions.service';

describe('Conversions Service', () => {
  let service: ConversionsService;
  let httpMock: HttpTestingController;
  let elemDefault: IConversions;
  let expectedResult: IConversions | IConversions[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ConversionsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      amount: 0,
      date: currentDate,
      isActive: YesNo.Y,
      totalWithdrawals: 0,
      profitToFactor: 0,
      factored: YesNo.Y,
      date1: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          date: currentDate.format(DATE_TIME_FORMAT),
          date1: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Conversions', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          date: currentDate.format(DATE_TIME_FORMAT),
          date1: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
          date1: currentDate,
        },
        returnedFromService
      );

      service.create(new Conversions()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Conversions', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          amount: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
          isActive: 'BBBBBB',
          totalWithdrawals: 1,
          profitToFactor: 1,
          factored: 'BBBBBB',
          date1: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
          date1: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Conversions', () => {
      const patchObject = Object.assign(
        {
          amount: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
          date1: currentDate.format(DATE_TIME_FORMAT),
        },
        new Conversions()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          date: currentDate,
          date1: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Conversions', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          amount: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
          isActive: 'BBBBBB',
          totalWithdrawals: 1,
          profitToFactor: 1,
          factored: 'BBBBBB',
          date1: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
          date1: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Conversions', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addConversionsToCollectionIfMissing', () => {
      it('should add a Conversions to an empty array', () => {
        const conversions: IConversions = { id: 123 };
        expectedResult = service.addConversionsToCollectionIfMissing([], conversions);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(conversions);
      });

      it('should not add a Conversions to an array that contains it', () => {
        const conversions: IConversions = { id: 123 };
        const conversionsCollection: IConversions[] = [
          {
            ...conversions,
          },
          { id: 456 },
        ];
        expectedResult = service.addConversionsToCollectionIfMissing(conversionsCollection, conversions);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Conversions to an array that doesn't contain it", () => {
        const conversions: IConversions = { id: 123 };
        const conversionsCollection: IConversions[] = [{ id: 456 }];
        expectedResult = service.addConversionsToCollectionIfMissing(conversionsCollection, conversions);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(conversions);
      });

      it('should add only unique Conversions to an array', () => {
        const conversionsArray: IConversions[] = [{ id: 123 }, { id: 456 }, { id: 26355 }];
        const conversionsCollection: IConversions[] = [{ id: 123 }];
        expectedResult = service.addConversionsToCollectionIfMissing(conversionsCollection, ...conversionsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const conversions: IConversions = { id: 123 };
        const conversions2: IConversions = { id: 456 };
        expectedResult = service.addConversionsToCollectionIfMissing([], conversions, conversions2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(conversions);
        expect(expectedResult).toContain(conversions2);
      });

      it('should accept null and undefined values', () => {
        const conversions: IConversions = { id: 123 };
        expectedResult = service.addConversionsToCollectionIfMissing([], null, conversions, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(conversions);
      });

      it('should return initial array if no Conversions is added', () => {
        const conversionsCollection: IConversions[] = [{ id: 123 }];
        expectedResult = service.addConversionsToCollectionIfMissing(conversionsCollection, undefined, null);
        expect(expectedResult).toEqual(conversionsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
