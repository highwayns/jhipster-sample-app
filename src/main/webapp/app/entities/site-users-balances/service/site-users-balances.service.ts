import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISiteUsersBalances, getSiteUsersBalancesIdentifier } from '../site-users-balances.model';

export type EntityResponseType = HttpResponse<ISiteUsersBalances>;
export type EntityArrayResponseType = HttpResponse<ISiteUsersBalances[]>;

@Injectable({ providedIn: 'root' })
export class SiteUsersBalancesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/site-users-balances');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(siteUsersBalances: ISiteUsersBalances): Observable<EntityResponseType> {
    return this.http.post<ISiteUsersBalances>(this.resourceUrl, siteUsersBalances, { observe: 'response' });
  }

  update(siteUsersBalances: ISiteUsersBalances): Observable<EntityResponseType> {
    return this.http.put<ISiteUsersBalances>(
      `${this.resourceUrl}/${getSiteUsersBalancesIdentifier(siteUsersBalances) as number}`,
      siteUsersBalances,
      { observe: 'response' }
    );
  }

  partialUpdate(siteUsersBalances: ISiteUsersBalances): Observable<EntityResponseType> {
    return this.http.patch<ISiteUsersBalances>(
      `${this.resourceUrl}/${getSiteUsersBalancesIdentifier(siteUsersBalances) as number}`,
      siteUsersBalances,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISiteUsersBalances>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISiteUsersBalances[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSiteUsersBalancesToCollectionIfMissing(
    siteUsersBalancesCollection: ISiteUsersBalances[],
    ...siteUsersBalancesToCheck: (ISiteUsersBalances | null | undefined)[]
  ): ISiteUsersBalances[] {
    const siteUsersBalances: ISiteUsersBalances[] = siteUsersBalancesToCheck.filter(isPresent);
    if (siteUsersBalances.length > 0) {
      const siteUsersBalancesCollectionIdentifiers = siteUsersBalancesCollection.map(
        siteUsersBalancesItem => getSiteUsersBalancesIdentifier(siteUsersBalancesItem)!
      );
      const siteUsersBalancesToAdd = siteUsersBalances.filter(siteUsersBalancesItem => {
        const siteUsersBalancesIdentifier = getSiteUsersBalancesIdentifier(siteUsersBalancesItem);
        if (siteUsersBalancesIdentifier == null || siteUsersBalancesCollectionIdentifiers.includes(siteUsersBalancesIdentifier)) {
          return false;
        }
        siteUsersBalancesCollectionIdentifiers.push(siteUsersBalancesIdentifier);
        return true;
      });
      return [...siteUsersBalancesToAdd, ...siteUsersBalancesCollection];
    }
    return siteUsersBalancesCollection;
  }
}
