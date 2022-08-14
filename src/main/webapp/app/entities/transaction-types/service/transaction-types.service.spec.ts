import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITransactionTypes, TransactionTypes } from '../transaction-types.model';

import { TransactionTypesService } from './transaction-types.service';

describe('TransactionTypes Service', () => {
  let service: TransactionTypesService;
  let httpMock: HttpTestingController;
  let elemDefault: ITransactionTypes;
  let expectedResult: ITransactionTypes | ITransactionTypes[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TransactionTypesService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nameEn: 'AAAAAAA',
      nameEs: 'AAAAAAA',
      nameRu: 'AAAAAAA',
      nameZh: 'AAAAAAA',
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

    it('should create a TransactionTypes', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new TransactionTypes()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TransactionTypes', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nameEn: 'BBBBBB',
          nameEs: 'BBBBBB',
          nameRu: 'BBBBBB',
          nameZh: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TransactionTypes', () => {
      const patchObject = Object.assign(
        {
          nameEn: 'BBBBBB',
          nameEs: 'BBBBBB',
          nameRu: 'BBBBBB',
        },
        new TransactionTypes()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TransactionTypes', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nameEn: 'BBBBBB',
          nameEs: 'BBBBBB',
          nameRu: 'BBBBBB',
          nameZh: 'BBBBBB',
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

    it('should delete a TransactionTypes', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTransactionTypesToCollectionIfMissing', () => {
      it('should add a TransactionTypes to an empty array', () => {
        const transactionTypes: ITransactionTypes = { id: 123 };
        expectedResult = service.addTransactionTypesToCollectionIfMissing([], transactionTypes);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transactionTypes);
      });

      it('should not add a TransactionTypes to an array that contains it', () => {
        const transactionTypes: ITransactionTypes = { id: 123 };
        const transactionTypesCollection: ITransactionTypes[] = [
          {
            ...transactionTypes,
          },
          { id: 456 },
        ];
        expectedResult = service.addTransactionTypesToCollectionIfMissing(transactionTypesCollection, transactionTypes);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TransactionTypes to an array that doesn't contain it", () => {
        const transactionTypes: ITransactionTypes = { id: 123 };
        const transactionTypesCollection: ITransactionTypes[] = [{ id: 456 }];
        expectedResult = service.addTransactionTypesToCollectionIfMissing(transactionTypesCollection, transactionTypes);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transactionTypes);
      });

      it('should add only unique TransactionTypes to an array', () => {
        const transactionTypesArray: ITransactionTypes[] = [{ id: 123 }, { id: 456 }, { id: 23144 }];
        const transactionTypesCollection: ITransactionTypes[] = [{ id: 123 }];
        expectedResult = service.addTransactionTypesToCollectionIfMissing(transactionTypesCollection, ...transactionTypesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const transactionTypes: ITransactionTypes = { id: 123 };
        const transactionTypes2: ITransactionTypes = { id: 456 };
        expectedResult = service.addTransactionTypesToCollectionIfMissing([], transactionTypes, transactionTypes2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transactionTypes);
        expect(expectedResult).toContain(transactionTypes2);
      });

      it('should accept null and undefined values', () => {
        const transactionTypes: ITransactionTypes = { id: 123 };
        expectedResult = service.addTransactionTypesToCollectionIfMissing([], null, transactionTypes, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transactionTypes);
      });

      it('should return initial array if no TransactionTypes is added', () => {
        const transactionTypesCollection: ITransactionTypes[] = [{ id: 123 }];
        expectedResult = service.addTransactionTypesToCollectionIfMissing(transactionTypesCollection, undefined, null);
        expect(expectedResult).toEqual(transactionTypesCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
