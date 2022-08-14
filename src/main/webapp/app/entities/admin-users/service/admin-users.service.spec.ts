import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { YesNo } from 'app/entities/enumerations/yes-no.model';
import { IAdminUsers, AdminUsers } from '../admin-users.model';

import { AdminUsersService } from './admin-users.service';

describe('AdminUsers Service', () => {
  let service: AdminUsersService;
  let httpMock: HttpTestingController;
  let elemDefault: IAdminUsers;
  let expectedResult: IAdminUsers | IAdminUsers[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AdminUsersService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      user: 'AAAAAAA',
      pass: 'AAAAAAA',
      firstName: 'AAAAAAA',
      lastName: 'AAAAAAA',
      company: 'AAAAAAA',
      address: 'AAAAAAA',
      city: 'AAAAAAA',
      phone: 'AAAAAAA',
      email: 'AAAAAAA',
      website: 'AAAAAAA',
      fId: 0,
      order: 0,
      isAdmin: YesNo.Y,
      countryCode: 0,
      verifiedAuthy: YesNo.Y,
      authyId: 'AAAAAAA',
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

    it('should create a AdminUsers', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AdminUsers()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AdminUsers', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          user: 'BBBBBB',
          pass: 'BBBBBB',
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          company: 'BBBBBB',
          address: 'BBBBBB',
          city: 'BBBBBB',
          phone: 'BBBBBB',
          email: 'BBBBBB',
          website: 'BBBBBB',
          fId: 1,
          order: 1,
          isAdmin: 'BBBBBB',
          countryCode: 1,
          verifiedAuthy: 'BBBBBB',
          authyId: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AdminUsers', () => {
      const patchObject = Object.assign(
        {
          lastName: 'BBBBBB',
          company: 'BBBBBB',
          address: 'BBBBBB',
          phone: 'BBBBBB',
          email: 'BBBBBB',
          website: 'BBBBBB',
          order: 1,
          isAdmin: 'BBBBBB',
          authyId: 'BBBBBB',
        },
        new AdminUsers()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AdminUsers', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          user: 'BBBBBB',
          pass: 'BBBBBB',
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          company: 'BBBBBB',
          address: 'BBBBBB',
          city: 'BBBBBB',
          phone: 'BBBBBB',
          email: 'BBBBBB',
          website: 'BBBBBB',
          fId: 1,
          order: 1,
          isAdmin: 'BBBBBB',
          countryCode: 1,
          verifiedAuthy: 'BBBBBB',
          authyId: 'BBBBBB',
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

    it('should delete a AdminUsers', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAdminUsersToCollectionIfMissing', () => {
      it('should add a AdminUsers to an empty array', () => {
        const adminUsers: IAdminUsers = { id: 123 };
        expectedResult = service.addAdminUsersToCollectionIfMissing([], adminUsers);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminUsers);
      });

      it('should not add a AdminUsers to an array that contains it', () => {
        const adminUsers: IAdminUsers = { id: 123 };
        const adminUsersCollection: IAdminUsers[] = [
          {
            ...adminUsers,
          },
          { id: 456 },
        ];
        expectedResult = service.addAdminUsersToCollectionIfMissing(adminUsersCollection, adminUsers);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AdminUsers to an array that doesn't contain it", () => {
        const adminUsers: IAdminUsers = { id: 123 };
        const adminUsersCollection: IAdminUsers[] = [{ id: 456 }];
        expectedResult = service.addAdminUsersToCollectionIfMissing(adminUsersCollection, adminUsers);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminUsers);
      });

      it('should add only unique AdminUsers to an array', () => {
        const adminUsersArray: IAdminUsers[] = [{ id: 123 }, { id: 456 }, { id: 33107 }];
        const adminUsersCollection: IAdminUsers[] = [{ id: 123 }];
        expectedResult = service.addAdminUsersToCollectionIfMissing(adminUsersCollection, ...adminUsersArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const adminUsers: IAdminUsers = { id: 123 };
        const adminUsers2: IAdminUsers = { id: 456 };
        expectedResult = service.addAdminUsersToCollectionIfMissing([], adminUsers, adminUsers2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminUsers);
        expect(expectedResult).toContain(adminUsers2);
      });

      it('should accept null and undefined values', () => {
        const adminUsers: IAdminUsers = { id: 123 };
        expectedResult = service.addAdminUsersToCollectionIfMissing([], null, adminUsers, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminUsers);
      });

      it('should return initial array if no AdminUsers is added', () => {
        const adminUsersCollection: IAdminUsers[] = [{ id: 123 }];
        expectedResult = service.addAdminUsersToCollectionIfMissing(adminUsersCollection, undefined, null);
        expect(expectedResult).toEqual(adminUsersCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
