import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IIsoCountries, IsoCountries } from '../iso-countries.model';

import { IsoCountriesService } from './iso-countries.service';

describe('IsoCountries Service', () => {
  let service: IsoCountriesService;
  let httpMock: HttpTestingController;
  let elemDefault: IIsoCountries;
  let expectedResult: IIsoCountries | IIsoCountries[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IsoCountriesService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      locale: 'AAAAAAA',
      code: 'AAAAAAA',
      name: 'AAAAAAA',
      prefix: 'AAAAAAA',
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

    it('should create a IsoCountries', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new IsoCountries()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a IsoCountries', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          locale: 'BBBBBB',
          code: 'BBBBBB',
          name: 'BBBBBB',
          prefix: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a IsoCountries', () => {
      const patchObject = Object.assign(
        {
          locale: 'BBBBBB',
          code: 'BBBBBB',
          name: 'BBBBBB',
          prefix: 'BBBBBB',
        },
        new IsoCountries()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of IsoCountries', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          locale: 'BBBBBB',
          code: 'BBBBBB',
          name: 'BBBBBB',
          prefix: 'BBBBBB',
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

    it('should delete a IsoCountries', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addIsoCountriesToCollectionIfMissing', () => {
      it('should add a IsoCountries to an empty array', () => {
        const isoCountries: IIsoCountries = { id: 123 };
        expectedResult = service.addIsoCountriesToCollectionIfMissing([], isoCountries);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(isoCountries);
      });

      it('should not add a IsoCountries to an array that contains it', () => {
        const isoCountries: IIsoCountries = { id: 123 };
        const isoCountriesCollection: IIsoCountries[] = [
          {
            ...isoCountries,
          },
          { id: 456 },
        ];
        expectedResult = service.addIsoCountriesToCollectionIfMissing(isoCountriesCollection, isoCountries);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a IsoCountries to an array that doesn't contain it", () => {
        const isoCountries: IIsoCountries = { id: 123 };
        const isoCountriesCollection: IIsoCountries[] = [{ id: 456 }];
        expectedResult = service.addIsoCountriesToCollectionIfMissing(isoCountriesCollection, isoCountries);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(isoCountries);
      });

      it('should add only unique IsoCountries to an array', () => {
        const isoCountriesArray: IIsoCountries[] = [{ id: 123 }, { id: 456 }, { id: 70004 }];
        const isoCountriesCollection: IIsoCountries[] = [{ id: 123 }];
        expectedResult = service.addIsoCountriesToCollectionIfMissing(isoCountriesCollection, ...isoCountriesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const isoCountries: IIsoCountries = { id: 123 };
        const isoCountries2: IIsoCountries = { id: 456 };
        expectedResult = service.addIsoCountriesToCollectionIfMissing([], isoCountries, isoCountries2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(isoCountries);
        expect(expectedResult).toContain(isoCountries2);
      });

      it('should accept null and undefined values', () => {
        const isoCountries: IIsoCountries = { id: 123 };
        expectedResult = service.addIsoCountriesToCollectionIfMissing([], null, isoCountries, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(isoCountries);
      });

      it('should return initial array if no IsoCountries is added', () => {
        const isoCountriesCollection: IIsoCountries[] = [{ id: 123 }];
        expectedResult = service.addIsoCountriesToCollectionIfMissing(isoCountriesCollection, undefined, null);
        expect(expectedResult).toEqual(isoCountriesCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
