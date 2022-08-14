import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdminTabs, getAdminTabsIdentifier } from '../admin-tabs.model';

export type EntityResponseType = HttpResponse<IAdminTabs>;
export type EntityArrayResponseType = HttpResponse<IAdminTabs[]>;

@Injectable({ providedIn: 'root' })
export class AdminTabsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/admin-tabs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(adminTabs: IAdminTabs): Observable<EntityResponseType> {
    return this.http.post<IAdminTabs>(this.resourceUrl, adminTabs, { observe: 'response' });
  }

  update(adminTabs: IAdminTabs): Observable<EntityResponseType> {
    return this.http.put<IAdminTabs>(`${this.resourceUrl}/${getAdminTabsIdentifier(adminTabs) as number}`, adminTabs, {
      observe: 'response',
    });
  }

  partialUpdate(adminTabs: IAdminTabs): Observable<EntityResponseType> {
    return this.http.patch<IAdminTabs>(`${this.resourceUrl}/${getAdminTabsIdentifier(adminTabs) as number}`, adminTabs, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdminTabs>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdminTabs[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAdminTabsToCollectionIfMissing(
    adminTabsCollection: IAdminTabs[],
    ...adminTabsToCheck: (IAdminTabs | null | undefined)[]
  ): IAdminTabs[] {
    const adminTabs: IAdminTabs[] = adminTabsToCheck.filter(isPresent);
    if (adminTabs.length > 0) {
      const adminTabsCollectionIdentifiers = adminTabsCollection.map(adminTabsItem => getAdminTabsIdentifier(adminTabsItem)!);
      const adminTabsToAdd = adminTabs.filter(adminTabsItem => {
        const adminTabsIdentifier = getAdminTabsIdentifier(adminTabsItem);
        if (adminTabsIdentifier == null || adminTabsCollectionIdentifiers.includes(adminTabsIdentifier)) {
          return false;
        }
        adminTabsCollectionIdentifiers.push(adminTabsIdentifier);
        return true;
      });
      return [...adminTabsToAdd, ...adminTabsCollection];
    }
    return adminTabsCollection;
  }
}
