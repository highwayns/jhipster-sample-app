import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { YesNo } from 'app/entities/enumerations/yes-no.model';
import { IAdminControls, AdminControls } from '../admin-controls.model';

import { AdminControlsService } from './admin-controls.service';

describe('AdminControls Service', () => {
  let service: AdminControlsService;
  let httpMock: HttpTestingController;
  let elemDefault: IAdminControls;
  let expectedResult: IAdminControls | IAdminControls[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AdminControlsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      action: 'AAAAAAA',
      controlClass: 'AAAAAAA',
      argument: 'AAAAAAA',
      order: 0,
      isStatic: YesNo.Y,
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

    it('should create a AdminControls', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AdminControls()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AdminControls', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          action: 'BBBBBB',
          controlClass: 'BBBBBB',
          argument: 'BBBBBB',
          order: 1,
          isStatic: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AdminControls', () => {
      const patchObject = Object.assign(
        {
          action: 'BBBBBB',
          controlClass: 'BBBBBB',
          argument: 'BBBBBB',
          order: 1,
        },
        new AdminControls()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AdminControls', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          action: 'BBBBBB',
          controlClass: 'BBBBBB',
          argument: 'BBBBBB',
          order: 1,
          isStatic: 'BBBBBB',
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

    it('should delete a AdminControls', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAdminControlsToCollectionIfMissing', () => {
      it('should add a AdminControls to an empty array', () => {
        const adminControls: IAdminControls = { id: 123 };
        expectedResult = service.addAdminControlsToCollectionIfMissing([], adminControls);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminControls);
      });

      it('should not add a AdminControls to an array that contains it', () => {
        const adminControls: IAdminControls = { id: 123 };
        const adminControlsCollection: IAdminControls[] = [
          {
            ...adminControls,
          },
          { id: 456 },
        ];
        expectedResult = service.addAdminControlsToCollectionIfMissing(adminControlsCollection, adminControls);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AdminControls to an array that doesn't contain it", () => {
        const adminControls: IAdminControls = { id: 123 };
        const adminControlsCollection: IAdminControls[] = [{ id: 456 }];
        expectedResult = service.addAdminControlsToCollectionIfMissing(adminControlsCollection, adminControls);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminControls);
      });

      it('should add only unique AdminControls to an array', () => {
        const adminControlsArray: IAdminControls[] = [{ id: 123 }, { id: 456 }, { id: 77536 }];
        const adminControlsCollection: IAdminControls[] = [{ id: 123 }];
        expectedResult = service.addAdminControlsToCollectionIfMissing(adminControlsCollection, ...adminControlsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const adminControls: IAdminControls = { id: 123 };
        const adminControls2: IAdminControls = { id: 456 };
        expectedResult = service.addAdminControlsToCollectionIfMissing([], adminControls, adminControls2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminControls);
        expect(expectedResult).toContain(adminControls2);
      });

      it('should accept null and undefined values', () => {
        const adminControls: IAdminControls = { id: 123 };
        expectedResult = service.addAdminControlsToCollectionIfMissing([], null, adminControls, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminControls);
      });

      it('should return initial array if no AdminControls is added', () => {
        const adminControlsCollection: IAdminControls[] = [{ id: 123 }];
        expectedResult = service.addAdminControlsToCollectionIfMissing(adminControlsCollection, undefined, null);
        expect(expectedResult).toEqual(adminControlsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
