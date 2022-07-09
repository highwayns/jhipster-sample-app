import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAbuseTrigger, AbuseTrigger } from '../abuse-trigger.model';

import { AbuseTriggerService } from './abuse-trigger.service';

describe('AbuseTrigger Service', () => {
  let service: AbuseTriggerService;
  let httpMock: HttpTestingController;
  let elemDefault: IAbuseTrigger;
  let expectedResult: IAbuseTrigger | IAbuseTrigger[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AbuseTriggerService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      score: 0,
      code: 'AAAAAAA',
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

    it('should create a AbuseTrigger', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AbuseTrigger()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AbuseTrigger', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          score: 1,
          code: 'BBBBBB',
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

    it('should partial update a AbuseTrigger', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          description: 'BBBBBB',
        },
        new AbuseTrigger()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AbuseTrigger', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          score: 1,
          code: 'BBBBBB',
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

    it('should delete a AbuseTrigger', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAbuseTriggerToCollectionIfMissing', () => {
      it('should add a AbuseTrigger to an empty array', () => {
        const abuseTrigger: IAbuseTrigger = { id: 123 };
        expectedResult = service.addAbuseTriggerToCollectionIfMissing([], abuseTrigger);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(abuseTrigger);
      });

      it('should not add a AbuseTrigger to an array that contains it', () => {
        const abuseTrigger: IAbuseTrigger = { id: 123 };
        const abuseTriggerCollection: IAbuseTrigger[] = [
          {
            ...abuseTrigger,
          },
          { id: 456 },
        ];
        expectedResult = service.addAbuseTriggerToCollectionIfMissing(abuseTriggerCollection, abuseTrigger);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AbuseTrigger to an array that doesn't contain it", () => {
        const abuseTrigger: IAbuseTrigger = { id: 123 };
        const abuseTriggerCollection: IAbuseTrigger[] = [{ id: 456 }];
        expectedResult = service.addAbuseTriggerToCollectionIfMissing(abuseTriggerCollection, abuseTrigger);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(abuseTrigger);
      });

      it('should add only unique AbuseTrigger to an array', () => {
        const abuseTriggerArray: IAbuseTrigger[] = [{ id: 123 }, { id: 456 }, { id: 85237 }];
        const abuseTriggerCollection: IAbuseTrigger[] = [{ id: 123 }];
        expectedResult = service.addAbuseTriggerToCollectionIfMissing(abuseTriggerCollection, ...abuseTriggerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const abuseTrigger: IAbuseTrigger = { id: 123 };
        const abuseTrigger2: IAbuseTrigger = { id: 456 };
        expectedResult = service.addAbuseTriggerToCollectionIfMissing([], abuseTrigger, abuseTrigger2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(abuseTrigger);
        expect(expectedResult).toContain(abuseTrigger2);
      });

      it('should accept null and undefined values', () => {
        const abuseTrigger: IAbuseTrigger = { id: 123 };
        expectedResult = service.addAbuseTriggerToCollectionIfMissing([], null, abuseTrigger, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(abuseTrigger);
      });

      it('should return initial array if no AbuseTrigger is added', () => {
        const abuseTriggerCollection: IAbuseTrigger[] = [{ id: 123 }];
        expectedResult = service.addAbuseTriggerToCollectionIfMissing(abuseTriggerCollection, undefined, null);
        expect(expectedResult).toEqual(abuseTriggerCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
