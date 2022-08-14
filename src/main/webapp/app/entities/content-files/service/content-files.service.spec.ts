import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IContentFiles, ContentFiles } from '../content-files.model';

import { ContentFilesService } from './content-files.service';

describe('ContentFiles Service', () => {
  let service: ContentFilesService;
  let httpMock: HttpTestingController;
  let elemDefault: IContentFiles;
  let expectedResult: IContentFiles | IContentFiles[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ContentFilesService);
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

    it('should create a ContentFiles', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ContentFiles()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ContentFiles', () => {
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

    it('should partial update a ContentFiles', () => {
      const patchObject = Object.assign(
        {
          fId: 1,
          ext: 'BBBBBB',
        },
        new ContentFiles()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ContentFiles', () => {
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

    it('should delete a ContentFiles', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addContentFilesToCollectionIfMissing', () => {
      it('should add a ContentFiles to an empty array', () => {
        const contentFiles: IContentFiles = { id: 123 };
        expectedResult = service.addContentFilesToCollectionIfMissing([], contentFiles);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contentFiles);
      });

      it('should not add a ContentFiles to an array that contains it', () => {
        const contentFiles: IContentFiles = { id: 123 };
        const contentFilesCollection: IContentFiles[] = [
          {
            ...contentFiles,
          },
          { id: 456 },
        ];
        expectedResult = service.addContentFilesToCollectionIfMissing(contentFilesCollection, contentFiles);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ContentFiles to an array that doesn't contain it", () => {
        const contentFiles: IContentFiles = { id: 123 };
        const contentFilesCollection: IContentFiles[] = [{ id: 456 }];
        expectedResult = service.addContentFilesToCollectionIfMissing(contentFilesCollection, contentFiles);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contentFiles);
      });

      it('should add only unique ContentFiles to an array', () => {
        const contentFilesArray: IContentFiles[] = [{ id: 123 }, { id: 456 }, { id: 97054 }];
        const contentFilesCollection: IContentFiles[] = [{ id: 123 }];
        expectedResult = service.addContentFilesToCollectionIfMissing(contentFilesCollection, ...contentFilesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const contentFiles: IContentFiles = { id: 123 };
        const contentFiles2: IContentFiles = { id: 456 };
        expectedResult = service.addContentFilesToCollectionIfMissing([], contentFiles, contentFiles2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contentFiles);
        expect(expectedResult).toContain(contentFiles2);
      });

      it('should accept null and undefined values', () => {
        const contentFiles: IContentFiles = { id: 123 };
        expectedResult = service.addContentFilesToCollectionIfMissing([], null, contentFiles, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contentFiles);
      });

      it('should return initial array if no ContentFiles is added', () => {
        const contentFilesCollection: IContentFiles[] = [{ id: 123 }];
        expectedResult = service.addContentFilesToCollectionIfMissing(contentFilesCollection, undefined, null);
        expect(expectedResult).toEqual(contentFilesCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
