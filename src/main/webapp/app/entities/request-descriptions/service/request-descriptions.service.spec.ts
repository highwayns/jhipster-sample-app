import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRequestDescriptions, RequestDescriptions } from '../request-descriptions.model';

import { RequestDescriptionsService } from './request-descriptions.service';

describe('RequestDescriptions Service', () => {
  let service: RequestDescriptionsService;
  let httpMock: HttpTestingController;
  let elemDefault: IRequestDescriptions;
  let expectedResult: IRequestDescriptions | IRequestDescriptions[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RequestDescriptionsService);
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

    it('should create a RequestDescriptions', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new RequestDescriptions()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RequestDescriptions', () => {
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

    it('should partial update a RequestDescriptions', () => {
      const patchObject = Object.assign(
        {
          nameEs: 'BBBBBB',
          nameRu: 'BBBBBB',
          nameZh: 'BBBBBB',
        },
        new RequestDescriptions()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RequestDescriptions', () => {
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

    it('should delete a RequestDescriptions', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRequestDescriptionsToCollectionIfMissing', () => {
      it('should add a RequestDescriptions to an empty array', () => {
        const requestDescriptions: IRequestDescriptions = { id: 123 };
        expectedResult = service.addRequestDescriptionsToCollectionIfMissing([], requestDescriptions);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requestDescriptions);
      });

      it('should not add a RequestDescriptions to an array that contains it', () => {
        const requestDescriptions: IRequestDescriptions = { id: 123 };
        const requestDescriptionsCollection: IRequestDescriptions[] = [
          {
            ...requestDescriptions,
          },
          { id: 456 },
        ];
        expectedResult = service.addRequestDescriptionsToCollectionIfMissing(requestDescriptionsCollection, requestDescriptions);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RequestDescriptions to an array that doesn't contain it", () => {
        const requestDescriptions: IRequestDescriptions = { id: 123 };
        const requestDescriptionsCollection: IRequestDescriptions[] = [{ id: 456 }];
        expectedResult = service.addRequestDescriptionsToCollectionIfMissing(requestDescriptionsCollection, requestDescriptions);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requestDescriptions);
      });

      it('should add only unique RequestDescriptions to an array', () => {
        const requestDescriptionsArray: IRequestDescriptions[] = [{ id: 123 }, { id: 456 }, { id: 32317 }];
        const requestDescriptionsCollection: IRequestDescriptions[] = [{ id: 123 }];
        expectedResult = service.addRequestDescriptionsToCollectionIfMissing(requestDescriptionsCollection, ...requestDescriptionsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const requestDescriptions: IRequestDescriptions = { id: 123 };
        const requestDescriptions2: IRequestDescriptions = { id: 456 };
        expectedResult = service.addRequestDescriptionsToCollectionIfMissing([], requestDescriptions, requestDescriptions2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requestDescriptions);
        expect(expectedResult).toContain(requestDescriptions2);
      });

      it('should accept null and undefined values', () => {
        const requestDescriptions: IRequestDescriptions = { id: 123 };
        expectedResult = service.addRequestDescriptionsToCollectionIfMissing([], null, requestDescriptions, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requestDescriptions);
      });

      it('should return initial array if no RequestDescriptions is added', () => {
        const requestDescriptionsCollection: IRequestDescriptions[] = [{ id: 123 }];
        expectedResult = service.addRequestDescriptionsToCollectionIfMissing(requestDescriptionsCollection, undefined, null);
        expect(expectedResult).toEqual(requestDescriptionsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
