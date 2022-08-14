import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { YesNo } from 'app/entities/enumerations/yes-no.model';
import { IApiKeys, ApiKeys } from '../api-keys.model';

import { ApiKeysService } from './api-keys.service';

describe('ApiKeys Service', () => {
  let service: ApiKeysService;
  let httpMock: HttpTestingController;
  let elemDefault: IApiKeys;
  let expectedResult: IApiKeys | IApiKeys[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ApiKeysService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      key: 'AAAAAAA',
      secret: 'AAAAAAA',
      view: YesNo.Y,
      orders: YesNo.Y,
      withdraw: YesNo.Y,
      nonce: 0,
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

    it('should create a ApiKeys', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ApiKeys()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ApiKeys', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          key: 'BBBBBB',
          secret: 'BBBBBB',
          view: 'BBBBBB',
          orders: 'BBBBBB',
          withdraw: 'BBBBBB',
          nonce: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ApiKeys', () => {
      const patchObject = Object.assign({}, new ApiKeys());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ApiKeys', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          key: 'BBBBBB',
          secret: 'BBBBBB',
          view: 'BBBBBB',
          orders: 'BBBBBB',
          withdraw: 'BBBBBB',
          nonce: 1,
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

    it('should delete a ApiKeys', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addApiKeysToCollectionIfMissing', () => {
      it('should add a ApiKeys to an empty array', () => {
        const apiKeys: IApiKeys = { id: 123 };
        expectedResult = service.addApiKeysToCollectionIfMissing([], apiKeys);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(apiKeys);
      });

      it('should not add a ApiKeys to an array that contains it', () => {
        const apiKeys: IApiKeys = { id: 123 };
        const apiKeysCollection: IApiKeys[] = [
          {
            ...apiKeys,
          },
          { id: 456 },
        ];
        expectedResult = service.addApiKeysToCollectionIfMissing(apiKeysCollection, apiKeys);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ApiKeys to an array that doesn't contain it", () => {
        const apiKeys: IApiKeys = { id: 123 };
        const apiKeysCollection: IApiKeys[] = [{ id: 456 }];
        expectedResult = service.addApiKeysToCollectionIfMissing(apiKeysCollection, apiKeys);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(apiKeys);
      });

      it('should add only unique ApiKeys to an array', () => {
        const apiKeysArray: IApiKeys[] = [{ id: 123 }, { id: 456 }, { id: 29140 }];
        const apiKeysCollection: IApiKeys[] = [{ id: 123 }];
        expectedResult = service.addApiKeysToCollectionIfMissing(apiKeysCollection, ...apiKeysArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const apiKeys: IApiKeys = { id: 123 };
        const apiKeys2: IApiKeys = { id: 456 };
        expectedResult = service.addApiKeysToCollectionIfMissing([], apiKeys, apiKeys2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(apiKeys);
        expect(expectedResult).toContain(apiKeys2);
      });

      it('should accept null and undefined values', () => {
        const apiKeys: IApiKeys = { id: 123 };
        expectedResult = service.addApiKeysToCollectionIfMissing([], null, apiKeys, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(apiKeys);
      });

      it('should return initial array if no ApiKeys is added', () => {
        const apiKeysCollection: IApiKeys[] = [{ id: 123 }];
        expectedResult = service.addApiKeysToCollectionIfMissing(apiKeysCollection, undefined, null);
        expect(expectedResult).toEqual(apiKeysCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
