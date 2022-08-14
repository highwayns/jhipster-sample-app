import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { YesNo } from 'app/entities/enumerations/yes-no.model';
import { IBitcoinAddresses, BitcoinAddresses } from '../bitcoin-addresses.model';

import { BitcoinAddressesService } from './bitcoin-addresses.service';

describe('BitcoinAddresses Service', () => {
  let service: BitcoinAddressesService;
  let httpMock: HttpTestingController;
  let elemDefault: IBitcoinAddresses;
  let expectedResult: IBitcoinAddresses | IBitcoinAddresses[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BitcoinAddressesService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      address: 'AAAAAAA',
      date: currentDate,
      systemAddress: YesNo.Y,
      hotWallet: YesNo.Y,
      warmWallet: YesNo.Y,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          date: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a BitcoinAddresses', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          date: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.create(new BitcoinAddresses()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BitcoinAddresses', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          address: 'BBBBBB',
          date: currentDate.format(DATE_TIME_FORMAT),
          systemAddress: 'BBBBBB',
          hotWallet: 'BBBBBB',
          warmWallet: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BitcoinAddresses', () => {
      const patchObject = Object.assign(
        {
          date: currentDate.format(DATE_TIME_FORMAT),
        },
        new BitcoinAddresses()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BitcoinAddresses', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          address: 'BBBBBB',
          date: currentDate.format(DATE_TIME_FORMAT),
          systemAddress: 'BBBBBB',
          hotWallet: 'BBBBBB',
          warmWallet: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a BitcoinAddresses', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBitcoinAddressesToCollectionIfMissing', () => {
      it('should add a BitcoinAddresses to an empty array', () => {
        const bitcoinAddresses: IBitcoinAddresses = { id: 123 };
        expectedResult = service.addBitcoinAddressesToCollectionIfMissing([], bitcoinAddresses);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bitcoinAddresses);
      });

      it('should not add a BitcoinAddresses to an array that contains it', () => {
        const bitcoinAddresses: IBitcoinAddresses = { id: 123 };
        const bitcoinAddressesCollection: IBitcoinAddresses[] = [
          {
            ...bitcoinAddresses,
          },
          { id: 456 },
        ];
        expectedResult = service.addBitcoinAddressesToCollectionIfMissing(bitcoinAddressesCollection, bitcoinAddresses);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BitcoinAddresses to an array that doesn't contain it", () => {
        const bitcoinAddresses: IBitcoinAddresses = { id: 123 };
        const bitcoinAddressesCollection: IBitcoinAddresses[] = [{ id: 456 }];
        expectedResult = service.addBitcoinAddressesToCollectionIfMissing(bitcoinAddressesCollection, bitcoinAddresses);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bitcoinAddresses);
      });

      it('should add only unique BitcoinAddresses to an array', () => {
        const bitcoinAddressesArray: IBitcoinAddresses[] = [{ id: 123 }, { id: 456 }, { id: 12821 }];
        const bitcoinAddressesCollection: IBitcoinAddresses[] = [{ id: 123 }];
        expectedResult = service.addBitcoinAddressesToCollectionIfMissing(bitcoinAddressesCollection, ...bitcoinAddressesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bitcoinAddresses: IBitcoinAddresses = { id: 123 };
        const bitcoinAddresses2: IBitcoinAddresses = { id: 456 };
        expectedResult = service.addBitcoinAddressesToCollectionIfMissing([], bitcoinAddresses, bitcoinAddresses2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bitcoinAddresses);
        expect(expectedResult).toContain(bitcoinAddresses2);
      });

      it('should accept null and undefined values', () => {
        const bitcoinAddresses: IBitcoinAddresses = { id: 123 };
        expectedResult = service.addBitcoinAddressesToCollectionIfMissing([], null, bitcoinAddresses, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bitcoinAddresses);
      });

      it('should return initial array if no BitcoinAddresses is added', () => {
        const bitcoinAddressesCollection: IBitcoinAddresses[] = [{ id: 123 }];
        expectedResult = service.addBitcoinAddressesToCollectionIfMissing(bitcoinAddressesCollection, undefined, null);
        expect(expectedResult).toEqual(bitcoinAddressesCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
