import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITokenisedCard, TokenisedCard } from '../tokenised-card.model';

import { TokenisedCardService } from './tokenised-card.service';

describe('TokenisedCard Service', () => {
  let service: TokenisedCardService;
  let httpMock: HttpTestingController;
  let elemDefault: ITokenisedCard;
  let expectedResult: ITokenisedCard | ITokenisedCard[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TokenisedCardService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      token: 'AAAAAAA',
      cardExpiryMonth: 'AAAAAAA',
      cardExpiryYear: 'AAAAAAA',
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

    it('should create a TokenisedCard', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new TokenisedCard()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TokenisedCard', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          token: 'BBBBBB',
          cardExpiryMonth: 'BBBBBB',
          cardExpiryYear: 'BBBBBB',
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

    it('should partial update a TokenisedCard', () => {
      const patchObject = Object.assign(
        {
          token: 'BBBBBB',
          cardExpiryMonth: 'BBBBBB',
          truncatedCardNumber: 'BBBBBB',
        },
        new TokenisedCard()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TokenisedCard', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          token: 'BBBBBB',
          cardExpiryMonth: 'BBBBBB',
          cardExpiryYear: 'BBBBBB',
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

    it('should delete a TokenisedCard', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTokenisedCardToCollectionIfMissing', () => {
      it('should add a TokenisedCard to an empty array', () => {
        const tokenisedCard: ITokenisedCard = { id: 123 };
        expectedResult = service.addTokenisedCardToCollectionIfMissing([], tokenisedCard);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tokenisedCard);
      });

      it('should not add a TokenisedCard to an array that contains it', () => {
        const tokenisedCard: ITokenisedCard = { id: 123 };
        const tokenisedCardCollection: ITokenisedCard[] = [
          {
            ...tokenisedCard,
          },
          { id: 456 },
        ];
        expectedResult = service.addTokenisedCardToCollectionIfMissing(tokenisedCardCollection, tokenisedCard);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TokenisedCard to an array that doesn't contain it", () => {
        const tokenisedCard: ITokenisedCard = { id: 123 };
        const tokenisedCardCollection: ITokenisedCard[] = [{ id: 456 }];
        expectedResult = service.addTokenisedCardToCollectionIfMissing(tokenisedCardCollection, tokenisedCard);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tokenisedCard);
      });

      it('should add only unique TokenisedCard to an array', () => {
        const tokenisedCardArray: ITokenisedCard[] = [{ id: 123 }, { id: 456 }, { id: 14806 }];
        const tokenisedCardCollection: ITokenisedCard[] = [{ id: 123 }];
        expectedResult = service.addTokenisedCardToCollectionIfMissing(tokenisedCardCollection, ...tokenisedCardArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tokenisedCard: ITokenisedCard = { id: 123 };
        const tokenisedCard2: ITokenisedCard = { id: 456 };
        expectedResult = service.addTokenisedCardToCollectionIfMissing([], tokenisedCard, tokenisedCard2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tokenisedCard);
        expect(expectedResult).toContain(tokenisedCard2);
      });

      it('should accept null and undefined values', () => {
        const tokenisedCard: ITokenisedCard = { id: 123 };
        expectedResult = service.addTokenisedCardToCollectionIfMissing([], null, tokenisedCard, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tokenisedCard);
      });

      it('should return initial array if no TokenisedCard is added', () => {
        const tokenisedCardCollection: ITokenisedCard[] = [{ id: 123 }];
        expectedResult = service.addTokenisedCardToCollectionIfMissing(tokenisedCardCollection, undefined, null);
        expect(expectedResult).toEqual(tokenisedCardCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
