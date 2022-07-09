import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { OrderLineType } from 'app/entities/enumerations/order-line-type.model';
import { IOrderLine, OrderLine } from '../order-line.model';

import { OrderLineService } from './order-line.service';

describe('OrderLine Service', () => {
  let service: OrderLineService;
  let httpMock: HttpTestingController;
  let elemDefault: IOrderLine;
  let expectedResult: IOrderLine | IOrderLine[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OrderLineService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      lineNumber: 0,
      type: OrderLineType.PHYSICALITEM,
      skuCode: 'AAAAAAA',
      name: 'AAAAAAA',
      description: 'AAAAAAA',
      quantity: 0,
      unitPriceExclVat: 0,
      unitPriceInclVat: 0,
      vatPercentage: 0,
      vatPercentageLabel: 'AAAAAAA',
      discountPercentageLabel: 'AAAAAAA',
      totalLineAmount: 0,
      url: 'AAAAAAA',
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

    it('should create a OrderLine', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new OrderLine()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrderLine', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          lineNumber: 1,
          type: 'BBBBBB',
          skuCode: 'BBBBBB',
          name: 'BBBBBB',
          description: 'BBBBBB',
          quantity: 1,
          unitPriceExclVat: 1,
          unitPriceInclVat: 1,
          vatPercentage: 1,
          vatPercentageLabel: 'BBBBBB',
          discountPercentageLabel: 'BBBBBB',
          totalLineAmount: 1,
          url: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OrderLine', () => {
      const patchObject = Object.assign(
        {
          lineNumber: 1,
          type: 'BBBBBB',
          quantity: 1,
          vatPercentageLabel: 'BBBBBB',
        },
        new OrderLine()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OrderLine', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          lineNumber: 1,
          type: 'BBBBBB',
          skuCode: 'BBBBBB',
          name: 'BBBBBB',
          description: 'BBBBBB',
          quantity: 1,
          unitPriceExclVat: 1,
          unitPriceInclVat: 1,
          vatPercentage: 1,
          vatPercentageLabel: 'BBBBBB',
          discountPercentageLabel: 'BBBBBB',
          totalLineAmount: 1,
          url: 'BBBBBB',
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

    it('should delete a OrderLine', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addOrderLineToCollectionIfMissing', () => {
      it('should add a OrderLine to an empty array', () => {
        const orderLine: IOrderLine = { id: 123 };
        expectedResult = service.addOrderLineToCollectionIfMissing([], orderLine);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orderLine);
      });

      it('should not add a OrderLine to an array that contains it', () => {
        const orderLine: IOrderLine = { id: 123 };
        const orderLineCollection: IOrderLine[] = [
          {
            ...orderLine,
          },
          { id: 456 },
        ];
        expectedResult = service.addOrderLineToCollectionIfMissing(orderLineCollection, orderLine);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrderLine to an array that doesn't contain it", () => {
        const orderLine: IOrderLine = { id: 123 };
        const orderLineCollection: IOrderLine[] = [{ id: 456 }];
        expectedResult = service.addOrderLineToCollectionIfMissing(orderLineCollection, orderLine);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orderLine);
      });

      it('should add only unique OrderLine to an array', () => {
        const orderLineArray: IOrderLine[] = [{ id: 123 }, { id: 456 }, { id: 16703 }];
        const orderLineCollection: IOrderLine[] = [{ id: 123 }];
        expectedResult = service.addOrderLineToCollectionIfMissing(orderLineCollection, ...orderLineArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const orderLine: IOrderLine = { id: 123 };
        const orderLine2: IOrderLine = { id: 456 };
        expectedResult = service.addOrderLineToCollectionIfMissing([], orderLine, orderLine2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orderLine);
        expect(expectedResult).toContain(orderLine2);
      });

      it('should accept null and undefined values', () => {
        const orderLine: IOrderLine = { id: 123 };
        expectedResult = service.addOrderLineToCollectionIfMissing([], null, orderLine, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orderLine);
      });

      it('should return initial array if no OrderLine is added', () => {
        const orderLineCollection: IOrderLine[] = [{ id: 123 }];
        expectedResult = service.addOrderLineToCollectionIfMissing(orderLineCollection, undefined, null);
        expect(expectedResult).toEqual(orderLineCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
