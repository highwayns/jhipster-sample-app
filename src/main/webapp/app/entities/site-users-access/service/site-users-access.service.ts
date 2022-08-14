import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISiteUsersAccess, getSiteUsersAccessIdentifier } from '../site-users-access.model';

export type EntityResponseType = HttpResponse<ISiteUsersAccess>;
export type EntityArrayResponseType = HttpResponse<ISiteUsersAccess[]>;

@Injectable({ providedIn: 'root' })
export class SiteUsersAccessService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/site-users-accesses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(siteUsersAccess: ISiteUsersAccess): Observable<EntityResponseType> {
    return this.http.post<ISiteUsersAccess>(this.resourceUrl, siteUsersAccess, { observe: 'response' });
  }

  update(siteUsersAccess: ISiteUsersAccess): Observable<EntityResponseType> {
    return this.http.put<ISiteUsersAccess>(
      `${this.resourceUrl}/${getSiteUsersAccessIdentifier(siteUsersAccess) as number}`,
      siteUsersAccess,
      { observe: 'response' }
    );
  }

  partialUpdate(siteUsersAccess: ISiteUsersAccess): Observable<EntityResponseType> {
    return this.http.patch<ISiteUsersAccess>(
      `${this.resourceUrl}/${getSiteUsersAccessIdentifier(siteUsersAccess) as number}`,
      siteUsersAccess,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISiteUsersAccess>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISiteUsersAccess[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSiteUsersAccessToCollectionIfMissing(
    siteUsersAccessCollection: ISiteUsersAccess[],
    ...siteUsersAccessesToCheck: (ISiteUsersAccess | null | undefined)[]
  ): ISiteUsersAccess[] {
    const siteUsersAccesses: ISiteUsersAccess[] = siteUsersAccessesToCheck.filter(isPresent);
    if (siteUsersAccesses.length > 0) {
      const siteUsersAccessCollectionIdentifiers = siteUsersAccessCollection.map(
        siteUsersAccessItem => getSiteUsersAccessIdentifier(siteUsersAccessItem)!
      );
      const siteUsersAccessesToAdd = siteUsersAccesses.filter(siteUsersAccessItem => {
        const siteUsersAccessIdentifier = getSiteUsersAccessIdentifier(siteUsersAccessItem);
        if (siteUsersAccessIdentifier == null || siteUsersAccessCollectionIdentifiers.includes(siteUsersAccessIdentifier)) {
          return false;
        }
        siteUsersAccessCollectionIdentifiers.push(siteUsersAccessIdentifier);
        return true;
      });
      return [...siteUsersAccessesToAdd, ...siteUsersAccessCollection];
    }
    return siteUsersAccessCollection;
  }
}
