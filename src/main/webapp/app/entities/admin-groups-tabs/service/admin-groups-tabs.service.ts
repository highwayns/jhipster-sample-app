import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdminGroupsTabs, getAdminGroupsTabsIdentifier } from '../admin-groups-tabs.model';

export type EntityResponseType = HttpResponse<IAdminGroupsTabs>;
export type EntityArrayResponseType = HttpResponse<IAdminGroupsTabs[]>;

@Injectable({ providedIn: 'root' })
export class AdminGroupsTabsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/admin-groups-tabs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(adminGroupsTabs: IAdminGroupsTabs): Observable<EntityResponseType> {
    return this.http.post<IAdminGroupsTabs>(this.resourceUrl, adminGroupsTabs, { observe: 'response' });
  }

  update(adminGroupsTabs: IAdminGroupsTabs): Observable<EntityResponseType> {
    return this.http.put<IAdminGroupsTabs>(
      `${this.resourceUrl}/${getAdminGroupsTabsIdentifier(adminGroupsTabs) as number}`,
      adminGroupsTabs,
      { observe: 'response' }
    );
  }

  partialUpdate(adminGroupsTabs: IAdminGroupsTabs): Observable<EntityResponseType> {
    return this.http.patch<IAdminGroupsTabs>(
      `${this.resourceUrl}/${getAdminGroupsTabsIdentifier(adminGroupsTabs) as number}`,
      adminGroupsTabs,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdminGroupsTabs>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdminGroupsTabs[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAdminGroupsTabsToCollectionIfMissing(
    adminGroupsTabsCollection: IAdminGroupsTabs[],
    ...adminGroupsTabsToCheck: (IAdminGroupsTabs | null | undefined)[]
  ): IAdminGroupsTabs[] {
    const adminGroupsTabs: IAdminGroupsTabs[] = adminGroupsTabsToCheck.filter(isPresent);
    if (adminGroupsTabs.length > 0) {
      const adminGroupsTabsCollectionIdentifiers = adminGroupsTabsCollection.map(
        adminGroupsTabsItem => getAdminGroupsTabsIdentifier(adminGroupsTabsItem)!
      );
      const adminGroupsTabsToAdd = adminGroupsTabs.filter(adminGroupsTabsItem => {
        const adminGroupsTabsIdentifier = getAdminGroupsTabsIdentifier(adminGroupsTabsItem);
        if (adminGroupsTabsIdentifier == null || adminGroupsTabsCollectionIdentifiers.includes(adminGroupsTabsIdentifier)) {
          return false;
        }
        adminGroupsTabsCollectionIdentifiers.push(adminGroupsTabsIdentifier);
        return true;
      });
      return [...adminGroupsTabsToAdd, ...adminGroupsTabsCollection];
    }
    return adminGroupsTabsCollection;
  }
}
