import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAdminControlsMethods, AdminControlsMethods } from '../admin-controls-methods.model';

import { AdminControlsMethodsService } from './admin-controls-methods.service';

describe('AdminControlsMethods Service', () => {
  let service: AdminControlsMethodsService;
  let httpMock: HttpTestingController;
  let elemDefault: IAdminControlsMethods;
  let expectedResult: IAdminControlsMethods | IAdminControlsMethods[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AdminControlsMethodsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      method: 'AAAAAAA',
      argument: 'AAAAAAA',
      order: 0,
      pId: 0,
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

    it('should create a AdminControlsMethods', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AdminControlsMethods()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AdminControlsMethods', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          method: 'BBBBBB',
          argument: 'BBBBBB',
          order: 1,
          pId: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AdminControlsMethods', () => {
      const patchObject = Object.assign(
        {
          method: 'BBBBBB',
          pId: 1,
        },
        new AdminControlsMethods()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AdminControlsMethods', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          method: 'BBBBBB',
          argument: 'BBBBBB',
          order: 1,
          pId: 1,
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

    it('should delete a AdminControlsMethods', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAdminControlsMethodsToCollectionIfMissing', () => {
      it('should add a AdminControlsMethods to an empty array', () => {
        const adminControlsMethods: IAdminControlsMethods = { id: 123 };
        expectedResult = service.addAdminControlsMethodsToCollectionIfMissing([], adminControlsMethods);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminControlsMethods);
      });

      it('should not add a AdminControlsMethods to an array that contains it', () => {
        const adminControlsMethods: IAdminControlsMethods = { id: 123 };
        const adminControlsMethodsCollection: IAdminControlsMethods[] = [
          {
            ...adminControlsMethods,
          },
          { id: 456 },
        ];
        expectedResult = service.addAdminControlsMethodsToCollectionIfMissing(adminControlsMethodsCollection, adminControlsMethods);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AdminControlsMethods to an array that doesn't contain it", () => {
        const adminControlsMethods: IAdminControlsMethods = { id: 123 };
        const adminControlsMethodsCollection: IAdminControlsMethods[] = [{ id: 456 }];
        expectedResult = service.addAdminControlsMethodsToCollectionIfMissing(adminControlsMethodsCollection, adminControlsMethods);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminControlsMethods);
      });

      it('should add only unique AdminControlsMethods to an array', () => {
        const adminControlsMethodsArray: IAdminControlsMethods[] = [{ id: 123 }, { id: 456 }, { id: 46735 }];
        const adminControlsMethodsCollection: IAdminControlsMethods[] = [{ id: 123 }];
        expectedResult = service.addAdminControlsMethodsToCollectionIfMissing(adminControlsMethodsCollection, ...adminControlsMethodsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const adminControlsMethods: IAdminControlsMethods = { id: 123 };
        const adminControlsMethods2: IAdminControlsMethods = { id: 456 };
        expectedResult = service.addAdminControlsMethodsToCollectionIfMissing([], adminControlsMethods, adminControlsMethods2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminControlsMethods);
        expect(expectedResult).toContain(adminControlsMethods2);
      });

      it('should accept null and undefined values', () => {
        const adminControlsMethods: IAdminControlsMethods = { id: 123 };
        expectedResult = service.addAdminControlsMethodsToCollectionIfMissing([], null, adminControlsMethods, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminControlsMethods);
      });

      it('should return initial array if no AdminControlsMethods is added', () => {
        const adminControlsMethodsCollection: IAdminControlsMethods[] = [{ id: 123 }];
        expectedResult = service.addAdminControlsMethodsToCollectionIfMissing(adminControlsMethodsCollection, undefined, null);
        expect(expectedResult).toEqual(adminControlsMethodsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
