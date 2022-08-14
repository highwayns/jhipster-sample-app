import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmails, getEmailsIdentifier } from '../emails.model';

export type EntityResponseType = HttpResponse<IEmails>;
export type EntityArrayResponseType = HttpResponse<IEmails[]>;

@Injectable({ providedIn: 'root' })
export class EmailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/emails');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(emails: IEmails): Observable<EntityResponseType> {
    return this.http.post<IEmails>(this.resourceUrl, emails, { observe: 'response' });
  }

  update(emails: IEmails): Observable<EntityResponseType> {
    return this.http.put<IEmails>(`${this.resourceUrl}/${getEmailsIdentifier(emails) as number}`, emails, { observe: 'response' });
  }

  partialUpdate(emails: IEmails): Observable<EntityResponseType> {
    return this.http.patch<IEmails>(`${this.resourceUrl}/${getEmailsIdentifier(emails) as number}`, emails, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEmailsToCollectionIfMissing(emailsCollection: IEmails[], ...emailsToCheck: (IEmails | null | undefined)[]): IEmails[] {
    const emails: IEmails[] = emailsToCheck.filter(isPresent);
    if (emails.length > 0) {
      const emailsCollectionIdentifiers = emailsCollection.map(emailsItem => getEmailsIdentifier(emailsItem)!);
      const emailsToAdd = emails.filter(emailsItem => {
        const emailsIdentifier = getEmailsIdentifier(emailsItem);
        if (emailsIdentifier == null || emailsCollectionIdentifiers.includes(emailsIdentifier)) {
          return false;
        }
        emailsCollectionIdentifiers.push(emailsIdentifier);
        return true;
      });
      return [...emailsToAdd, ...emailsCollection];
    }
    return emailsCollection;
  }
}
