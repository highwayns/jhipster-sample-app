import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { YesNo } from 'app/entities/enumerations/yes-no.model';
import { IAdminTabs, AdminTabs } from '../admin-tabs.model';

import { AdminTabsService } from './admin-tabs.service';

describe('AdminTabs Service', () => {
  let service: AdminTabsService;
  let httpMock: HttpTestingController;
  let elemDefault: IAdminTabs;
  let expectedResult: IAdminTabs | IAdminTabs[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AdminTabsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      order: 0,
      icon: 'AAAAAAA',
      url: 'AAAAAAA',
      hidden: YesNo.Y,
      isCtrlPanel: YesNo.Y,
      forGroup: 0,
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

    it('should create a AdminTabs', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AdminTabs()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AdminTabs', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          order: 1,
          icon: 'BBBBBB',
          url: 'BBBBBB',
          hidden: 'BBBBBB',
          isCtrlPanel: 'BBBBBB',
          forGroup: 1,
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

    it('should partial update a AdminTabs', () => {
      const patchObject = Object.assign(
        {
          order: 1,
          url: 'BBBBBB',
          hidden: 'BBBBBB',
          isCtrlPanel: 'BBBBBB',
          forGroup: 1,
        },
        new AdminTabs()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AdminTabs', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          order: 1,
          icon: 'BBBBBB',
          url: 'BBBBBB',
          hidden: 'BBBBBB',
          isCtrlPanel: 'BBBBBB',
          forGroup: 1,
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

    it('should delete a AdminTabs', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAdminTabsToCollectionIfMissing', () => {
      it('should add a AdminTabs to an empty array', () => {
        const adminTabs: IAdminTabs = { id: 123 };
        expectedResult = service.addAdminTabsToCollectionIfMissing([], adminTabs);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminTabs);
      });

      it('should not add a AdminTabs to an array that contains it', () => {
        const adminTabs: IAdminTabs = { id: 123 };
        const adminTabsCollection: IAdminTabs[] = [
          {
            ...adminTabs,
          },
          { id: 456 },
        ];
        expectedResult = service.addAdminTabsToCollectionIfMissing(adminTabsCollection, adminTabs);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AdminTabs to an array that doesn't contain it", () => {
        const adminTabs: IAdminTabs = { id: 123 };
        const adminTabsCollection: IAdminTabs[] = [{ id: 456 }];
        expectedResult = service.addAdminTabsToCollectionIfMissing(adminTabsCollection, adminTabs);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminTabs);
      });

      it('should add only unique AdminTabs to an array', () => {
        const adminTabsArray: IAdminTabs[] = [{ id: 123 }, { id: 456 }, { id: 7515 }];
        const adminTabsCollection: IAdminTabs[] = [{ id: 123 }];
        expectedResult = service.addAdminTabsToCollectionIfMissing(adminTabsCollection, ...adminTabsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const adminTabs: IAdminTabs = { id: 123 };
        const adminTabs2: IAdminTabs = { id: 456 };
        expectedResult = service.addAdminTabsToCollectionIfMissing([], adminTabs, adminTabs2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminTabs);
        expect(expectedResult).toContain(adminTabs2);
      });

      it('should accept null and undefined values', () => {
        const adminTabs: IAdminTabs = { id: 123 };
        expectedResult = service.addAdminTabsToCollectionIfMissing([], null, adminTabs, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminTabs);
      });

      it('should return initial array if no AdminTabs is added', () => {
        const adminTabsCollection: IAdminTabs[] = [{ id: 123 }];
        expectedResult = service.addAdminTabsToCollectionIfMissing(adminTabsCollection, undefined, null);
        expect(expectedResult).toEqual(adminTabsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
