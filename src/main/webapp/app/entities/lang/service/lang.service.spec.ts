import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILang, Lang } from '../lang.model';

import { LangService } from './lang.service';

describe('Lang Service', () => {
  let service: LangService;
  let httpMock: HttpTestingController;
  let elemDefault: ILang;
  let expectedResult: ILang | ILang[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LangService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      key: 'AAAAAAA',
      esp: 'AAAAAAA',
      eng: 'AAAAAAA',
      order: 'AAAAAAA',
      pId: 0,
      zh: 'AAAAAAA',
      ru: 'AAAAAAA',
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

    it('should create a Lang', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Lang()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Lang', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          key: 'BBBBBB',
          esp: 'BBBBBB',
          eng: 'BBBBBB',
          order: 'BBBBBB',
          pId: 1,
          zh: 'BBBBBB',
          ru: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Lang', () => {
      const patchObject = Object.assign(
        {
          key: 'BBBBBB',
          esp: 'BBBBBB',
          eng: 'BBBBBB',
          order: 'BBBBBB',
          zh: 'BBBBBB',
        },
        new Lang()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Lang', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          key: 'BBBBBB',
          esp: 'BBBBBB',
          eng: 'BBBBBB',
          order: 'BBBBBB',
          pId: 1,
          zh: 'BBBBBB',
          ru: 'BBBBBB',
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

    it('should delete a Lang', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLangToCollectionIfMissing', () => {
      it('should add a Lang to an empty array', () => {
        const lang: ILang = { id: 123 };
        expectedResult = service.addLangToCollectionIfMissing([], lang);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(lang);
      });

      it('should not add a Lang to an array that contains it', () => {
        const lang: ILang = { id: 123 };
        const langCollection: ILang[] = [
          {
            ...lang,
          },
          { id: 456 },
        ];
        expectedResult = service.addLangToCollectionIfMissing(langCollection, lang);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Lang to an array that doesn't contain it", () => {
        const lang: ILang = { id: 123 };
        const langCollection: ILang[] = [{ id: 456 }];
        expectedResult = service.addLangToCollectionIfMissing(langCollection, lang);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(lang);
      });

      it('should add only unique Lang to an array', () => {
        const langArray: ILang[] = [{ id: 123 }, { id: 456 }, { id: 78175 }];
        const langCollection: ILang[] = [{ id: 123 }];
        expectedResult = service.addLangToCollectionIfMissing(langCollection, ...langArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const lang: ILang = { id: 123 };
        const lang2: ILang = { id: 456 };
        expectedResult = service.addLangToCollectionIfMissing([], lang, lang2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(lang);
        expect(expectedResult).toContain(lang2);
      });

      it('should accept null and undefined values', () => {
        const lang: ILang = { id: 123 };
        expectedResult = service.addLangToCollectionIfMissing([], null, lang, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(lang);
      });

      it('should return initial array if no Lang is added', () => {
        const langCollection: ILang[] = [{ id: 123 }];
        expectedResult = service.addLangToCollectionIfMissing(langCollection, undefined, null);
        expect(expectedResult).toEqual(langCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
