import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IOrderTypes, OrderTypes } from '../order-types.model';

import { OrderTypesService } from './order-types.service';

describe('OrderTypes Service', () => {
  let service: OrderTypesService;
  let httpMock: HttpTestingController;
  let elemDefault: IOrderTypes;
  let expectedResult: IOrderTypes | IOrderTypes[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OrderTypesService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nameEn: 'AAAAAAA',
      nameEs: 'AAAAAAA',
      nameRu: 'AAAAAAA',
      nameZh: 'AAAAAAA',
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

    it('should create a OrderTypes', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new OrderTypes()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrderTypes', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nameEn: 'BBBBBB',
          nameEs: 'BBBBBB',
          nameRu: 'BBBBBB',
          nameZh: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OrderTypes', () => {
      const patchObject = Object.assign(
        {
          nameEn: 'BBBBBB',
          nameRu: 'BBBBBB',
        },
        new OrderTypes()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OrderTypes', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nameEn: 'BBBBBB',
          nameEs: 'BBBBBB',
          nameRu: 'BBBBBB',
          nameZh: 'BBBBBB',
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

    it('should delete a OrderTypes', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addOrderTypesToCollectionIfMissing', () => {
      it('should add a OrderTypes to an empty array', () => {
        const orderTypes: IOrderTypes = { id: 123 };
        expectedResult = service.addOrderTypesToCollectionIfMissing([], orderTypes);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orderTypes);
      });

      it('should not add a OrderTypes to an array that contains it', () => {
        const orderTypes: IOrderTypes = { id: 123 };
        const orderTypesCollection: IOrderTypes[] = [
          {
            ...orderTypes,
          },
          { id: 456 },
        ];
        expectedResult = service.addOrderTypesToCollectionIfMissing(orderTypesCollection, orderTypes);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrderTypes to an array that doesn't contain it", () => {
        const orderTypes: IOrderTypes = { id: 123 };
        const orderTypesCollection: IOrderTypes[] = [{ id: 456 }];
        expectedResult = service.addOrderTypesToCollectionIfMissing(orderTypesCollection, orderTypes);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orderTypes);
      });

      it('should add only unique OrderTypes to an array', () => {
        const orderTypesArray: IOrderTypes[] = [{ id: 123 }, { id: 456 }, { id: 34007 }];
        const orderTypesCollection: IOrderTypes[] = [{ id: 123 }];
        expectedResult = service.addOrderTypesToCollectionIfMissing(orderTypesCollection, ...orderTypesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const orderTypes: IOrderTypes = { id: 123 };
        const orderTypes2: IOrderTypes = { id: 456 };
        expectedResult = service.addOrderTypesToCollectionIfMissing([], orderTypes, orderTypes2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orderTypes);
        expect(expectedResult).toContain(orderTypes2);
      });

      it('should accept null and undefined values', () => {
        const orderTypes: IOrderTypes = { id: 123 };
        expectedResult = service.addOrderTypesToCollectionIfMissing([], null, orderTypes, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orderTypes);
      });

      it('should return initial array if no OrderTypes is added', () => {
        const orderTypesCollection: IOrderTypes[] = [{ id: 123 }];
        expectedResult = service.addOrderTypesToCollectionIfMissing(orderTypesCollection, undefined, null);
        expect(expectedResult).toEqual(orderTypesCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
