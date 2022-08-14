import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISettingsFiles, SettingsFiles } from '../settings-files.model';

import { SettingsFilesService } from './settings-files.service';

describe('SettingsFiles Service', () => {
  let service: SettingsFilesService;
  let httpMock: HttpTestingController;
  let elemDefault: ISettingsFiles;
  let expectedResult: ISettingsFiles | ISettingsFiles[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SettingsFilesService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      fId: 0,
      ext: 'AAAAAAA',
      dir: 'AAAAAAA',
      url: 'AAAAAAA',
      oldName: 'AAAAAAA',
      fieldName: 'AAAAAAA',
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

    it('should create a SettingsFiles', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new SettingsFiles()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SettingsFiles', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          fId: 1,
          ext: 'BBBBBB',
          dir: 'BBBBBB',
          url: 'BBBBBB',
          oldName: 'BBBBBB',
          fieldName: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SettingsFiles', () => {
      const patchObject = Object.assign(
        {
          fId: 1,
          oldName: 'BBBBBB',
        },
        new SettingsFiles()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SettingsFiles', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          fId: 1,
          ext: 'BBBBBB',
          dir: 'BBBBBB',
          url: 'BBBBBB',
          oldName: 'BBBBBB',
          fieldName: 'BBBBBB',
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

    it('should delete a SettingsFiles', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSettingsFilesToCollectionIfMissing', () => {
      it('should add a SettingsFiles to an empty array', () => {
        const settingsFiles: ISettingsFiles = { id: 123 };
        expectedResult = service.addSettingsFilesToCollectionIfMissing([], settingsFiles);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(settingsFiles);
      });

      it('should not add a SettingsFiles to an array that contains it', () => {
        const settingsFiles: ISettingsFiles = { id: 123 };
        const settingsFilesCollection: ISettingsFiles[] = [
          {
            ...settingsFiles,
          },
          { id: 456 },
        ];
        expectedResult = service.addSettingsFilesToCollectionIfMissing(settingsFilesCollection, settingsFiles);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SettingsFiles to an array that doesn't contain it", () => {
        const settingsFiles: ISettingsFiles = { id: 123 };
        const settingsFilesCollection: ISettingsFiles[] = [{ id: 456 }];
        expectedResult = service.addSettingsFilesToCollectionIfMissing(settingsFilesCollection, settingsFiles);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(settingsFiles);
      });

      it('should add only unique SettingsFiles to an array', () => {
        const settingsFilesArray: ISettingsFiles[] = [{ id: 123 }, { id: 456 }, { id: 82373 }];
        const settingsFilesCollection: ISettingsFiles[] = [{ id: 123 }];
        expectedResult = service.addSettingsFilesToCollectionIfMissing(settingsFilesCollection, ...settingsFilesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const settingsFiles: ISettingsFiles = { id: 123 };
        const settingsFiles2: ISettingsFiles = { id: 456 };
        expectedResult = service.addSettingsFilesToCollectionIfMissing([], settingsFiles, settingsFiles2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(settingsFiles);
        expect(expectedResult).toContain(settingsFiles2);
      });

      it('should accept null and undefined values', () => {
        const settingsFiles: ISettingsFiles = { id: 123 };
        expectedResult = service.addSettingsFilesToCollectionIfMissing([], null, settingsFiles, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(settingsFiles);
      });

      it('should return initial array if no SettingsFiles is added', () => {
        const settingsFilesCollection: ISettingsFiles[] = [{ id: 123 }];
        expectedResult = service.addSettingsFilesToCollectionIfMissing(settingsFilesCollection, undefined, null);
        expect(expectedResult).toEqual(settingsFilesCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
