import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IInnodbLockMonitor, InnodbLockMonitor } from '../innodb-lock-monitor.model';

import { InnodbLockMonitorService } from './innodb-lock-monitor.service';

describe('InnodbLockMonitor Service', () => {
  let service: InnodbLockMonitorService;
  let httpMock: HttpTestingController;
  let elemDefault: IInnodbLockMonitor;
  let expectedResult: IInnodbLockMonitor | IInnodbLockMonitor[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(InnodbLockMonitorService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      a: 0,
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

    it('should create a InnodbLockMonitor', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new InnodbLockMonitor()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a InnodbLockMonitor', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          a: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a InnodbLockMonitor', () => {
      const patchObject = Object.assign(
        {
          a: 1,
        },
        new InnodbLockMonitor()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of InnodbLockMonitor', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          a: 1,
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

    it('should delete a InnodbLockMonitor', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addInnodbLockMonitorToCollectionIfMissing', () => {
      it('should add a InnodbLockMonitor to an empty array', () => {
        const innodbLockMonitor: IInnodbLockMonitor = { id: 123 };
        expectedResult = service.addInnodbLockMonitorToCollectionIfMissing([], innodbLockMonitor);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(innodbLockMonitor);
      });

      it('should not add a InnodbLockMonitor to an array that contains it', () => {
        const innodbLockMonitor: IInnodbLockMonitor = { id: 123 };
        const innodbLockMonitorCollection: IInnodbLockMonitor[] = [
          {
            ...innodbLockMonitor,
          },
          { id: 456 },
        ];
        expectedResult = service.addInnodbLockMonitorToCollectionIfMissing(innodbLockMonitorCollection, innodbLockMonitor);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a InnodbLockMonitor to an array that doesn't contain it", () => {
        const innodbLockMonitor: IInnodbLockMonitor = { id: 123 };
        const innodbLockMonitorCollection: IInnodbLockMonitor[] = [{ id: 456 }];
        expectedResult = service.addInnodbLockMonitorToCollectionIfMissing(innodbLockMonitorCollection, innodbLockMonitor);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(innodbLockMonitor);
      });

      it('should add only unique InnodbLockMonitor to an array', () => {
        const innodbLockMonitorArray: IInnodbLockMonitor[] = [{ id: 123 }, { id: 456 }, { id: 92721 }];
        const innodbLockMonitorCollection: IInnodbLockMonitor[] = [{ id: 123 }];
        expectedResult = service.addInnodbLockMonitorToCollectionIfMissing(innodbLockMonitorCollection, ...innodbLockMonitorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const innodbLockMonitor: IInnodbLockMonitor = { id: 123 };
        const innodbLockMonitor2: IInnodbLockMonitor = { id: 456 };
        expectedResult = service.addInnodbLockMonitorToCollectionIfMissing([], innodbLockMonitor, innodbLockMonitor2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(innodbLockMonitor);
        expect(expectedResult).toContain(innodbLockMonitor2);
      });

      it('should accept null and undefined values', () => {
        const innodbLockMonitor: IInnodbLockMonitor = { id: 123 };
        expectedResult = service.addInnodbLockMonitorToCollectionIfMissing([], null, innodbLockMonitor, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(innodbLockMonitor);
      });

      it('should return initial array if no InnodbLockMonitor is added', () => {
        const innodbLockMonitorCollection: IInnodbLockMonitor[] = [{ id: 123 }];
        expectedResult = service.addInnodbLockMonitorToCollectionIfMissing(innodbLockMonitorCollection, undefined, null);
        expect(expectedResult).toEqual(innodbLockMonitorCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
