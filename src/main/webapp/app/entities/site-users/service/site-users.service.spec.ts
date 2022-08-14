import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { YesNo } from 'app/entities/enumerations/yes-no.model';
import { ISiteUsers, SiteUsers } from '../site-users.model';

import { SiteUsersService } from './site-users.service';

describe('SiteUsers Service', () => {
  let service: SiteUsersService;
  let httpMock: HttpTestingController;
  let elemDefault: ISiteUsers;
  let expectedResult: ISiteUsers | ISiteUsers[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SiteUsersService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      pass: 'AAAAAAA',
      firstName: 'AAAAAAA',
      lastName: 'AAAAAAA',
      email: 'AAAAAAA',
      date: currentDate,
      tel: 'AAAAAAA',
      user: 'AAAAAAA',
      countryCode: 0,
      authyRequested: YesNo.Y,
      verifiedAuthy: YesNo.Y,
      authyId: 0,
      usingSms: YesNo.Y,
      dontAsk30Days: YesNo.Y,
      dontAskDate: currentDate,
      confirmWithdrawalEmailBtc: YesNo.Y,
      confirmWithdrawal2faBtc: YesNo.Y,
      confirmWithdrawal2faBank: YesNo.Y,
      confirmWithdrawalEmailBank: YesNo.Y,
      notifyDepositBtc: YesNo.Y,
      notifyDepositBank: YesNo.Y,
      lastUpdate: currentDate,
      noLogins: YesNo.Y,
      notifyLogin: YesNo.Y,
      deactivated: YesNo.Y,
      locked: YesNo.Y,
      google2faCode: 'AAAAAAA',
      verifiedGoogle: YesNo.Y,
      lastLang: 'AAAAAAA',
      notifyWithdrawBtc: YesNo.Y,
      notifyWithdrawBank: YesNo.Y,
      trusted: YesNo.Y,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          date: currentDate.format(DATE_TIME_FORMAT),
          dontAskDate: currentDate.format(DATE_TIME_FORMAT),
          lastUpdate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a SiteUsers', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          date: currentDate.format(DATE_TIME_FORMAT),
          dontAskDate: currentDate.format(DATE_TIME_FORMAT),
          lastUpdate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
          dontAskDate: currentDate,
          lastUpdate: currentDate,
        },
        returnedFromService
      );

      service.create(new SiteUsers()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SiteUsers', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          pass: 'BBBBBB',
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          email: 'BBBBBB',
          date: currentDate.format(DATE_TIME_FORMAT),
          tel: 'BBBBBB',
          user: 'BBBBBB',
          countryCode: 1,
          authyRequested: 'BBBBBB',
          verifiedAuthy: 'BBBBBB',
          authyId: 1,
          usingSms: 'BBBBBB',
          dontAsk30Days: 'BBBBBB',
          dontAskDate: currentDate.format(DATE_TIME_FORMAT),
          confirmWithdrawalEmailBtc: 'BBBBBB',
          confirmWithdrawal2faBtc: 'BBBBBB',
          confirmWithdrawal2faBank: 'BBBBBB',
          confirmWithdrawalEmailBank: 'BBBBBB',
          notifyDepositBtc: 'BBBBBB',
          notifyDepositBank: 'BBBBBB',
          lastUpdate: currentDate.format(DATE_TIME_FORMAT),
          noLogins: 'BBBBBB',
          notifyLogin: 'BBBBBB',
          deactivated: 'BBBBBB',
          locked: 'BBBBBB',
          google2faCode: 'BBBBBB',
          verifiedGoogle: 'BBBBBB',
          lastLang: 'BBBBBB',
          notifyWithdrawBtc: 'BBBBBB',
          notifyWithdrawBank: 'BBBBBB',
          trusted: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
          dontAskDate: currentDate,
          lastUpdate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SiteUsers', () => {
      const patchObject = Object.assign(
        {
          pass: 'BBBBBB',
          firstName: 'BBBBBB',
          date: currentDate.format(DATE_TIME_FORMAT),
          user: 'BBBBBB',
          countryCode: 1,
          authyRequested: 'BBBBBB',
          verifiedAuthy: 'BBBBBB',
          usingSms: 'BBBBBB',
          dontAsk30Days: 'BBBBBB',
          confirmWithdrawalEmailBtc: 'BBBBBB',
          confirmWithdrawal2faBtc: 'BBBBBB',
          confirmWithdrawalEmailBank: 'BBBBBB',
          notifyDepositBank: 'BBBBBB',
          lastUpdate: currentDate.format(DATE_TIME_FORMAT),
          notifyLogin: 'BBBBBB',
          locked: 'BBBBBB',
          google2faCode: 'BBBBBB',
          lastLang: 'BBBBBB',
          notifyWithdrawBank: 'BBBBBB',
        },
        new SiteUsers()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          date: currentDate,
          dontAskDate: currentDate,
          lastUpdate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SiteUsers', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          pass: 'BBBBBB',
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          email: 'BBBBBB',
          date: currentDate.format(DATE_TIME_FORMAT),
          tel: 'BBBBBB',
          user: 'BBBBBB',
          countryCode: 1,
          authyRequested: 'BBBBBB',
          verifiedAuthy: 'BBBBBB',
          authyId: 1,
          usingSms: 'BBBBBB',
          dontAsk30Days: 'BBBBBB',
          dontAskDate: currentDate.format(DATE_TIME_FORMAT),
          confirmWithdrawalEmailBtc: 'BBBBBB',
          confirmWithdrawal2faBtc: 'BBBBBB',
          confirmWithdrawal2faBank: 'BBBBBB',
          confirmWithdrawalEmailBank: 'BBBBBB',
          notifyDepositBtc: 'BBBBBB',
          notifyDepositBank: 'BBBBBB',
          lastUpdate: currentDate.format(DATE_TIME_FORMAT),
          noLogins: 'BBBBBB',
          notifyLogin: 'BBBBBB',
          deactivated: 'BBBBBB',
          locked: 'BBBBBB',
          google2faCode: 'BBBBBB',
          verifiedGoogle: 'BBBBBB',
          lastLang: 'BBBBBB',
          notifyWithdrawBtc: 'BBBBBB',
          notifyWithdrawBank: 'BBBBBB',
          trusted: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
          dontAskDate: currentDate,
          lastUpdate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a SiteUsers', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSiteUsersToCollectionIfMissing', () => {
      it('should add a SiteUsers to an empty array', () => {
        const siteUsers: ISiteUsers = { id: 123 };
        expectedResult = service.addSiteUsersToCollectionIfMissing([], siteUsers);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(siteUsers);
      });

      it('should not add a SiteUsers to an array that contains it', () => {
        const siteUsers: ISiteUsers = { id: 123 };
        const siteUsersCollection: ISiteUsers[] = [
          {
            ...siteUsers,
          },
          { id: 456 },
        ];
        expectedResult = service.addSiteUsersToCollectionIfMissing(siteUsersCollection, siteUsers);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SiteUsers to an array that doesn't contain it", () => {
        const siteUsers: ISiteUsers = { id: 123 };
        const siteUsersCollection: ISiteUsers[] = [{ id: 456 }];
        expectedResult = service.addSiteUsersToCollectionIfMissing(siteUsersCollection, siteUsers);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(siteUsers);
      });

      it('should add only unique SiteUsers to an array', () => {
        const siteUsersArray: ISiteUsers[] = [{ id: 123 }, { id: 456 }, { id: 28950 }];
        const siteUsersCollection: ISiteUsers[] = [{ id: 123 }];
        expectedResult = service.addSiteUsersToCollectionIfMissing(siteUsersCollection, ...siteUsersArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const siteUsers: ISiteUsers = { id: 123 };
        const siteUsers2: ISiteUsers = { id: 456 };
        expectedResult = service.addSiteUsersToCollectionIfMissing([], siteUsers, siteUsers2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(siteUsers);
        expect(expectedResult).toContain(siteUsers2);
      });

      it('should accept null and undefined values', () => {
        const siteUsers: ISiteUsers = { id: 123 };
        expectedResult = service.addSiteUsersToCollectionIfMissing([], null, siteUsers, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(siteUsers);
      });

      it('should return initial array if no SiteUsers is added', () => {
        const siteUsersCollection: ISiteUsers[] = [{ id: 123 }];
        expectedResult = service.addSiteUsersToCollectionIfMissing(siteUsersCollection, undefined, null);
        expect(expectedResult).toEqual(siteUsersCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
