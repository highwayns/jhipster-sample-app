import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAdminGroupsPages, AdminGroupsPages } from '../admin-groups-pages.model';

import { AdminGroupsPagesService } from './admin-groups-pages.service';

describe('AdminGroupsPages Service', () => {
  let service: AdminGroupsPagesService;
  let httpMock: HttpTestingController;
  let elemDefault: IAdminGroupsPages;
  let expectedResult: IAdminGroupsPages | IAdminGroupsPages[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AdminGroupsPagesService);
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

    it('should create a AdminGroupsPages', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AdminGroupsPages()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AdminGroupsPages', () => {
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

    it('should partial update a AdminGroupsPages', () => {
      const patchObject = Object.assign({}, new AdminGroupsPages());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AdminGroupsPages', () => {
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

    it('should delete a AdminGroupsPages', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAdminGroupsPagesToCollectionIfMissing', () => {
      it('should add a AdminGroupsPages to an empty array', () => {
        const adminGroupsPages: IAdminGroupsPages = { id: 123 };
        expectedResult = service.addAdminGroupsPagesToCollectionIfMissing([], adminGroupsPages);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminGroupsPages);
      });

      it('should not add a AdminGroupsPages to an array that contains it', () => {
        const adminGroupsPages: IAdminGroupsPages = { id: 123 };
        const adminGroupsPagesCollection: IAdminGroupsPages[] = [
          {
            ...adminGroupsPages,
          },
          { id: 456 },
        ];
        expectedResult = service.addAdminGroupsPagesToCollectionIfMissing(adminGroupsPagesCollection, adminGroupsPages);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AdminGroupsPages to an array that doesn't contain it", () => {
        const adminGroupsPages: IAdminGroupsPages = { id: 123 };
        const adminGroupsPagesCollection: IAdminGroupsPages[] = [{ id: 456 }];
        expectedResult = service.addAdminGroupsPagesToCollectionIfMissing(adminGroupsPagesCollection, adminGroupsPages);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminGroupsPages);
      });

      it('should add only unique AdminGroupsPages to an array', () => {
        const adminGroupsPagesArray: IAdminGroupsPages[] = [{ id: 123 }, { id: 456 }, { id: 43790 }];
        const adminGroupsPagesCollection: IAdminGroupsPages[] = [{ id: 123 }];
        expectedResult = service.addAdminGroupsPagesToCollectionIfMissing(adminGroupsPagesCollection, ...adminGroupsPagesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const adminGroupsPages: IAdminGroupsPages = { id: 123 };
        const adminGroupsPages2: IAdminGroupsPages = { id: 456 };
        expectedResult = service.addAdminGroupsPagesToCollectionIfMissing([], adminGroupsPages, adminGroupsPages2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminGroupsPages);
        expect(expectedResult).toContain(adminGroupsPages2);
      });

      it('should accept null and undefined values', () => {
        const adminGroupsPages: IAdminGroupsPages = { id: 123 };
        expectedResult = service.addAdminGroupsPagesToCollectionIfMissing([], null, adminGroupsPages, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminGroupsPages);
      });

      it('should return initial array if no AdminGroupsPages is added', () => {
        const adminGroupsPagesCollection: IAdminGroupsPages[] = [{ id: 123 }];
        expectedResult = service.addAdminGroupsPagesToCollectionIfMissing(adminGroupsPagesCollection, undefined, null);
        expect(expectedResult).toEqual(adminGroupsPagesCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
