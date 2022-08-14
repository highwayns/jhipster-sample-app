import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IHistoryActions, HistoryActions } from '../history-actions.model';

import { HistoryActionsService } from './history-actions.service';

describe('HistoryActions Service', () => {
  let service: HistoryActionsService;
  let httpMock: HttpTestingController;
  let elemDefault: IHistoryActions;
  let expectedResult: IHistoryActions | IHistoryActions[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(HistoryActionsService);
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

    it('should create a HistoryActions', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new HistoryActions()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a HistoryActions', () => {
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

    it('should partial update a HistoryActions', () => {
      const patchObject = Object.assign(
        {
          nameEn: 'BBBBBB',
          nameRu: 'BBBBBB',
        },
        new HistoryActions()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of HistoryActions', () => {
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

    it('should delete a HistoryActions', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addHistoryActionsToCollectionIfMissing', () => {
      it('should add a HistoryActions to an empty array', () => {
        const historyActions: IHistoryActions = { id: 123 };
        expectedResult = service.addHistoryActionsToCollectionIfMissing([], historyActions);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(historyActions);
      });

      it('should not add a HistoryActions to an array that contains it', () => {
        const historyActions: IHistoryActions = { id: 123 };
        const historyActionsCollection: IHistoryActions[] = [
          {
            ...historyActions,
          },
          { id: 456 },
        ];
        expectedResult = service.addHistoryActionsToCollectionIfMissing(historyActionsCollection, historyActions);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a HistoryActions to an array that doesn't contain it", () => {
        const historyActions: IHistoryActions = { id: 123 };
        const historyActionsCollection: IHistoryActions[] = [{ id: 456 }];
        expectedResult = service.addHistoryActionsToCollectionIfMissing(historyActionsCollection, historyActions);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(historyActions);
      });

      it('should add only unique HistoryActions to an array', () => {
        const historyActionsArray: IHistoryActions[] = [{ id: 123 }, { id: 456 }, { id: 77902 }];
        const historyActionsCollection: IHistoryActions[] = [{ id: 123 }];
        expectedResult = service.addHistoryActionsToCollectionIfMissing(historyActionsCollection, ...historyActionsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const historyActions: IHistoryActions = { id: 123 };
        const historyActions2: IHistoryActions = { id: 456 };
        expectedResult = service.addHistoryActionsToCollectionIfMissing([], historyActions, historyActions2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(historyActions);
        expect(expectedResult).toContain(historyActions2);
      });

      it('should accept null and undefined values', () => {
        const historyActions: IHistoryActions = { id: 123 };
        expectedResult = service.addHistoryActionsToCollectionIfMissing([], null, historyActions, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(historyActions);
      });

      it('should return initial array if no HistoryActions is added', () => {
        const historyActionsCollection: IHistoryActions[] = [{ id: 123 }];
        expectedResult = service.addHistoryActionsToCollectionIfMissing(historyActionsCollection, undefined, null);
        expect(expectedResult).toEqual(historyActionsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
