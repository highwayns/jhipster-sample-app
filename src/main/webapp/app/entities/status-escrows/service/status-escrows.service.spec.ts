import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IStatusEscrows, StatusEscrows } from '../status-escrows.model';

import { StatusEscrowsService } from './status-escrows.service';

describe('StatusEscrows Service', () => {
  let service: StatusEscrowsService;
  let httpMock: HttpTestingController;
  let elemDefault: IStatusEscrows;
  let expectedResult: IStatusEscrows | IStatusEscrows[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StatusEscrowsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      balance: 0,
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

    it('should create a StatusEscrows', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new StatusEscrows()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a StatusEscrows', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          balance: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a StatusEscrows', () => {
      const patchObject = Object.assign({}, new StatusEscrows());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of StatusEscrows', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          balance: 1,
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

    it('should delete a StatusEscrows', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addStatusEscrowsToCollectionIfMissing', () => {
      it('should add a StatusEscrows to an empty array', () => {
        const statusEscrows: IStatusEscrows = { id: 123 };
        expectedResult = service.addStatusEscrowsToCollectionIfMissing([], statusEscrows);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(statusEscrows);
      });

      it('should not add a StatusEscrows to an array that contains it', () => {
        const statusEscrows: IStatusEscrows = { id: 123 };
        const statusEscrowsCollection: IStatusEscrows[] = [
          {
            ...statusEscrows,
          },
          { id: 456 },
        ];
        expectedResult = service.addStatusEscrowsToCollectionIfMissing(statusEscrowsCollection, statusEscrows);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a StatusEscrows to an array that doesn't contain it", () => {
        const statusEscrows: IStatusEscrows = { id: 123 };
        const statusEscrowsCollection: IStatusEscrows[] = [{ id: 456 }];
        expectedResult = service.addStatusEscrowsToCollectionIfMissing(statusEscrowsCollection, statusEscrows);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(statusEscrows);
      });

      it('should add only unique StatusEscrows to an array', () => {
        const statusEscrowsArray: IStatusEscrows[] = [{ id: 123 }, { id: 456 }, { id: 80623 }];
        const statusEscrowsCollection: IStatusEscrows[] = [{ id: 123 }];
        expectedResult = service.addStatusEscrowsToCollectionIfMissing(statusEscrowsCollection, ...statusEscrowsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const statusEscrows: IStatusEscrows = { id: 123 };
        const statusEscrows2: IStatusEscrows = { id: 456 };
        expectedResult = service.addStatusEscrowsToCollectionIfMissing([], statusEscrows, statusEscrows2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(statusEscrows);
        expect(expectedResult).toContain(statusEscrows2);
      });

      it('should accept null and undefined values', () => {
        const statusEscrows: IStatusEscrows = { id: 123 };
        expectedResult = service.addStatusEscrowsToCollectionIfMissing([], null, statusEscrows, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(statusEscrows);
      });

      it('should return initial array if no StatusEscrows is added', () => {
        const statusEscrowsCollection: IStatusEscrows[] = [{ id: 123 }];
        expectedResult = service.addStatusEscrowsToCollectionIfMissing(statusEscrowsCollection, undefined, null);
        expect(expectedResult).toEqual(statusEscrowsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
