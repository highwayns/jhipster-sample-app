import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAdminSessions, AdminSessions } from '../admin-sessions.model';

import { AdminSessionsService } from './admin-sessions.service';

describe('AdminSessions Service', () => {
  let service: AdminSessionsService;
  let httpMock: HttpTestingController;
  let elemDefault: IAdminSessions;
  let expectedResult: IAdminSessions | IAdminSessions[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AdminSessionsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      sessionId: 'AAAAAAA',
      sessionTime: currentDate,
      sessionStart: currentDate,
      sessionValue: 'AAAAAAA',
      ipAddress: 'AAAAAAA',
      userAgent: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          sessionTime: currentDate.format(DATE_TIME_FORMAT),
          sessionStart: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a AdminSessions', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          sessionTime: currentDate.format(DATE_TIME_FORMAT),
          sessionStart: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          sessionTime: currentDate,
          sessionStart: currentDate,
        },
        returnedFromService
      );

      service.create(new AdminSessions()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AdminSessions', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          sessionId: 'BBBBBB',
          sessionTime: currentDate.format(DATE_TIME_FORMAT),
          sessionStart: currentDate.format(DATE_TIME_FORMAT),
          sessionValue: 'BBBBBB',
          ipAddress: 'BBBBBB',
          userAgent: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          sessionTime: currentDate,
          sessionStart: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AdminSessions', () => {
      const patchObject = Object.assign(
        {
          sessionId: 'BBBBBB',
          ipAddress: 'BBBBBB',
        },
        new AdminSessions()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          sessionTime: currentDate,
          sessionStart: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AdminSessions', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          sessionId: 'BBBBBB',
          sessionTime: currentDate.format(DATE_TIME_FORMAT),
          sessionStart: currentDate.format(DATE_TIME_FORMAT),
          sessionValue: 'BBBBBB',
          ipAddress: 'BBBBBB',
          userAgent: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          sessionTime: currentDate,
          sessionStart: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a AdminSessions', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAdminSessionsToCollectionIfMissing', () => {
      it('should add a AdminSessions to an empty array', () => {
        const adminSessions: IAdminSessions = { id: 123 };
        expectedResult = service.addAdminSessionsToCollectionIfMissing([], adminSessions);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminSessions);
      });

      it('should not add a AdminSessions to an array that contains it', () => {
        const adminSessions: IAdminSessions = { id: 123 };
        const adminSessionsCollection: IAdminSessions[] = [
          {
            ...adminSessions,
          },
          { id: 456 },
        ];
        expectedResult = service.addAdminSessionsToCollectionIfMissing(adminSessionsCollection, adminSessions);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AdminSessions to an array that doesn't contain it", () => {
        const adminSessions: IAdminSessions = { id: 123 };
        const adminSessionsCollection: IAdminSessions[] = [{ id: 456 }];
        expectedResult = service.addAdminSessionsToCollectionIfMissing(adminSessionsCollection, adminSessions);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminSessions);
      });

      it('should add only unique AdminSessions to an array', () => {
        const adminSessionsArray: IAdminSessions[] = [{ id: 123 }, { id: 456 }, { id: 52373 }];
        const adminSessionsCollection: IAdminSessions[] = [{ id: 123 }];
        expectedResult = service.addAdminSessionsToCollectionIfMissing(adminSessionsCollection, ...adminSessionsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const adminSessions: IAdminSessions = { id: 123 };
        const adminSessions2: IAdminSessions = { id: 456 };
        expectedResult = service.addAdminSessionsToCollectionIfMissing([], adminSessions, adminSessions2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminSessions);
        expect(expectedResult).toContain(adminSessions2);
      });

      it('should accept null and undefined values', () => {
        const adminSessions: IAdminSessions = { id: 123 };
        expectedResult = service.addAdminSessionsToCollectionIfMissing([], null, adminSessions, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminSessions);
      });

      it('should return initial array if no AdminSessions is added', () => {
        const adminSessionsCollection: IAdminSessions[] = [{ id: 123 }];
        expectedResult = service.addAdminSessionsToCollectionIfMissing(adminSessionsCollection, undefined, null);
        expect(expectedResult).toEqual(adminSessionsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
