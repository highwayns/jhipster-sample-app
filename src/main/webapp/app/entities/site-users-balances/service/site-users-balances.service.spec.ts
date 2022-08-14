import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISiteUsersBalances, SiteUsersBalances } from '../site-users-balances.model';

import { SiteUsersBalancesService } from './site-users-balances.service';

describe('SiteUsersBalances Service', () => {
  let service: SiteUsersBalancesService;
  let httpMock: HttpTestingController;
  let elemDefault: ISiteUsersBalances;
  let expectedResult: ISiteUsersBalances | ISiteUsersBalances[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SiteUsersBalancesService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      balance: 0,
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

    it('should create a SiteUsersBalances', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new SiteUsersBalances()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SiteUsersBalances', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          balance: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SiteUsersBalances', () => {
      const patchObject = Object.assign(
        {
          balance: 1,
        },
        new SiteUsersBalances()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SiteUsersBalances', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          balance: 1,
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

    it('should delete a SiteUsersBalances', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSiteUsersBalancesToCollectionIfMissing', () => {
      it('should add a SiteUsersBalances to an empty array', () => {
        const siteUsersBalances: ISiteUsersBalances = { id: 123 };
        expectedResult = service.addSiteUsersBalancesToCollectionIfMissing([], siteUsersBalances);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(siteUsersBalances);
      });

      it('should not add a SiteUsersBalances to an array that contains it', () => {
        const siteUsersBalances: ISiteUsersBalances = { id: 123 };
        const siteUsersBalancesCollection: ISiteUsersBalances[] = [
          {
            ...siteUsersBalances,
          },
          { id: 456 },
        ];
        expectedResult = service.addSiteUsersBalancesToCollectionIfMissing(siteUsersBalancesCollection, siteUsersBalances);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SiteUsersBalances to an array that doesn't contain it", () => {
        const siteUsersBalances: ISiteUsersBalances = { id: 123 };
        const siteUsersBalancesCollection: ISiteUsersBalances[] = [{ id: 456 }];
        expectedResult = service.addSiteUsersBalancesToCollectionIfMissing(siteUsersBalancesCollection, siteUsersBalances);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(siteUsersBalances);
      });

      it('should add only unique SiteUsersBalances to an array', () => {
        const siteUsersBalancesArray: ISiteUsersBalances[] = [{ id: 123 }, { id: 456 }, { id: 82570 }];
        const siteUsersBalancesCollection: ISiteUsersBalances[] = [{ id: 123 }];
        expectedResult = service.addSiteUsersBalancesToCollectionIfMissing(siteUsersBalancesCollection, ...siteUsersBalancesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const siteUsersBalances: ISiteUsersBalances = { id: 123 };
        const siteUsersBalances2: ISiteUsersBalances = { id: 456 };
        expectedResult = service.addSiteUsersBalancesToCollectionIfMissing([], siteUsersBalances, siteUsersBalances2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(siteUsersBalances);
        expect(expectedResult).toContain(siteUsersBalances2);
      });

      it('should accept null and undefined values', () => {
        const siteUsersBalances: ISiteUsersBalances = { id: 123 };
        expectedResult = service.addSiteUsersBalancesToCollectionIfMissing([], null, siteUsersBalances, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(siteUsersBalances);
      });

      it('should return initial array if no SiteUsersBalances is added', () => {
        const siteUsersBalancesCollection: ISiteUsersBalances[] = [{ id: 123 }];
        expectedResult = service.addSiteUsersBalancesToCollectionIfMissing(siteUsersBalancesCollection, undefined, null);
        expect(expectedResult).toEqual(siteUsersBalancesCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
