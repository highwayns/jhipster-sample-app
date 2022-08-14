import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBankAccounts, BankAccounts } from '../bank-accounts.model';

import { BankAccountsService } from './bank-accounts.service';

describe('BankAccounts Service', () => {
  let service: BankAccountsService;
  let httpMock: HttpTestingController;
  let elemDefault: IBankAccounts;
  let expectedResult: IBankAccounts | IBankAccounts[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BankAccountsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      accountNumber: 0,
      description: 'AAAAAAA',
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

    it('should create a BankAccounts', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new BankAccounts()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BankAccounts', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          accountNumber: 1,
          description: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BankAccounts', () => {
      const patchObject = Object.assign(
        {
          accountNumber: 1,
        },
        new BankAccounts()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BankAccounts', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          accountNumber: 1,
          description: 'BBBBBB',
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

    it('should delete a BankAccounts', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBankAccountsToCollectionIfMissing', () => {
      it('should add a BankAccounts to an empty array', () => {
        const bankAccounts: IBankAccounts = { id: 123 };
        expectedResult = service.addBankAccountsToCollectionIfMissing([], bankAccounts);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bankAccounts);
      });

      it('should not add a BankAccounts to an array that contains it', () => {
        const bankAccounts: IBankAccounts = { id: 123 };
        const bankAccountsCollection: IBankAccounts[] = [
          {
            ...bankAccounts,
          },
          { id: 456 },
        ];
        expectedResult = service.addBankAccountsToCollectionIfMissing(bankAccountsCollection, bankAccounts);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BankAccounts to an array that doesn't contain it", () => {
        const bankAccounts: IBankAccounts = { id: 123 };
        const bankAccountsCollection: IBankAccounts[] = [{ id: 456 }];
        expectedResult = service.addBankAccountsToCollectionIfMissing(bankAccountsCollection, bankAccounts);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bankAccounts);
      });

      it('should add only unique BankAccounts to an array', () => {
        const bankAccountsArray: IBankAccounts[] = [{ id: 123 }, { id: 456 }, { id: 71585 }];
        const bankAccountsCollection: IBankAccounts[] = [{ id: 123 }];
        expectedResult = service.addBankAccountsToCollectionIfMissing(bankAccountsCollection, ...bankAccountsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bankAccounts: IBankAccounts = { id: 123 };
        const bankAccounts2: IBankAccounts = { id: 456 };
        expectedResult = service.addBankAccountsToCollectionIfMissing([], bankAccounts, bankAccounts2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bankAccounts);
        expect(expectedResult).toContain(bankAccounts2);
      });

      it('should accept null and undefined values', () => {
        const bankAccounts: IBankAccounts = { id: 123 };
        expectedResult = service.addBankAccountsToCollectionIfMissing([], null, bankAccounts, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bankAccounts);
      });

      it('should return initial array if no BankAccounts is added', () => {
        const bankAccountsCollection: IBankAccounts[] = [{ id: 123 }];
        expectedResult = service.addBankAccountsToCollectionIfMissing(bankAccountsCollection, undefined, null);
        expect(expectedResult).toEqual(bankAccountsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
