import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Currency } from 'app/entities/enumerations/currency.model';
import { IPaymentMethodInfo, PaymentMethodInfo } from '../payment-method-info.model';

import { PaymentMethodInfoService } from './payment-method-info.service';

describe('PaymentMethodInfo Service', () => {
  let service: PaymentMethodInfoService;
  let httpMock: HttpTestingController;
  let elemDefault: IPaymentMethodInfo;
  let expectedResult: IPaymentMethodInfo | IPaymentMethodInfo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PaymentMethodInfoService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      paymentMethod: 'AAAAAAA',
      logo: 'AAAAAAA',
      supportsTokenisation: false,
      currencies: Currency.CNY,
      surchargeAmount: 0,
      surchargeAmountExclVat: 0,
      surchargeAmountVat: 0,
      surchargeVatPercentage: 0,
      description: 'AAAAAAA',
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

    it('should create a PaymentMethodInfo', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PaymentMethodInfo()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PaymentMethodInfo', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          paymentMethod: 'BBBBBB',
          logo: 'BBBBBB',
          supportsTokenisation: true,
          currencies: 'BBBBBB',
          surchargeAmount: 1,
          surchargeAmountExclVat: 1,
          surchargeAmountVat: 1,
          surchargeVatPercentage: 1,
          description: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PaymentMethodInfo', () => {
      const patchObject = Object.assign(
        {
          logo: 'BBBBBB',
          surchargeAmount: 1,
          surchargeVatPercentage: 1,
          description: 'BBBBBB',
        },
        new PaymentMethodInfo()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PaymentMethodInfo', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          paymentMethod: 'BBBBBB',
          logo: 'BBBBBB',
          supportsTokenisation: true,
          currencies: 'BBBBBB',
          surchargeAmount: 1,
          surchargeAmountExclVat: 1,
          surchargeAmountVat: 1,
          surchargeVatPercentage: 1,
          description: 'BBBBBB',
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

    it('should delete a PaymentMethodInfo', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPaymentMethodInfoToCollectionIfMissing', () => {
      it('should add a PaymentMethodInfo to an empty array', () => {
        const paymentMethodInfo: IPaymentMethodInfo = { id: 123 };
        expectedResult = service.addPaymentMethodInfoToCollectionIfMissing([], paymentMethodInfo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentMethodInfo);
      });

      it('should not add a PaymentMethodInfo to an array that contains it', () => {
        const paymentMethodInfo: IPaymentMethodInfo = { id: 123 };
        const paymentMethodInfoCollection: IPaymentMethodInfo[] = [
          {
            ...paymentMethodInfo,
          },
          { id: 456 },
        ];
        expectedResult = service.addPaymentMethodInfoToCollectionIfMissing(paymentMethodInfoCollection, paymentMethodInfo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PaymentMethodInfo to an array that doesn't contain it", () => {
        const paymentMethodInfo: IPaymentMethodInfo = { id: 123 };
        const paymentMethodInfoCollection: IPaymentMethodInfo[] = [{ id: 456 }];
        expectedResult = service.addPaymentMethodInfoToCollectionIfMissing(paymentMethodInfoCollection, paymentMethodInfo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentMethodInfo);
      });

      it('should add only unique PaymentMethodInfo to an array', () => {
        const paymentMethodInfoArray: IPaymentMethodInfo[] = [{ id: 123 }, { id: 456 }, { id: 35393 }];
        const paymentMethodInfoCollection: IPaymentMethodInfo[] = [{ id: 123 }];
        expectedResult = service.addPaymentMethodInfoToCollectionIfMissing(paymentMethodInfoCollection, ...paymentMethodInfoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const paymentMethodInfo: IPaymentMethodInfo = { id: 123 };
        const paymentMethodInfo2: IPaymentMethodInfo = { id: 456 };
        expectedResult = service.addPaymentMethodInfoToCollectionIfMissing([], paymentMethodInfo, paymentMethodInfo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentMethodInfo);
        expect(expectedResult).toContain(paymentMethodInfo2);
      });

      it('should accept null and undefined values', () => {
        const paymentMethodInfo: IPaymentMethodInfo = { id: 123 };
        expectedResult = service.addPaymentMethodInfoToCollectionIfMissing([], null, paymentMethodInfo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentMethodInfo);
      });

      it('should return initial array if no PaymentMethodInfo is added', () => {
        const paymentMethodInfoCollection: IPaymentMethodInfo[] = [{ id: 123 }];
        expectedResult = service.addPaymentMethodInfoToCollectionIfMissing(paymentMethodInfoCollection, undefined, null);
        expect(expectedResult).toEqual(paymentMethodInfoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
