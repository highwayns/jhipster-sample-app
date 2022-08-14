import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAdminGroupsTabs, AdminGroupsTabs } from '../admin-groups-tabs.model';

import { AdminGroupsTabsService } from './admin-groups-tabs.service';

describe('AdminGroupsTabs Service', () => {
  let service: AdminGroupsTabsService;
  let httpMock: HttpTestingController;
  let elemDefault: IAdminGroupsTabs;
  let expectedResult: IAdminGroupsTabs | IAdminGroupsTabs[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AdminGroupsTabsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      permission: false,
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

    it('should create a AdminGroupsTabs', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AdminGroupsTabs()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AdminGroupsTabs', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          permission: true,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AdminGroupsTabs', () => {
      const patchObject = Object.assign({}, new AdminGroupsTabs());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AdminGroupsTabs', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          permission: true,
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

    it('should delete a AdminGroupsTabs', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAdminGroupsTabsToCollectionIfMissing', () => {
      it('should add a AdminGroupsTabs to an empty array', () => {
        const adminGroupsTabs: IAdminGroupsTabs = { id: 123 };
        expectedResult = service.addAdminGroupsTabsToCollectionIfMissing([], adminGroupsTabs);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminGroupsTabs);
      });

      it('should not add a AdminGroupsTabs to an array that contains it', () => {
        const adminGroupsTabs: IAdminGroupsTabs = { id: 123 };
        const adminGroupsTabsCollection: IAdminGroupsTabs[] = [
          {
            ...adminGroupsTabs,
          },
          { id: 456 },
        ];
        expectedResult = service.addAdminGroupsTabsToCollectionIfMissing(adminGroupsTabsCollection, adminGroupsTabs);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AdminGroupsTabs to an array that doesn't contain it", () => {
        const adminGroupsTabs: IAdminGroupsTabs = { id: 123 };
        const adminGroupsTabsCollection: IAdminGroupsTabs[] = [{ id: 456 }];
        expectedResult = service.addAdminGroupsTabsToCollectionIfMissing(adminGroupsTabsCollection, adminGroupsTabs);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminGroupsTabs);
      });

      it('should add only unique AdminGroupsTabs to an array', () => {
        const adminGroupsTabsArray: IAdminGroupsTabs[] = [{ id: 123 }, { id: 456 }, { id: 84838 }];
        const adminGroupsTabsCollection: IAdminGroupsTabs[] = [{ id: 123 }];
        expectedResult = service.addAdminGroupsTabsToCollectionIfMissing(adminGroupsTabsCollection, ...adminGroupsTabsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const adminGroupsTabs: IAdminGroupsTabs = { id: 123 };
        const adminGroupsTabs2: IAdminGroupsTabs = { id: 456 };
        expectedResult = service.addAdminGroupsTabsToCollectionIfMissing([], adminGroupsTabs, adminGroupsTabs2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminGroupsTabs);
        expect(expectedResult).toContain(adminGroupsTabs2);
      });

      it('should accept null and undefined values', () => {
        const adminGroupsTabs: IAdminGroupsTabs = { id: 123 };
        expectedResult = service.addAdminGroupsTabsToCollectionIfMissing([], null, adminGroupsTabs, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminGroupsTabs);
      });

      it('should return initial array if no AdminGroupsTabs is added', () => {
        const adminGroupsTabsCollection: IAdminGroupsTabs[] = [{ id: 123 }];
        expectedResult = service.addAdminGroupsTabsToCollectionIfMissing(adminGroupsTabsCollection, undefined, null);
        expect(expectedResult).toEqual(adminGroupsTabsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
