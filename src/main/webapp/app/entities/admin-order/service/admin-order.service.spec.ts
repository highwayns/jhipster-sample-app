import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAdminOrder, AdminOrder } from '../admin-order.model';

import { AdminOrderService } from './admin-order.service';

describe('AdminOrder Service', () => {
  let service: AdminOrderService;
  let httpMock: HttpTestingController;
  let elemDefault: IAdminOrder;
  let expectedResult: IAdminOrder | IAdminOrder[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AdminOrderService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      orderBy: 'AAAAAAA',
      orderAsc: 0,
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

    it('should create a AdminOrder', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AdminOrder()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AdminOrder', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          orderBy: 'BBBBBB',
          orderAsc: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AdminOrder', () => {
      const patchObject = Object.assign(
        {
          orderBy: 'BBBBBB',
          orderAsc: 1,
        },
        new AdminOrder()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AdminOrder', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          orderBy: 'BBBBBB',
          orderAsc: 1,
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

    it('should delete a AdminOrder', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAdminOrderToCollectionIfMissing', () => {
      it('should add a AdminOrder to an empty array', () => {
        const adminOrder: IAdminOrder = { id: 123 };
        expectedResult = service.addAdminOrderToCollectionIfMissing([], adminOrder);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminOrder);
      });

      it('should not add a AdminOrder to an array that contains it', () => {
        const adminOrder: IAdminOrder = { id: 123 };
        const adminOrderCollection: IAdminOrder[] = [
          {
            ...adminOrder,
          },
          { id: 456 },
        ];
        expectedResult = service.addAdminOrderToCollectionIfMissing(adminOrderCollection, adminOrder);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AdminOrder to an array that doesn't contain it", () => {
        const adminOrder: IAdminOrder = { id: 123 };
        const adminOrderCollection: IAdminOrder[] = [{ id: 456 }];
        expectedResult = service.addAdminOrderToCollectionIfMissing(adminOrderCollection, adminOrder);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminOrder);
      });

      it('should add only unique AdminOrder to an array', () => {
        const adminOrderArray: IAdminOrder[] = [{ id: 123 }, { id: 456 }, { id: 96731 }];
        const adminOrderCollection: IAdminOrder[] = [{ id: 123 }];
        expectedResult = service.addAdminOrderToCollectionIfMissing(adminOrderCollection, ...adminOrderArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const adminOrder: IAdminOrder = { id: 123 };
        const adminOrder2: IAdminOrder = { id: 456 };
        expectedResult = service.addAdminOrderToCollectionIfMissing([], adminOrder, adminOrder2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adminOrder);
        expect(expectedResult).toContain(adminOrder2);
      });

      it('should accept null and undefined values', () => {
        const adminOrder: IAdminOrder = { id: 123 };
        expectedResult = service.addAdminOrderToCollectionIfMissing([], null, adminOrder, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adminOrder);
      });

      it('should return initial array if no AdminOrder is added', () => {
        const adminOrderCollection: IAdminOrder[] = [{ id: 123 }];
        expectedResult = service.addAdminOrderToCollectionIfMissing(adminOrderCollection, undefined, null);
        expect(expectedResult).toEqual(adminOrderCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
