import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAdminCron, AdminCron } from '../admin-cron.model';

import { AdminCronService } from './admin-cron.service';

describe('AdminCron Service', () => {
  let service: AdminCronService;
  let httpMock: HttpTestingController;
  let elemDefault: IAdminCron;
  let expectedResult: IAdminCron | IAdminCron[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AdminCronService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      day: 'AAAAAAA',
      month: 'AAAAAAA',
      year: 'AAAAAAA',
      sendCondition: 'AAAAAAA',
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

    it('should create a AdminCron', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AdminCron()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AdminCron', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          day: 'BBBBBB',
          month: 'BBBBBB',
          year: 'BBBBBB',
          sendCondition: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AdminCron', () => {
      const patchObject = Object.assign(
        {
          month: 'BBBBBB',
          sendCondition: 'BBBBBB',
        },
        new AdminCron()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AdminCron', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          day: 'BBBBBB',
          month: 'BBBBBB',
          year: 'BBBBBB',
          sendCondition: 'BBBBBB',
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

    it('should delete a AdminCron', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAdminCronToCollectionIfMissing', () => {
      it('should add a AdminCron to an empty array', () => {
        const adminCron: IAdminCron = { id: 123 };
        expectedResult = service.addAdminCronToCollectionIfMissing([], adminCron);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminCron);
      });

      it('should not add a AdminCron to an array that contains it', () => {
        const adminCron: IAdminCron = { id: 123 };
        const adminCronCollection: IAdminCron[] = [
          {
            ...adminCron,
          },
          { id: 456 },
        ];
        expectedResult = service.addAdminCronToCollectionIfMissing(adminCronCollection, adminCron);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AdminCron to an array that doesn't contain it", () => {
        const adminCron: IAdminCron = { id: 123 };
        const adminCronCollection: IAdminCron[] = [{ id: 456 }];
        expectedResult = service.addAdminCronToCollectionIfMissing(adminCronCollection, adminCron);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminCron);
      });

      it('should add only unique AdminCron to an array', () => {
        const adminCronArray: IAdminCron[] = [{ id: 123 }, { id: 456 }, { id: 56028 }];
        const adminCronCollection: IAdminCron[] = [{ id: 123 }];
        expectedResult = service.addAdminCronToCollectionIfMissing(adminCronCollection, ...adminCronArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const adminCron: IAdminCron = { id: 123 };
        const adminCron2: IAdminCron = { id: 456 };
        expectedResult = service.addAdminCronToCollectionIfMissing([], adminCron, adminCron2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminCron);
        expect(expectedResult).toContain(adminCron2);
      });

      it('should accept null and undefined values', () => {
        const adminCron: IAdminCron = { id: 123 };
        expectedResult = service.addAdminCronToCollectionIfMissing([], null, adminCron, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminCron);
      });

      it('should return initial array if no AdminCron is added', () => {
        const adminCronCollection: IAdminCron[] = [{ id: 123 }];
        expectedResult = service.addAdminCronToCollectionIfMissing(adminCronCollection, undefined, null);
        expect(expectedResult).toEqual(adminCronCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
