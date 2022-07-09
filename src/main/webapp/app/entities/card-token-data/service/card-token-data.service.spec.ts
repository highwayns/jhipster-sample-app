import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICardTokenData, CardTokenData } from '../card-token-data.model';

import { CardTokenDataService } from './card-token-data.service';

describe('CardTokenData Service', () => {
  let service: CardTokenDataService;
  let httpMock: HttpTestingController;
  let elemDefault: ICardTokenData;
  let expectedResult: ICardTokenData | ICardTokenData[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CardTokenDataService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      token: 'AAAAAAA',
      cardExpiryMonth: 'AAAAAAA',
      cardExpiryYear: 'AAAAAAA',
      issuerReturnCode: 'AAAAAAA',
      truncatedCardNumber: 'AAAAAAA',
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

    it('should create a CardTokenData', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new CardTokenData()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CardTokenData', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          token: 'BBBBBB',
          cardExpiryMonth: 'BBBBBB',
          cardExpiryYear: 'BBBBBB',
          issuerReturnCode: 'BBBBBB',
          truncatedCardNumber: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CardTokenData', () => {
      const patchObject = Object.assign(
        {
          token: 'BBBBBB',
          cardExpiryMonth: 'BBBBBB',
          issuerReturnCode: 'BBBBBB',
        },
        new CardTokenData()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CardTokenData', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          token: 'BBBBBB',
          cardExpiryMonth: 'BBBBBB',
          cardExpiryYear: 'BBBBBB',
          issuerReturnCode: 'BBBBBB',
          truncatedCardNumber: 'BBBBBB',
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

    it('should delete a CardTokenData', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCardTokenDataToCollectionIfMissing', () => {
      it('should add a CardTokenData to an empty array', () => {
        const cardTokenData: ICardTokenData = { id: 123 };
        expectedResult = service.addCardTokenDataToCollectionIfMissing([], cardTokenData);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cardTokenData);
      });

      it('should not add a CardTokenData to an array that contains it', () => {
        const cardTokenData: ICardTokenData = { id: 123 };
        const cardTokenDataCollection: ICardTokenData[] = [
          {
            ...cardTokenData,
          },
          { id: 456 },
        ];
        expectedResult = service.addCardTokenDataToCollectionIfMissing(cardTokenDataCollection, cardTokenData);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CardTokenData to an array that doesn't contain it", () => {
        const cardTokenData: ICardTokenData = { id: 123 };
        const cardTokenDataCollection: ICardTokenData[] = [{ id: 456 }];
        expectedResult = service.addCardTokenDataToCollectionIfMissing(cardTokenDataCollection, cardTokenData);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cardTokenData);
      });

      it('should add only unique CardTokenData to an array', () => {
        const cardTokenDataArray: ICardTokenData[] = [{ id: 123 }, { id: 456 }, { id: 41275 }];
        const cardTokenDataCollection: ICardTokenData[] = [{ id: 123 }];
        expectedResult = service.addCardTokenDataToCollectionIfMissing(cardTokenDataCollection, ...cardTokenDataArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cardTokenData: ICardTokenData = { id: 123 };
        const cardTokenData2: ICardTokenData = { id: 456 };
        expectedResult = service.addCardTokenDataToCollectionIfMissing([], cardTokenData, cardTokenData2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cardTokenData);
        expect(expectedResult).toContain(cardTokenData2);
      });

      it('should accept null and undefined values', () => {
        const cardTokenData: ICardTokenData = { id: 123 };
        expectedResult = service.addCardTokenDataToCollectionIfMissing([], null, cardTokenData, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cardTokenData);
      });

      it('should return initial array if no CardTokenData is added', () => {
        const cardTokenDataCollection: ICardTokenData[] = [{ id: 123 }];
        expectedResult = service.addCardTokenDataToCollectionIfMissing(cardTokenDataCollection, undefined, null);
        expect(expectedResult).toEqual(cardTokenDataCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
