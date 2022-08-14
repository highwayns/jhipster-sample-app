import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { YesNo } from 'app/entities/enumerations/yes-no.model';
import { IAdminPages, AdminPages } from '../admin-pages.model';

import { AdminPagesService } from './admin-pages.service';

describe('AdminPages Service', () => {
  let service: AdminPagesService;
  let httpMock: HttpTestingController;
  let elemDefault: IAdminPages;
  let expectedResult: IAdminPages | IAdminPages[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AdminPagesService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      fId: 0,
      name: 'AAAAAAA',
      url: 'AAAAAAA',
      icon: 'AAAAAAA',
      order: 0,
      pageMapReorders: false,
      oneRecord: YesNo.Y,
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

    it('should create a AdminPages', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AdminPages()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AdminPages', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          fId: 1,
          name: 'BBBBBB',
          url: 'BBBBBB',
          icon: 'BBBBBB',
          order: 1,
          pageMapReorders: true,
          oneRecord: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AdminPages', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          url: 'BBBBBB',
          icon: 'BBBBBB',
          order: 1,
        },
        new AdminPages()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AdminPages', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          fId: 1,
          name: 'BBBBBB',
          url: 'BBBBBB',
          icon: 'BBBBBB',
          order: 1,
          pageMapReorders: true,
          oneRecord: 'BBBBBB',
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

    it('should delete a AdminPages', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAdminPagesToCollectionIfMissing', () => {
      it('should add a AdminPages to an empty array', () => {
        const adminPages: IAdminPages = { id: 123 };
        expectedResult = service.addAdminPagesToCollectionIfMissing([], adminPages);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminPages);
      });

      it('should not add a AdminPages to an array that contains it', () => {
        const adminPages: IAdminPages = { id: 123 };
        const adminPagesCollection: IAdminPages[] = [
          {
            ...adminPages,
          },
          { id: 456 },
        ];
        expectedResult = service.addAdminPagesToCollectionIfMissing(adminPagesCollection, adminPages);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AdminPages to an array that doesn't contain it", () => {
        const adminPages: IAdminPages = { id: 123 };
        const adminPagesCollection: IAdminPages[] = [{ id: 456 }];
        expectedResult = service.addAdminPagesToCollectionIfMissing(adminPagesCollection, adminPages);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminPages);
      });

      it('should add only unique AdminPages to an array', () => {
        const adminPagesArray: IAdminPages[] = [{ id: 123 }, { id: 456 }, { id: 562 }];
        const adminPagesCollection: IAdminPages[] = [{ id: 123 }];
        expectedResult = service.addAdminPagesToCollectionIfMissing(adminPagesCollection, ...adminPagesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const adminPages: IAdminPages = { id: 123 };
        const adminPages2: IAdminPages = { id: 456 };
        expectedResult = service.addAdminPagesToCollectionIfMissing([], adminPages, adminPages2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminPages);
        expect(expectedResult).toContain(adminPages2);
      });

      it('should accept null and undefined values', () => {
        const adminPages: IAdminPages = { id: 123 };
        expectedResult = service.addAdminPagesToCollectionIfMissing([], null, adminPages, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminPages);
      });

      it('should return initial array if no AdminPages is added', () => {
        const adminPagesCollection: IAdminPages[] = [{ id: 123 }];
        expectedResult = service.addAdminPagesToCollectionIfMissing(adminPagesCollection, undefined, null);
        expect(expectedResult).toEqual(adminPagesCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
