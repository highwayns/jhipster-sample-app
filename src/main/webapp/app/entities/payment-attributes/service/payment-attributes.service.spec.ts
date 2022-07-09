import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { PaymentStatus } from 'app/entities/enumerations/payment-status.model';
import { IPaymentAttributes, PaymentAttributes } from '../payment-attributes.model';

import { PaymentAttributesService } from './payment-attributes.service';

describe('PaymentAttributes Service', () => {
  let service: PaymentAttributesService;
  let httpMock: HttpTestingController;
  let elemDefault: IPaymentAttributes;
  let expectedResult: IPaymentAttributes | IPaymentAttributes[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PaymentAttributesService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      originatingIpAddress: 'AAAAAAA',
      originHeader: 'AAAAAAA',
      userAgent: 'AAAAAAA',
      returnUrlSuccess: 'AAAAAAA',
      returnUrlFailed: 'AAAAAAA',
      returnUrlCancelled: 'AAAAAAA',
      simulatedStatus: 'AAAAAAA',
      idealBic: 'AAAAAAA',
      paymentMethodTransactionId: 'AAAAAAA',
      paymentMethodVoidTransactionId: 'AAAAAAA',
      token: 'AAAAAAA',
      cashFlowsAcquiringDetails: 'AAAAAAA',
      descriptor: 'AAAAAAA',
      ewalletType: 'AAAAAAA',
      paymentStatus: PaymentStatus.PENDING,
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

    it('should create a PaymentAttributes', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PaymentAttributes()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PaymentAttributes', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          originatingIpAddress: 'BBBBBB',
          originHeader: 'BBBBBB',
          userAgent: 'BBBBBB',
          returnUrlSuccess: 'BBBBBB',
          returnUrlFailed: 'BBBBBB',
          returnUrlCancelled: 'BBBBBB',
          simulatedStatus: 'BBBBBB',
          idealBic: 'BBBBBB',
          paymentMethodTransactionId: 'BBBBBB',
          paymentMethodVoidTransactionId: 'BBBBBB',
          token: 'BBBBBB',
          cashFlowsAcquiringDetails: 'BBBBBB',
          descriptor: 'BBBBBB',
          ewalletType: 'BBBBBB',
          paymentStatus: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PaymentAttributes', () => {
      const patchObject = Object.assign(
        {
          originatingIpAddress: 'BBBBBB',
          returnUrlSuccess: 'BBBBBB',
          returnUrlFailed: 'BBBBBB',
          returnUrlCancelled: 'BBBBBB',
          simulatedStatus: 'BBBBBB',
          cashFlowsAcquiringDetails: 'BBBBBB',
          ewalletType: 'BBBBBB',
          paymentStatus: 'BBBBBB',
        },
        new PaymentAttributes()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PaymentAttributes', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          originatingIpAddress: 'BBBBBB',
          originHeader: 'BBBBBB',
          userAgent: 'BBBBBB',
          returnUrlSuccess: 'BBBBBB',
          returnUrlFailed: 'BBBBBB',
          returnUrlCancelled: 'BBBBBB',
          simulatedStatus: 'BBBBBB',
          idealBic: 'BBBBBB',
          paymentMethodTransactionId: 'BBBBBB',
          paymentMethodVoidTransactionId: 'BBBBBB',
          token: 'BBBBBB',
          cashFlowsAcquiringDetails: 'BBBBBB',
          descriptor: 'BBBBBB',
          ewalletType: 'BBBBBB',
          paymentStatus: 'BBBBBB',
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

    it('should delete a PaymentAttributes', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPaymentAttributesToCollectionIfMissing', () => {
      it('should add a PaymentAttributes to an empty array', () => {
        const paymentAttributes: IPaymentAttributes = { id: 123 };
        expectedResult = service.addPaymentAttributesToCollectionIfMissing([], paymentAttributes);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentAttributes);
      });

      it('should not add a PaymentAttributes to an array that contains it', () => {
        const paymentAttributes: IPaymentAttributes = { id: 123 };
        const paymentAttributesCollection: IPaymentAttributes[] = [
          {
            ...paymentAttributes,
          },
          { id: 456 },
        ];
        expectedResult = service.addPaymentAttributesToCollectionIfMissing(paymentAttributesCollection, paymentAttributes);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PaymentAttributes to an array that doesn't contain it", () => {
        const paymentAttributes: IPaymentAttributes = { id: 123 };
        const paymentAttributesCollection: IPaymentAttributes[] = [{ id: 456 }];
        expectedResult = service.addPaymentAttributesToCollectionIfMissing(paymentAttributesCollection, paymentAttributes);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentAttributes);
      });

      it('should add only unique PaymentAttributes to an array', () => {
        const paymentAttributesArray: IPaymentAttributes[] = [{ id: 123 }, { id: 456 }, { id: 34527 }];
        const paymentAttributesCollection: IPaymentAttributes[] = [{ id: 123 }];
        expectedResult = service.addPaymentAttributesToCollectionIfMissing(paymentAttributesCollection, ...paymentAttributesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const paymentAttributes: IPaymentAttributes = { id: 123 };
        const paymentAttributes2: IPaymentAttributes = { id: 456 };
        expectedResult = service.addPaymentAttributesToCollectionIfMissing([], paymentAttributes, paymentAttributes2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentAttributes);
        expect(expectedResult).toContain(paymentAttributes2);
      });

      it('should accept null and undefined values', () => {
        const paymentAttributes: IPaymentAttributes = { id: 123 };
        expectedResult = service.addPaymentAttributesToCollectionIfMissing([], null, paymentAttributes, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentAttributes);
      });

      it('should return initial array if no PaymentAttributes is added', () => {
        const paymentAttributesCollection: IPaymentAttributes[] = [{ id: 123 }];
        expectedResult = service.addPaymentAttributesToCollectionIfMissing(paymentAttributesCollection, undefined, null);
        expect(expectedResult).toEqual(paymentAttributesCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
