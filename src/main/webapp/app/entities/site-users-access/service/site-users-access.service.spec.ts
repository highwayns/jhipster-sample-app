import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISiteUsersAccess, SiteUsersAccess } from '../site-users-access.model';

import { SiteUsersAccessService } from './site-users-access.service';

describe('SiteUsersAccess Service', () => {
  let service: SiteUsersAccessService;
  let httpMock: HttpTestingController;
  let elemDefault: ISiteUsersAccess;
  let expectedResult: ISiteUsersAccess | ISiteUsersAccess[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SiteUsersAccessService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      start: 0,
      last: 0,
      attempts: 0,
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

    it('should create a SiteUsersAccess', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new SiteUsersAccess()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SiteUsersAccess', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          start: 1,
          last: 1,
          attempts: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SiteUsersAccess', () => {
      const patchObject = Object.assign(
        {
          attempts: 1,
        },
        new SiteUsersAccess()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SiteUsersAccess', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          start: 1,
          last: 1,
          attempts: 1,
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

    it('should delete a SiteUsersAccess', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSiteUsersAccessToCollectionIfMissing', () => {
      it('should add a SiteUsersAccess to an empty array', () => {
        const siteUsersAccess: ISiteUsersAccess = { id: 123 };
        expectedResult = service.addSiteUsersAccessToCollectionIfMissing([], siteUsersAccess);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(siteUsersAccess);
      });

      it('should not add a SiteUsersAccess to an array that contains it', () => {
        const siteUsersAccess: ISiteUsersAccess = { id: 123 };
        const siteUsersAccessCollection: ISiteUsersAccess[] = [
          {
            ...siteUsersAccess,
          },
          { id: 456 },
        ];
        expectedResult = service.addSiteUsersAccessToCollectionIfMissing(siteUsersAccessCollection, siteUsersAccess);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SiteUsersAccess to an array that doesn't contain it", () => {
        const siteUsersAccess: ISiteUsersAccess = { id: 123 };
        const siteUsersAccessCollection: ISiteUsersAccess[] = [{ id: 456 }];
        expectedResult = service.addSiteUsersAccessToCollectionIfMissing(siteUsersAccessCollection, siteUsersAccess);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(siteUsersAccess);
      });

      it('should add only unique SiteUsersAccess to an array', () => {
        const siteUsersAccessArray: ISiteUsersAccess[] = [{ id: 123 }, { id: 456 }, { id: 42996 }];
        const siteUsersAccessCollection: ISiteUsersAccess[] = [{ id: 123 }];
        expectedResult = service.addSiteUsersAccessToCollectionIfMissing(siteUsersAccessCollection, ...siteUsersAccessArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const siteUsersAccess: ISiteUsersAccess = { id: 123 };
        const siteUsersAccess2: ISiteUsersAccess = { id: 456 };
        expectedResult = service.addSiteUsersAccessToCollectionIfMissing([], siteUsersAccess, siteUsersAccess2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(siteUsersAccess);
        expect(expectedResult).toContain(siteUsersAccess2);
      });

      it('should accept null and undefined values', () => {
        const siteUsersAccess: ISiteUsersAccess = { id: 123 };
        expectedResult = service.addSiteUsersAccessToCollectionIfMissing([], null, siteUsersAccess, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(siteUsersAccess);
      });

      it('should return initial array if no SiteUsersAccess is added', () => {
        const siteUsersAccessCollection: ISiteUsersAccess[] = [{ id: 123 }];
        expectedResult = service.addSiteUsersAccessToCollectionIfMissing(siteUsersAccessCollection, undefined, null);
        expect(expectedResult).toEqual(siteUsersAccessCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
