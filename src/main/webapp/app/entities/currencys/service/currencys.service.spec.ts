import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Currency } from 'app/entities/enumerations/currency.model';
import { ICurrencys, Currencys } from '../currencys.model';

import { CurrencysService } from './currencys.service';

describe('Currencys Service', () => {
  let service: CurrencysService;
  let httpMock: HttpTestingController;
  let elemDefault: ICurrencys;
  let expectedResult: ICurrencys | ICurrencys[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CurrencysService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      currency: Currency.CNY,
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

    it('should create a Currencys', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Currencys()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Currencys', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          currency: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Currencys', () => {
      const patchObject = Object.assign({}, new Currencys());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Currencys', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          currency: 'BBBBBB',
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

    it('should delete a Currencys', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCurrencysToCollectionIfMissing', () => {
      it('should add a Currencys to an empty array', () => {
        const currencys: ICurrencys = { id: 123 };
        expectedResult = service.addCurrencysToCollectionIfMissing([], currencys);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(currencys);
      });

      it('should not add a Currencys to an array that contains it', () => {
        const currencys: ICurrencys = { id: 123 };
        const currencysCollection: ICurrencys[] = [
          {
            ...currencys,
          },
          { id: 456 },
        ];
        expectedResult = service.addCurrencysToCollectionIfMissing(currencysCollection, currencys);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Currencys to an array that doesn't contain it", () => {
        const currencys: ICurrencys = { id: 123 };
        const currencysCollection: ICurrencys[] = [{ id: 456 }];
        expectedResult = service.addCurrencysToCollectionIfMissing(currencysCollection, currencys);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(currencys);
      });

      it('should add only unique Currencys to an array', () => {
        const currencysArray: ICurrencys[] = [{ id: 123 }, { id: 456 }, { id: 19445 }];
        const currencysCollection: ICurrencys[] = [{ id: 123 }];
        expectedResult = service.addCurrencysToCollectionIfMissing(currencysCollection, ...currencysArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const currencys: ICurrencys = { id: 123 };
        const currencys2: ICurrencys = { id: 456 };
        expectedResult = service.addCurrencysToCollectionIfMissing([], currencys, currencys2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(currencys);
        expect(expectedResult).toContain(currencys2);
      });

      it('should accept null and undefined values', () => {
        const currencys: ICurrencys = { id: 123 };
        expectedResult = service.addCurrencysToCollectionIfMissing([], null, currencys, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(currencys);
      });

      it('should return initial array if no Currencys is added', () => {
        const currencysCollection: ICurrencys[] = [{ id: 123 }];
        expectedResult = service.addCurrencysToCollectionIfMissing(currencysCollection, undefined, null);
        expect(expectedResult).toEqual(currencysCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
