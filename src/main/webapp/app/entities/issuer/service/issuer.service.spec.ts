import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IIssuer, Issuer } from '../issuer.model';

import { IssuerService } from './issuer.service';

describe('Issuer Service', () => {
  let service: IssuerService;
  let httpMock: HttpTestingController;
  let elemDefault: IIssuer;
  let expectedResult: IIssuer | IIssuer[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IssuerService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 'AAAAAAA',
      name: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Issuer', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Issuer()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Issuer', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          name: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Issuer', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
        },
        new Issuer()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Issuer', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          name: 'BBBBBB',
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

    it('should delete a Issuer', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addIssuerToCollectionIfMissing', () => {
      it('should add a Issuer to an empty array', () => {
        const issuer: IIssuer = { id: 'ABC' };
        expectedResult = service.addIssuerToCollectionIfMissing([], issuer);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(issuer);
      });

      it('should not add a Issuer to an array that contains it', () => {
        const issuer: IIssuer = { id: 'ABC' };
        const issuerCollection: IIssuer[] = [
          {
            ...issuer,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addIssuerToCollectionIfMissing(issuerCollection, issuer);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Issuer to an array that doesn't contain it", () => {
        const issuer: IIssuer = { id: 'ABC' };
        const issuerCollection: IIssuer[] = [{ id: 'CBA' }];
        expectedResult = service.addIssuerToCollectionIfMissing(issuerCollection, issuer);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(issuer);
      });

      it('should add only unique Issuer to an array', () => {
        const issuerArray: IIssuer[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: '30f2b8ce-55a8-4679-876b-5052604e089a' }];
        const issuerCollection: IIssuer[] = [{ id: 'ABC' }];
        expectedResult = service.addIssuerToCollectionIfMissing(issuerCollection, ...issuerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const issuer: IIssuer = { id: 'ABC' };
        const issuer2: IIssuer = { id: 'CBA' };
        expectedResult = service.addIssuerToCollectionIfMissing([], issuer, issuer2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(issuer);
        expect(expectedResult).toContain(issuer2);
      });

      it('should accept null and undefined values', () => {
        const issuer: IIssuer = { id: 'ABC' };
        expectedResult = service.addIssuerToCollectionIfMissing([], null, issuer, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(issuer);
      });

      it('should return initial array if no Issuer is added', () => {
        const issuerCollection: IIssuer[] = [{ id: 'ABC' }];
        expectedResult = service.addIssuerToCollectionIfMissing(issuerCollection, undefined, null);
        expect(expectedResult).toEqual(issuerCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
