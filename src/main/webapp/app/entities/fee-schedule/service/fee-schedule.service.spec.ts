import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFeeSchedule, FeeSchedule } from '../fee-schedule.model';

import { FeeScheduleService } from './fee-schedule.service';

describe('FeeSchedule Service', () => {
  let service: FeeScheduleService;
  let httpMock: HttpTestingController;
  let elemDefault: IFeeSchedule;
  let expectedResult: IFeeSchedule | IFeeSchedule[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FeeScheduleService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      fee: 0,
      fromUsd: 0,
      toUsd: 0,
      order: 0,
      fee1: 0,
      globalBtc: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a FeeSchedule', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new FeeSchedule()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FeeSchedule', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          fee: 1,
          fromUsd: 1,
          toUsd: 1,
          order: 1,
          fee1: 1,
          globalBtc: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FeeSchedule', () => {
      const patchObject = Object.assign(
        {
          fee: 1,
          toUsd: 1,
          order: 1,
          fee1: 1,
        },
        new FeeSchedule()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FeeSchedule', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          fee: 1,
          fromUsd: 1,
          toUsd: 1,
          order: 1,
          fee1: 1,
          globalBtc: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a FeeSchedule', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFeeScheduleToCollectionIfMissing', () => {
      it('should add a FeeSchedule to an empty array', () => {
        const feeSchedule: IFeeSchedule = { id: 123 };
        expectedResult = service.addFeeScheduleToCollectionIfMissing([], feeSchedule);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(feeSchedule);
      });

      it('should not add a FeeSchedule to an array that contains it', () => {
        const feeSchedule: IFeeSchedule = { id: 123 };
        const feeScheduleCollection: IFeeSchedule[] = [
          {
            ...feeSchedule,
          },
          { id: 456 },
        ];
        expectedResult = service.addFeeScheduleToCollectionIfMissing(feeScheduleCollection, feeSchedule);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FeeSchedule to an array that doesn't contain it", () => {
        const feeSchedule: IFeeSchedule = { id: 123 };
        const feeScheduleCollection: IFeeSchedule[] = [{ id: 456 }];
        expectedResult = service.addFeeScheduleToCollectionIfMissing(feeScheduleCollection, feeSchedule);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(feeSchedule);
      });

      it('should add only unique FeeSchedule to an array', () => {
        const feeScheduleArray: IFeeSchedule[] = [{ id: 123 }, { id: 456 }, { id: 77714 }];
        const feeScheduleCollection: IFeeSchedule[] = [{ id: 123 }];
        expectedResult = service.addFeeScheduleToCollectionIfMissing(feeScheduleCollection, ...feeScheduleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const feeSchedule: IFeeSchedule = { id: 123 };
        const feeSchedule2: IFeeSchedule = { id: 456 };
        expectedResult = service.addFeeScheduleToCollectionIfMissing([], feeSchedule, feeSchedule2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(feeSchedule);
        expect(expectedResult).toContain(feeSchedule2);
      });

      it('should accept null and undefined values', () => {
        const feeSchedule: IFeeSchedule = { id: 123 };
        expectedResult = service.addFeeScheduleToCollectionIfMissing([], null, feeSchedule, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(feeSchedule);
      });

      it('should return initial array if no FeeSchedule is added', () => {
        const feeScheduleCollection: IFeeSchedule[] = [{ id: 123 }];
        expectedResult = service.addFeeScheduleToCollectionIfMissing(feeScheduleCollection, undefined, null);
        expect(expectedResult).toEqual(feeScheduleCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
