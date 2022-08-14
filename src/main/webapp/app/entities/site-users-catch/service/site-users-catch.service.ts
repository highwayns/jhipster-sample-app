import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISiteUsersCatch, getSiteUsersCatchIdentifier } from '../site-users-catch.model';

export type EntityResponseType = HttpResponse<ISiteUsersCatch>;
export type EntityArrayResponseType = HttpResponse<ISiteUsersCatch[]>;

@Injectable({ providedIn: 'root' })
export class SiteUsersCatchService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/site-users-catches');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(siteUsersCatch: ISiteUsersCatch): Observable<EntityResponseType> {
    return this.http.post<ISiteUsersCatch>(this.resourceUrl, siteUsersCatch, { observe: 'response' });
  }

  update(siteUsersCatch: ISiteUsersCatch): Observable<EntityResponseType> {
    return this.http.put<ISiteUsersCatch>(`${this.resourceUrl}/${getSiteUsersCatchIdentifier(siteUsersCatch) as number}`, siteUsersCatch, {
      observe: 'response',
    });
  }

  partialUpdate(siteUsersCatch: ISiteUsersCatch): Observable<EntityResponseType> {
    return this.http.patch<ISiteUsersCatch>(
      `${this.resourceUrl}/${getSiteUsersCatchIdentifier(siteUsersCatch) as number}`,
      siteUsersCatch,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISiteUsersCatch>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISiteUsersCatch[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSiteUsersCatchToCollectionIfMissing(
    siteUsersCatchCollection: ISiteUsersCatch[],
    ...siteUsersCatchesToCheck: (ISiteUsersCatch | null | undefined)[]
  ): ISiteUsersCatch[] {
    const siteUsersCatches: ISiteUsersCatch[] = siteUsersCatchesToCheck.filter(isPresent);
    if (siteUsersCatches.length > 0) {
      const siteUsersCatchCollectionIdentifiers = siteUsersCatchCollection.map(
        siteUsersCatchItem => getSiteUsersCatchIdentifier(siteUsersCatchItem)!
      );
      const siteUsersCatchesToAdd = siteUsersCatches.filter(siteUsersCatchItem => {
        const siteUsersCatchIdentifier = getSiteUsersCatchIdentifier(siteUsersCatchItem);
        if (siteUsersCatchIdentifier == null || siteUsersCatchCollectionIdentifiers.includes(siteUsersCatchIdentifier)) {
          return false;
        }
        siteUsersCatchCollectionIdentifiers.push(siteUsersCatchIdentifier);
        return true;
      });
      return [...siteUsersCatchesToAdd, ...siteUsersCatchCollection];
    }
    return siteUsersCatchCollection;
  }
}
