import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAdminImageSizes, AdminImageSizes } from '../admin-image-sizes.model';

import { AdminImageSizesService } from './admin-image-sizes.service';

describe('AdminImageSizes Service', () => {
  let service: AdminImageSizesService;
  let httpMock: HttpTestingController;
  let elemDefault: IAdminImageSizes;
  let expectedResult: IAdminImageSizes | IAdminImageSizes[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AdminImageSizesService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      fieldName: 'AAAAAAA',
      value: 'AAAAAAA',
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

    it('should create a AdminImageSizes', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AdminImageSizes()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AdminImageSizes', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          fieldName: 'BBBBBB',
          value: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AdminImageSizes', () => {
      const patchObject = Object.assign(
        {
          fieldName: 'BBBBBB',
        },
        new AdminImageSizes()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AdminImageSizes', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          fieldName: 'BBBBBB',
          value: 'BBBBBB',
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

    it('should delete a AdminImageSizes', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAdminImageSizesToCollectionIfMissing', () => {
      it('should add a AdminImageSizes to an empty array', () => {
        const adminImageSizes: IAdminImageSizes = { id: 123 };
        expectedResult = service.addAdminImageSizesToCollectionIfMissing([], adminImageSizes);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminImageSizes);
      });

      it('should not add a AdminImageSizes to an array that contains it', () => {
        const adminImageSizes: IAdminImageSizes = { id: 123 };
        const adminImageSizesCollection: IAdminImageSizes[] = [
          {
            ...adminImageSizes,
          },
          { id: 456 },
        ];
        expectedResult = service.addAdminImageSizesToCollectionIfMissing(adminImageSizesCollection, adminImageSizes);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AdminImageSizes to an array that doesn't contain it", () => {
        const adminImageSizes: IAdminImageSizes = { id: 123 };
        const adminImageSizesCollection: IAdminImageSizes[] = [{ id: 456 }];
        expectedResult = service.addAdminImageSizesToCollectionIfMissing(adminImageSizesCollection, adminImageSizes);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminImageSizes);
      });

      it('should add only unique AdminImageSizes to an array', () => {
        const adminImageSizesArray: IAdminImageSizes[] = [{ id: 123 }, { id: 456 }, { id: 11938 }];
        const adminImageSizesCollection: IAdminImageSizes[] = [{ id: 123 }];
        expectedResult = service.addAdminImageSizesToCollectionIfMissing(adminImageSizesCollection, ...adminImageSizesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const adminImageSizes: IAdminImageSizes = { id: 123 };
        const adminImageSizes2: IAdminImageSizes = { id: 456 };
        expectedResult = service.addAdminImageSizesToCollectionIfMissing([], adminImageSizes, adminImageSizes2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminImageSizes);
        expect(expectedResult).toContain(adminImageSizes2);
      });

      it('should accept null and undefined values', () => {
        const adminImageSizes: IAdminImageSizes = { id: 123 };
        expectedResult = service.addAdminImageSizesToCollectionIfMissing([], null, adminImageSizes, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminImageSizes);
      });

      it('should return initial array if no AdminImageSizes is added', () => {
        const adminImageSizesCollection: IAdminImageSizes[] = [{ id: 123 }];
        expectedResult = service.addAdminImageSizesToCollectionIfMissing(adminImageSizesCollection, undefined, null);
        expect(expectedResult).toEqual(adminImageSizesCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
