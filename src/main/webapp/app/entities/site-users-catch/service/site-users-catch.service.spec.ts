import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISiteUsersCatch, SiteUsersCatch } from '../site-users-catch.model';

import { SiteUsersCatchService } from './site-users-catch.service';

describe('SiteUsersCatch Service', () => {
  let service: SiteUsersCatchService;
  let httpMock: HttpTestingController;
  let elemDefault: ISiteUsersCatch;
  let expectedResult: ISiteUsersCatch | ISiteUsersCatch[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SiteUsersCatchService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
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

    it('should create a SiteUsersCatch', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new SiteUsersCatch()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SiteUsersCatch', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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

    it('should partial update a SiteUsersCatch', () => {
      const patchObject = Object.assign(
        {
          attempts: 1,
        },
        new SiteUsersCatch()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SiteUsersCatch', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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

    it('should delete a SiteUsersCatch', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSiteUsersCatchToCollectionIfMissing', () => {
      it('should add a SiteUsersCatch to an empty array', () => {
        const siteUsersCatch: ISiteUsersCatch = { id: 123 };
        expectedResult = service.addSiteUsersCatchToCollectionIfMissing([], siteUsersCatch);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(siteUsersCatch);
      });

      it('should not add a SiteUsersCatch to an array that contains it', () => {
        const siteUsersCatch: ISiteUsersCatch = { id: 123 };
        const siteUsersCatchCollection: ISiteUsersCatch[] = [
          {
            ...siteUsersCatch,
          },
          { id: 456 },
        ];
        expectedResult = service.addSiteUsersCatchToCollectionIfMissing(siteUsersCatchCollection, siteUsersCatch);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SiteUsersCatch to an array that doesn't contain it", () => {
        const siteUsersCatch: ISiteUsersCatch = { id: 123 };
        const siteUsersCatchCollection: ISiteUsersCatch[] = [{ id: 456 }];
        expectedResult = service.addSiteUsersCatchToCollectionIfMissing(siteUsersCatchCollection, siteUsersCatch);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(siteUsersCatch);
      });

      it('should add only unique SiteUsersCatch to an array', () => {
        const siteUsersCatchArray: ISiteUsersCatch[] = [{ id: 123 }, { id: 456 }, { id: 48415 }];
        const siteUsersCatchCollection: ISiteUsersCatch[] = [{ id: 123 }];
        expectedResult = service.addSiteUsersCatchToCollectionIfMissing(siteUsersCatchCollection, ...siteUsersCatchArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const siteUsersCatch: ISiteUsersCatch = { id: 123 };
        const siteUsersCatch2: ISiteUsersCatch = { id: 456 };
        expectedResult = service.addSiteUsersCatchToCollectionIfMissing([], siteUsersCatch, siteUsersCatch2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(siteUsersCatch);
        expect(expectedResult).toContain(siteUsersCatch2);
      });

      it('should accept null and undefined values', () => {
        const siteUsersCatch: ISiteUsersCatch = { id: 123 };
        expectedResult = service.addSiteUsersCatchToCollectionIfMissing([], null, siteUsersCatch, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(siteUsersCatch);
      });

      it('should return initial array if no SiteUsersCatch is added', () => {
        const siteUsersCatchCollection: ISiteUsersCatch[] = [{ id: 123 }];
        expectedResult = service.addSiteUsersCatchToCollectionIfMissing(siteUsersCatchCollection, undefined, null);
        expect(expectedResult).toEqual(siteUsersCatchCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
