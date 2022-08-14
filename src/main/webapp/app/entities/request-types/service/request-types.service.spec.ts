import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRequestTypes, RequestTypes } from '../request-types.model';

import { RequestTypesService } from './request-types.service';

describe('RequestTypes Service', () => {
  let service: RequestTypesService;
  let httpMock: HttpTestingController;
  let elemDefault: IRequestTypes;
  let expectedResult: IRequestTypes | IRequestTypes[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RequestTypesService);
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

    it('should create a RequestTypes', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new RequestTypes()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RequestTypes', () => {
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

    it('should partial update a RequestTypes', () => {
      const patchObject = Object.assign(
        {
          nameEn: 'BBBBBB',
          nameEs: 'BBBBBB',
          nameRu: 'BBBBBB',
          nameZh: 'BBBBBB',
        },
        new RequestTypes()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RequestTypes', () => {
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

    it('should delete a RequestTypes', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRequestTypesToCollectionIfMissing', () => {
      it('should add a RequestTypes to an empty array', () => {
        const requestTypes: IRequestTypes = { id: 123 };
        expectedResult = service.addRequestTypesToCollectionIfMissing([], requestTypes);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requestTypes);
      });

      it('should not add a RequestTypes to an array that contains it', () => {
        const requestTypes: IRequestTypes = { id: 123 };
        const requestTypesCollection: IRequestTypes[] = [
          {
            ...requestTypes,
          },
          { id: 456 },
        ];
        expectedResult = service.addRequestTypesToCollectionIfMissing(requestTypesCollection, requestTypes);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RequestTypes to an array that doesn't contain it", () => {
        const requestTypes: IRequestTypes = { id: 123 };
        const requestTypesCollection: IRequestTypes[] = [{ id: 456 }];
        expectedResult = service.addRequestTypesToCollectionIfMissing(requestTypesCollection, requestTypes);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requestTypes);
      });

      it('should add only unique RequestTypes to an array', () => {
        const requestTypesArray: IRequestTypes[] = [{ id: 123 }, { id: 456 }, { id: 91075 }];
        const requestTypesCollection: IRequestTypes[] = [{ id: 123 }];
        expectedResult = service.addRequestTypesToCollectionIfMissing(requestTypesCollection, ...requestTypesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const requestTypes: IRequestTypes = { id: 123 };
        const requestTypes2: IRequestTypes = { id: 456 };
        expectedResult = service.addRequestTypesToCollectionIfMissing([], requestTypes, requestTypes2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requestTypes);
        expect(expectedResult).toContain(requestTypes2);
      });

      it('should accept null and undefined values', () => {
        const requestTypes: IRequestTypes = { id: 123 };
        expectedResult = service.addRequestTypesToCollectionIfMissing([], null, requestTypes, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requestTypes);
      });

      it('should return initial array if no RequestTypes is added', () => {
        const requestTypesCollection: IRequestTypes[] = [{ id: 123 }];
        expectedResult = service.addRequestTypesToCollectionIfMissing(requestTypesCollection, undefined, null);
        expect(expectedResult).toEqual(requestTypesCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
