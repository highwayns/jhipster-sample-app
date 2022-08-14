import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAdminGroups, AdminGroups } from '../admin-groups.model';

import { AdminGroupsService } from './admin-groups.service';

describe('AdminGroups Service', () => {
  let service: AdminGroupsService;
  let httpMock: HttpTestingController;
  let elemDefault: IAdminGroups;
  let expectedResult: IAdminGroups | IAdminGroups[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AdminGroupsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      order: 0,
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

    it('should create a AdminGroups', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AdminGroups()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AdminGroups', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          order: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AdminGroups', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
        },
        new AdminGroups()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AdminGroups', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          order: 1,
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

    it('should delete a AdminGroups', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAdminGroupsToCollectionIfMissing', () => {
      it('should add a AdminGroups to an empty array', () => {
        const adminGroups: IAdminGroups = { id: 123 };
        expectedResult = service.addAdminGroupsToCollectionIfMissing([], adminGroups);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminGroups);
      });

      it('should not add a AdminGroups to an array that contains it', () => {
        const adminGroups: IAdminGroups = { id: 123 };
        const adminGroupsCollection: IAdminGroups[] = [
          {
            ...adminGroups,
          },
          { id: 456 },
        ];
        expectedResult = service.addAdminGroupsToCollectionIfMissing(adminGroupsCollection, adminGroups);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AdminGroups to an array that doesn't contain it", () => {
        const adminGroups: IAdminGroups = { id: 123 };
        const adminGroupsCollection: IAdminGroups[] = [{ id: 456 }];
        expectedResult = service.addAdminGroupsToCollectionIfMissing(adminGroupsCollection, adminGroups);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminGroups);
      });

      it('should add only unique AdminGroups to an array', () => {
        const adminGroupsArray: IAdminGroups[] = [{ id: 123 }, { id: 456 }, { id: 8709 }];
        const adminGroupsCollection: IAdminGroups[] = [{ id: 123 }];
        expectedResult = service.addAdminGroupsToCollectionIfMissing(adminGroupsCollection, ...adminGroupsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const adminGroups: IAdminGroups = { id: 123 };
        const adminGroups2: IAdminGroups = { id: 456 };
        expectedResult = service.addAdminGroupsToCollectionIfMissing([], adminGroups, adminGroups2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminGroups);
        expect(expectedResult).toContain(adminGroups2);
      });

      it('should accept null and undefined values', () => {
        const adminGroups: IAdminGroups = { id: 123 };
        expectedResult = service.addAdminGroupsToCollectionIfMissing([], null, adminGroups, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminGroups);
      });

      it('should return initial array if no AdminGroups is added', () => {
        const adminGroupsCollection: IAdminGroups[] = [{ id: 123 }];
        expectedResult = service.addAdminGroupsToCollectionIfMissing(adminGroupsCollection, undefined, null);
        expect(expectedResult).toEqual(adminGroupsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
