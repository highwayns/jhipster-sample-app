import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IResultAttributes, ResultAttributes } from '../result-attributes.model';

import { ResultAttributesService } from './result-attributes.service';

describe('ResultAttributes Service', () => {
  let service: ResultAttributesService;
  let httpMock: HttpTestingController;
  let elemDefault: IResultAttributes;
  let expectedResult: IResultAttributes | IResultAttributes[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ResultAttributesService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      key: 'AAAAAAA',
      value: 'AAAAAAA',
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

    it('should create a ResultAttributes', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ResultAttributes()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResultAttributes', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          key: 'BBBBBB',
          value: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ResultAttributes', () => {
      const patchObject = Object.assign(
        {
          key: 'BBBBBB',
        },
        new ResultAttributes()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResultAttributes', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          key: 'BBBBBB',
          value: 'BBBBBB',
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

    it('should delete a ResultAttributes', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addResultAttributesToCollectionIfMissing', () => {
      it('should add a ResultAttributes to an empty array', () => {
        const resultAttributes: IResultAttributes = { id: 123 };
        expectedResult = service.addResultAttributesToCollectionIfMissing([], resultAttributes);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resultAttributes);
      });

      it('should not add a ResultAttributes to an array that contains it', () => {
        const resultAttributes: IResultAttributes = { id: 123 };
        const resultAttributesCollection: IResultAttributes[] = [
          {
            ...resultAttributes,
          },
          { id: 456 },
        ];
        expectedResult = service.addResultAttributesToCollectionIfMissing(resultAttributesCollection, resultAttributes);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResultAttributes to an array that doesn't contain it", () => {
        const resultAttributes: IResultAttributes = { id: 123 };
        const resultAttributesCollection: IResultAttributes[] = [{ id: 456 }];
        expectedResult = service.addResultAttributesToCollectionIfMissing(resultAttributesCollection, resultAttributes);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resultAttributes);
      });

      it('should add only unique ResultAttributes to an array', () => {
        const resultAttributesArray: IResultAttributes[] = [{ id: 123 }, { id: 456 }, { id: 13489 }];
        const resultAttributesCollection: IResultAttributes[] = [{ id: 123 }];
        expectedResult = service.addResultAttributesToCollectionIfMissing(resultAttributesCollection, ...resultAttributesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const resultAttributes: IResultAttributes = { id: 123 };
        const resultAttributes2: IResultAttributes = { id: 456 };
        expectedResult = service.addResultAttributesToCollectionIfMissing([], resultAttributes, resultAttributes2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resultAttributes);
        expect(expectedResult).toContain(resultAttributes2);
      });

      it('should accept null and undefined values', () => {
        const resultAttributes: IResultAttributes = { id: 123 };
        expectedResult = service.addResultAttributesToCollectionIfMissing([], null, resultAttributes, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resultAttributes);
      });

      it('should return initial array if no ResultAttributes is added', () => {
        const resultAttributesCollection: IResultAttributes[] = [{ id: 123 }];
        expectedResult = service.addResultAttributesToCollectionIfMissing(resultAttributesCollection, undefined, null);
        expect(expectedResult).toEqual(resultAttributesCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
