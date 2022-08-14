import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { YesNo } from 'app/entities/enumerations/yes-no.model';
import { ICurrencies, Currencies } from '../currencies.model';

import { CurrenciesService } from './currencies.service';

describe('Currencies Service', () => {
  let service: CurrenciesService;
  let httpMock: HttpTestingController;
  let elemDefault: ICurrencies;
  let expectedResult: ICurrencies | ICurrencies[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CurrenciesService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      currency: 'AAAAAAA',
      faSymbol: 'AAAAAAA',
      accountNumber: 0,
      accountName: 'AAAAAAA',
      isActive: YesNo.Y,
      usdBid: 'AAAAAAA',
      usdAsk: 'AAAAAAA',
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

    it('should create a Currencies', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Currencies()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Currencies', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          currency: 'BBBBBB',
          faSymbol: 'BBBBBB',
          accountNumber: 1,
          accountName: 'BBBBBB',
          isActive: 'BBBBBB',
          usdBid: 'BBBBBB',
          usdAsk: 'BBBBBB',
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

    it('should partial update a Currencies', () => {
      const patchObject = Object.assign(
        {
          accountNumber: 1,
          accountName: 'BBBBBB',
          isActive: 'BBBBBB',
          usdAsk: 'BBBBBB',
          nameRu: 'BBBBBB',
        },
        new Currencies()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Currencies', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          currency: 'BBBBBB',
          faSymbol: 'BBBBBB',
          accountNumber: 1,
          accountName: 'BBBBBB',
          isActive: 'BBBBBB',
          usdBid: 'BBBBBB',
          usdAsk: 'BBBBBB',
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

    it('should delete a Currencies', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCurrenciesToCollectionIfMissing', () => {
      it('should add a Currencies to an empty array', () => {
        const currencies: ICurrencies = { id: 123 };
        expectedResult = service.addCurrenciesToCollectionIfMissing([], currencies);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(currencies);
      });

      it('should not add a Currencies to an array that contains it', () => {
        const currencies: ICurrencies = { id: 123 };
        const currenciesCollection: ICurrencies[] = [
          {
            ...currencies,
          },
          { id: 456 },
        ];
        expectedResult = service.addCurrenciesToCollectionIfMissing(currenciesCollection, currencies);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Currencies to an array that doesn't contain it", () => {
        const currencies: ICurrencies = { id: 123 };
        const currenciesCollection: ICurrencies[] = [{ id: 456 }];
        expectedResult = service.addCurrenciesToCollectionIfMissing(currenciesCollection, currencies);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(currencies);
      });

      it('should add only unique Currencies to an array', () => {
        const currenciesArray: ICurrencies[] = [{ id: 123 }, { id: 456 }, { id: 59937 }];
        const currenciesCollection: ICurrencies[] = [{ id: 123 }];
        expectedResult = service.addCurrenciesToCollectionIfMissing(currenciesCollection, ...currenciesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const currencies: ICurrencies = { id: 123 };
        const currencies2: ICurrencies = { id: 456 };
        expectedResult = service.addCurrenciesToCollectionIfMissing([], currencies, currencies2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(currencies);
        expect(expectedResult).toContain(currencies2);
      });

      it('should accept null and undefined values', () => {
        const currencies: ICurrencies = { id: 123 };
        expectedResult = service.addCurrenciesToCollectionIfMissing([], null, currencies, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(currencies);
      });

      it('should return initial array if no Currencies is added', () => {
        const currenciesCollection: ICurrencies[] = [{ id: 123 }];
        expectedResult = service.addCurrenciesToCollectionIfMissing(currenciesCollection, undefined, null);
        expect(expectedResult).toEqual(currenciesCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
