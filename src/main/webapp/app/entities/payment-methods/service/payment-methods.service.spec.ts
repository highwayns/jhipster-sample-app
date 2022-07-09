import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { PaymentMethod } from 'app/entities/enumerations/payment-method.model';
import { IPaymentMethods, PaymentMethods } from '../payment-methods.model';

import { PaymentMethodsService } from './payment-methods.service';

describe('PaymentMethods Service', () => {
  let service: PaymentMethodsService;
  let httpMock: HttpTestingController;
  let elemDefault: IPaymentMethods;
  let expectedResult: IPaymentMethods | IPaymentMethods[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PaymentMethodsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      paymentMethod: PaymentMethod.IDEAL,
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

    it('should create a PaymentMethods', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PaymentMethods()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PaymentMethods', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          paymentMethod: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PaymentMethods', () => {
      const patchObject = Object.assign({}, new PaymentMethods());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PaymentMethods', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          paymentMethod: 'BBBBBB',
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

    it('should delete a PaymentMethods', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPaymentMethodsToCollectionIfMissing', () => {
      it('should add a PaymentMethods to an empty array', () => {
        const paymentMethods: IPaymentMethods = { id: 123 };
        expectedResult = service.addPaymentMethodsToCollectionIfMissing([], paymentMethods);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentMethods);
      });

      it('should not add a PaymentMethods to an array that contains it', () => {
        const paymentMethods: IPaymentMethods = { id: 123 };
        const paymentMethodsCollection: IPaymentMethods[] = [
          {
            ...paymentMethods,
          },
          { id: 456 },
        ];
        expectedResult = service.addPaymentMethodsToCollectionIfMissing(paymentMethodsCollection, paymentMethods);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PaymentMethods to an array that doesn't contain it", () => {
        const paymentMethods: IPaymentMethods = { id: 123 };
        const paymentMethodsCollection: IPaymentMethods[] = [{ id: 456 }];
        expectedResult = service.addPaymentMethodsToCollectionIfMissing(paymentMethodsCollection, paymentMethods);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentMethods);
      });

      it('should add only unique PaymentMethods to an array', () => {
        const paymentMethodsArray: IPaymentMethods[] = [{ id: 123 }, { id: 456 }, { id: 74129 }];
        const paymentMethodsCollection: IPaymentMethods[] = [{ id: 123 }];
        expectedResult = service.addPaymentMethodsToCollectionIfMissing(paymentMethodsCollection, ...paymentMethodsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const paymentMethods: IPaymentMethods = { id: 123 };
        const paymentMethods2: IPaymentMethods = { id: 456 };
        expectedResult = service.addPaymentMethodsToCollectionIfMissing([], paymentMethods, paymentMethods2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentMethods);
        expect(expectedResult).toContain(paymentMethods2);
      });

      it('should accept null and undefined values', () => {
        const paymentMethods: IPaymentMethods = { id: 123 };
        expectedResult = service.addPaymentMethodsToCollectionIfMissing([], null, paymentMethods, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentMethods);
      });

      it('should return initial array if no PaymentMethods is added', () => {
        const paymentMethodsCollection: IPaymentMethods[] = [{ id: 123 }];
        expectedResult = service.addPaymentMethodsToCollectionIfMissing(paymentMethodsCollection, undefined, null);
        expect(expectedResult).toEqual(paymentMethodsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
