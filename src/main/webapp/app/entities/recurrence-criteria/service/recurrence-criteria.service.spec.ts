import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { RecurrenceType } from 'app/entities/enumerations/recurrence-type.model';
import { IRecurrenceCriteria, RecurrenceCriteria } from '../recurrence-criteria.model';

import { RecurrenceCriteriaService } from './recurrence-criteria.service';

describe('RecurrenceCriteria Service', () => {
  let service: RecurrenceCriteriaService;
  let httpMock: HttpTestingController;
  let elemDefault: IRecurrenceCriteria;
  let expectedResult: IRecurrenceCriteria | IRecurrenceCriteria[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RecurrenceCriteriaService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      recurrenceType: RecurrenceType.SUBSCRIPTION,
      recurringExpiry: currentDate,
      recurringFrequency: 0,
      instalments: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          recurringExpiry: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a RecurrenceCriteria', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          recurringExpiry: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          recurringExpiry: currentDate,
        },
        returnedFromService
      );

      service.create(new RecurrenceCriteria()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RecurrenceCriteria', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          recurrenceType: 'BBBBBB',
          recurringExpiry: currentDate.format(DATE_TIME_FORMAT),
          recurringFrequency: 1,
          instalments: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          recurringExpiry: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RecurrenceCriteria', () => {
      const patchObject = Object.assign(
        {
          recurrenceType: 'BBBBBB',
        },
        new RecurrenceCriteria()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          recurringExpiry: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RecurrenceCriteria', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          recurrenceType: 'BBBBBB',
          recurringExpiry: currentDate.format(DATE_TIME_FORMAT),
          recurringFrequency: 1,
          instalments: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          recurringExpiry: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a RecurrenceCriteria', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRecurrenceCriteriaToCollectionIfMissing', () => {
      it('should add a RecurrenceCriteria to an empty array', () => {
        const recurrenceCriteria: IRecurrenceCriteria = { id: 123 };
        expectedResult = service.addRecurrenceCriteriaToCollectionIfMissing([], recurrenceCriteria);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(recurrenceCriteria);
      });

      it('should not add a RecurrenceCriteria to an array that contains it', () => {
        const recurrenceCriteria: IRecurrenceCriteria = { id: 123 };
        const recurrenceCriteriaCollection: IRecurrenceCriteria[] = [
          {
            ...recurrenceCriteria,
          },
          { id: 456 },
        ];
        expectedResult = service.addRecurrenceCriteriaToCollectionIfMissing(recurrenceCriteriaCollection, recurrenceCriteria);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RecurrenceCriteria to an array that doesn't contain it", () => {
        const recurrenceCriteria: IRecurrenceCriteria = { id: 123 };
        const recurrenceCriteriaCollection: IRecurrenceCriteria[] = [{ id: 456 }];
        expectedResult = service.addRecurrenceCriteriaToCollectionIfMissing(recurrenceCriteriaCollection, recurrenceCriteria);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(recurrenceCriteria);
      });

      it('should add only unique RecurrenceCriteria to an array', () => {
        const recurrenceCriteriaArray: IRecurrenceCriteria[] = [{ id: 123 }, { id: 456 }, { id: 50637 }];
        const recurrenceCriteriaCollection: IRecurrenceCriteria[] = [{ id: 123 }];
        expectedResult = service.addRecurrenceCriteriaToCollectionIfMissing(recurrenceCriteriaCollection, ...recurrenceCriteriaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const recurrenceCriteria: IRecurrenceCriteria = { id: 123 };
        const recurrenceCriteria2: IRecurrenceCriteria = { id: 456 };
        expectedResult = service.addRecurrenceCriteriaToCollectionIfMissing([], recurrenceCriteria, recurrenceCriteria2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(recurrenceCriteria);
        expect(expectedResult).toContain(recurrenceCriteria2);
      });

      it('should accept null and undefined values', () => {
        const recurrenceCriteria: IRecurrenceCriteria = { id: 123 };
        expectedResult = service.addRecurrenceCriteriaToCollectionIfMissing([], null, recurrenceCriteria, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(recurrenceCriteria);
      });

      it('should return initial array if no RecurrenceCriteria is added', () => {
        const recurrenceCriteriaCollection: IRecurrenceCriteria[] = [{ id: 123 }];
        expectedResult = service.addRecurrenceCriteriaToCollectionIfMissing(recurrenceCriteriaCollection, undefined, null);
        expect(expectedResult).toEqual(recurrenceCriteriaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
