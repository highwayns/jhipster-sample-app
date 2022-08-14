import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRequestStatus, RequestStatus } from '../request-status.model';

import { RequestStatusService } from './request-status.service';

describe('RequestStatus Service', () => {
  let service: RequestStatusService;
  let httpMock: HttpTestingController;
  let elemDefault: IRequestStatus;
  let expectedResult: IRequestStatus | IRequestStatus[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RequestStatusService);
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

    it('should create a RequestStatus', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new RequestStatus()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RequestStatus', () => {
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

    it('should partial update a RequestStatus', () => {
      const patchObject = Object.assign({}, new RequestStatus());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RequestStatus', () => {
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

    it('should delete a RequestStatus', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRequestStatusToCollectionIfMissing', () => {
      it('should add a RequestStatus to an empty array', () => {
        const requestStatus: IRequestStatus = { id: 123 };
        expectedResult = service.addRequestStatusToCollectionIfMissing([], requestStatus);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requestStatus);
      });

      it('should not add a RequestStatus to an array that contains it', () => {
        const requestStatus: IRequestStatus = { id: 123 };
        const requestStatusCollection: IRequestStatus[] = [
          {
            ...requestStatus,
          },
          { id: 456 },
        ];
        expectedResult = service.addRequestStatusToCollectionIfMissing(requestStatusCollection, requestStatus);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RequestStatus to an array that doesn't contain it", () => {
        const requestStatus: IRequestStatus = { id: 123 };
        const requestStatusCollection: IRequestStatus[] = [{ id: 456 }];
        expectedResult = service.addRequestStatusToCollectionIfMissing(requestStatusCollection, requestStatus);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requestStatus);
      });

      it('should add only unique RequestStatus to an array', () => {
        const requestStatusArray: IRequestStatus[] = [{ id: 123 }, { id: 456 }, { id: 53363 }];
        const requestStatusCollection: IRequestStatus[] = [{ id: 123 }];
        expectedResult = service.addRequestStatusToCollectionIfMissing(requestStatusCollection, ...requestStatusArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const requestStatus: IRequestStatus = { id: 123 };
        const requestStatus2: IRequestStatus = { id: 456 };
        expectedResult = service.addRequestStatusToCollectionIfMissing([], requestStatus, requestStatus2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requestStatus);
        expect(expectedResult).toContain(requestStatus2);
      });

      it('should accept null and undefined values', () => {
        const requestStatus: IRequestStatus = { id: 123 };
        expectedResult = service.addRequestStatusToCollectionIfMissing([], null, requestStatus, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requestStatus);
      });

      it('should return initial array if no RequestStatus is added', () => {
        const requestStatusCollection: IRequestStatus[] = [{ id: 123 }];
        expectedResult = service.addRequestStatusToCollectionIfMissing(requestStatusCollection, undefined, null);
        expect(expectedResult).toEqual(requestStatusCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
