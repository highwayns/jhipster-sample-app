import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IChangeSettings, ChangeSettings } from '../change-settings.model';

import { ChangeSettingsService } from './change-settings.service';

describe('ChangeSettings Service', () => {
  let service: ChangeSettingsService;
  let httpMock: HttpTestingController;
  let elemDefault: IChangeSettings;
  let expectedResult: IChangeSettings | IChangeSettings[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ChangeSettingsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      requestContentType: 'image/png',
      request: 'AAAAAAA',
      date: currentDate,
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

    it('should create a ChangeSettings', () => {
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

      service.create(new ChangeSettings()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ChangeSettings', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          request: 'BBBBBB',
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

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ChangeSettings', () => {
      const patchObject = Object.assign(
        {
          date: currentDate.format(DATE_TIME_FORMAT),
        },
        new ChangeSettings()
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

    it('should return a list of ChangeSettings', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          request: 'BBBBBB',
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

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ChangeSettings', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addChangeSettingsToCollectionIfMissing', () => {
      it('should add a ChangeSettings to an empty array', () => {
        const changeSettings: IChangeSettings = { id: 123 };
        expectedResult = service.addChangeSettingsToCollectionIfMissing([], changeSettings);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(changeSettings);
      });

      it('should not add a ChangeSettings to an array that contains it', () => {
        const changeSettings: IChangeSettings = { id: 123 };
        const changeSettingsCollection: IChangeSettings[] = [
          {
            ...changeSettings,
          },
          { id: 456 },
        ];
        expectedResult = service.addChangeSettingsToCollectionIfMissing(changeSettingsCollection, changeSettings);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ChangeSettings to an array that doesn't contain it", () => {
        const changeSettings: IChangeSettings = { id: 123 };
        const changeSettingsCollection: IChangeSettings[] = [{ id: 456 }];
        expectedResult = service.addChangeSettingsToCollectionIfMissing(changeSettingsCollection, changeSettings);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(changeSettings);
      });

      it('should add only unique ChangeSettings to an array', () => {
        const changeSettingsArray: IChangeSettings[] = [{ id: 123 }, { id: 456 }, { id: 25685 }];
        const changeSettingsCollection: IChangeSettings[] = [{ id: 123 }];
        expectedResult = service.addChangeSettingsToCollectionIfMissing(changeSettingsCollection, ...changeSettingsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const changeSettings: IChangeSettings = { id: 123 };
        const changeSettings2: IChangeSettings = { id: 456 };
        expectedResult = service.addChangeSettingsToCollectionIfMissing([], changeSettings, changeSettings2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(changeSettings);
        expect(expectedResult).toContain(changeSettings2);
      });

      it('should accept null and undefined values', () => {
        const changeSettings: IChangeSettings = { id: 123 };
        expectedResult = service.addChangeSettingsToCollectionIfMissing([], null, changeSettings, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(changeSettings);
      });

      it('should return initial array if no ChangeSettings is added', () => {
        const changeSettingsCollection: IChangeSettings[] = [{ id: 123 }];
        expectedResult = service.addChangeSettingsToCollectionIfMissing(changeSettingsCollection, undefined, null);
        expect(expectedResult).toEqual(changeSettingsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
