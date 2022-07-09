import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPaymentJobAttributes, PaymentJobAttributes } from '../payment-job-attributes.model';

import { PaymentJobAttributesService } from './payment-job-attributes.service';

describe('PaymentJobAttributes Service', () => {
  let service: PaymentJobAttributesService;
  let httpMock: HttpTestingController;
  let elemDefault: IPaymentJobAttributes;
  let expectedResult: IPaymentJobAttributes | IPaymentJobAttributes[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PaymentJobAttributesService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      webhookUrl: 'AAAAAAA',
      googleAnalyticsClientId: 'AAAAAAA',
      allowedParentFrameDomains: 'AAAAAAA',
      paymentPageReference: 'AAAAAAA',
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

    it('should create a PaymentJobAttributes', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PaymentJobAttributes()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PaymentJobAttributes', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          webhookUrl: 'BBBBBB',
          googleAnalyticsClientId: 'BBBBBB',
          allowedParentFrameDomains: 'BBBBBB',
          paymentPageReference: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PaymentJobAttributes', () => {
      const patchObject = Object.assign(
        {
          webhookUrl: 'BBBBBB',
          googleAnalyticsClientId: 'BBBBBB',
          allowedParentFrameDomains: 'BBBBBB',
        },
        new PaymentJobAttributes()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PaymentJobAttributes', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          webhookUrl: 'BBBBBB',
          googleAnalyticsClientId: 'BBBBBB',
          allowedParentFrameDomains: 'BBBBBB',
          paymentPageReference: 'BBBBBB',
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

    it('should delete a PaymentJobAttributes', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPaymentJobAttributesToCollectionIfMissing', () => {
      it('should add a PaymentJobAttributes to an empty array', () => {
        const paymentJobAttributes: IPaymentJobAttributes = { id: 123 };
        expectedResult = service.addPaymentJobAttributesToCollectionIfMissing([], paymentJobAttributes);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentJobAttributes);
      });

      it('should not add a PaymentJobAttributes to an array that contains it', () => {
        const paymentJobAttributes: IPaymentJobAttributes = { id: 123 };
        const paymentJobAttributesCollection: IPaymentJobAttributes[] = [
          {
            ...paymentJobAttributes,
          },
          { id: 456 },
        ];
        expectedResult = service.addPaymentJobAttributesToCollectionIfMissing(paymentJobAttributesCollection, paymentJobAttributes);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PaymentJobAttributes to an array that doesn't contain it", () => {
        const paymentJobAttributes: IPaymentJobAttributes = { id: 123 };
        const paymentJobAttributesCollection: IPaymentJobAttributes[] = [{ id: 456 }];
        expectedResult = service.addPaymentJobAttributesToCollectionIfMissing(paymentJobAttributesCollection, paymentJobAttributes);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentJobAttributes);
      });

      it('should add only unique PaymentJobAttributes to an array', () => {
        const paymentJobAttributesArray: IPaymentJobAttributes[] = [{ id: 123 }, { id: 456 }, { id: 13426 }];
        const paymentJobAttributesCollection: IPaymentJobAttributes[] = [{ id: 123 }];
        expectedResult = service.addPaymentJobAttributesToCollectionIfMissing(paymentJobAttributesCollection, ...paymentJobAttributesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const paymentJobAttributes: IPaymentJobAttributes = { id: 123 };
        const paymentJobAttributes2: IPaymentJobAttributes = { id: 456 };
        expectedResult = service.addPaymentJobAttributesToCollectionIfMissing([], paymentJobAttributes, paymentJobAttributes2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentJobAttributes);
        expect(expectedResult).toContain(paymentJobAttributes2);
      });

      it('should accept null and undefined values', () => {
        const paymentJobAttributes: IPaymentJobAttributes = { id: 123 };
        expectedResult = service.addPaymentJobAttributesToCollectionIfMissing([], null, paymentJobAttributes, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentJobAttributes);
      });

      it('should return initial array if no PaymentJobAttributes is added', () => {
        const paymentJobAttributesCollection: IPaymentJobAttributes[] = [{ id: 123 }];
        expectedResult = service.addPaymentJobAttributesToCollectionIfMissing(paymentJobAttributesCollection, undefined, null);
        expect(expectedResult).toEqual(paymentJobAttributesCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
