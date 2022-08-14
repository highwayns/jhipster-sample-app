import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEmails, Emails } from '../emails.model';

import { EmailsService } from './emails.service';

describe('Emails Service', () => {
  let service: EmailsService;
  let httpMock: HttpTestingController;
  let elemDefault: IEmails;
  let expectedResult: IEmails | IEmails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EmailsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      key: 'AAAAAAA',
      titleEn: 'AAAAAAA',
      titleEs: 'AAAAAAA',
      contentEnContentType: 'image/png',
      contentEn: 'AAAAAAA',
      contentEsContentType: 'image/png',
      contentEs: 'AAAAAAA',
      titleRu: 'AAAAAAA',
      titleZh: 'AAAAAAA',
      contentRuContentType: 'image/png',
      contentRu: 'AAAAAAA',
      contentZhContentType: 'image/png',
      contentZh: 'AAAAAAA',
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

    it('should create a Emails', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Emails()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Emails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          key: 'BBBBBB',
          titleEn: 'BBBBBB',
          titleEs: 'BBBBBB',
          contentEn: 'BBBBBB',
          contentEs: 'BBBBBB',
          titleRu: 'BBBBBB',
          titleZh: 'BBBBBB',
          contentRu: 'BBBBBB',
          contentZh: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Emails', () => {
      const patchObject = Object.assign(
        {
          titleEs: 'BBBBBB',
          titleRu: 'BBBBBB',
          contentRu: 'BBBBBB',
          contentZh: 'BBBBBB',
        },
        new Emails()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Emails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          key: 'BBBBBB',
          titleEn: 'BBBBBB',
          titleEs: 'BBBBBB',
          contentEn: 'BBBBBB',
          contentEs: 'BBBBBB',
          titleRu: 'BBBBBB',
          titleZh: 'BBBBBB',
          contentRu: 'BBBBBB',
          contentZh: 'BBBBBB',
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

    it('should delete a Emails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEmailsToCollectionIfMissing', () => {
      it('should add a Emails to an empty array', () => {
        const emails: IEmails = { id: 123 };
        expectedResult = service.addEmailsToCollectionIfMissing([], emails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(emails);
      });

      it('should not add a Emails to an array that contains it', () => {
        const emails: IEmails = { id: 123 };
        const emailsCollection: IEmails[] = [
          {
            ...emails,
          },
          { id: 456 },
        ];
        expectedResult = service.addEmailsToCollectionIfMissing(emailsCollection, emails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Emails to an array that doesn't contain it", () => {
        const emails: IEmails = { id: 123 };
        const emailsCollection: IEmails[] = [{ id: 456 }];
        expectedResult = service.addEmailsToCollectionIfMissing(emailsCollection, emails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(emails);
      });

      it('should add only unique Emails to an array', () => {
        const emailsArray: IEmails[] = [{ id: 123 }, { id: 456 }, { id: 48379 }];
        const emailsCollection: IEmails[] = [{ id: 123 }];
        expectedResult = service.addEmailsToCollectionIfMissing(emailsCollection, ...emailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const emails: IEmails = { id: 123 };
        const emails2: IEmails = { id: 456 };
        expectedResult = service.addEmailsToCollectionIfMissing([], emails, emails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(emails);
        expect(expectedResult).toContain(emails2);
      });

      it('should accept null and undefined values', () => {
        const emails: IEmails = { id: 123 };
        expectedResult = service.addEmailsToCollectionIfMissing([], null, emails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(emails);
      });

      it('should return initial array if no Emails is added', () => {
        const emailsCollection: IEmails[] = [{ id: 123 }];
        expectedResult = service.addEmailsToCollectionIfMissing(emailsCollection, undefined, null);
        expect(expectedResult).toEqual(emailsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
