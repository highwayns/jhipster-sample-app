import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILinks, Links } from '../links.model';

import { LinksService } from './links.service';

describe('Links Service', () => {
  let service: LinksService;
  let httpMock: HttpTestingController;
  let elemDefault: ILinks;
  let expectedResult: ILinks | ILinks[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LinksService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
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

    it('should create a Links', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Links()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Links', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Links', () => {
      const patchObject = Object.assign({}, new Links());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Links', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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

    it('should delete a Links', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLinksToCollectionIfMissing', () => {
      it('should add a Links to an empty array', () => {
        const links: ILinks = { id: 123 };
        expectedResult = service.addLinksToCollectionIfMissing([], links);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(links);
      });

      it('should not add a Links to an array that contains it', () => {
        const links: ILinks = { id: 123 };
        const linksCollection: ILinks[] = [
          {
            ...links,
          },
          { id: 456 },
        ];
        expectedResult = service.addLinksToCollectionIfMissing(linksCollection, links);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Links to an array that doesn't contain it", () => {
        const links: ILinks = { id: 123 };
        const linksCollection: ILinks[] = [{ id: 456 }];
        expectedResult = service.addLinksToCollectionIfMissing(linksCollection, links);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(links);
      });

      it('should add only unique Links to an array', () => {
        const linksArray: ILinks[] = [{ id: 123 }, { id: 456 }, { id: 49676 }];
        const linksCollection: ILinks[] = [{ id: 123 }];
        expectedResult = service.addLinksToCollectionIfMissing(linksCollection, ...linksArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const links: ILinks = { id: 123 };
        const links2: ILinks = { id: 456 };
        expectedResult = service.addLinksToCollectionIfMissing([], links, links2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(links);
        expect(expectedResult).toContain(links2);
      });

      it('should accept null and undefined values', () => {
        const links: ILinks = { id: 123 };
        expectedResult = service.addLinksToCollectionIfMissing([], null, links, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(links);
      });

      it('should return initial array if no Links is added', () => {
        const linksCollection: ILinks[] = [{ id: 123 }];
        expectedResult = service.addLinksToCollectionIfMissing(linksCollection, undefined, null);
        expect(expectedResult).toEqual(linksCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
