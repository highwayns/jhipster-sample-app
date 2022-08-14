import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdminGroups, getAdminGroupsIdentifier } from '../admin-groups.model';

export type EntityResponseType = HttpResponse<IAdminGroups>;
export type EntityArrayResponseType = HttpResponse<IAdminGroups[]>;

@Injectable({ providedIn: 'root' })
export class AdminGroupsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/admin-groups');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(adminGroups: IAdminGroups): Observable<EntityResponseType> {
    return this.http.post<IAdminGroups>(this.resourceUrl, adminGroups, { observe: 'response' });
  }

  update(adminGroups: IAdminGroups): Observable<EntityResponseType> {
    return this.http.put<IAdminGroups>(`${this.resourceUrl}/${getAdminGroupsIdentifier(adminGroups) as number}`, adminGroups, {
      observe: 'response',
    });
  }

  partialUpdate(adminGroups: IAdminGroups): Observable<EntityResponseType> {
    return this.http.patch<IAdminGroups>(`${this.resourceUrl}/${getAdminGroupsIdentifier(adminGroups) as number}`, adminGroups, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdminGroups>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdminGroups[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAdminGroupsToCollectionIfMissing(
    adminGroupsCollection: IAdminGroups[],
    ...adminGroupsToCheck: (IAdminGroups | null | undefined)[]
  ): IAdminGroups[] {
    const adminGroups: IAdminGroups[] = adminGroupsToCheck.filter(isPresent);
    if (adminGroups.length > 0) {
      const adminGroupsCollectionIdentifiers = adminGroupsCollection.map(adminGroupsItem => getAdminGroupsIdentifier(adminGroupsItem)!);
      const adminGroupsToAdd = adminGroups.filter(adminGroupsItem => {
        const adminGroupsIdentifier = getAdminGroupsIdentifier(adminGroupsItem);
        if (adminGroupsIdentifier == null || adminGroupsCollectionIdentifiers.includes(adminGroupsIdentifier)) {
          return false;
        }
        adminGroupsCollectionIdentifiers.push(adminGroupsIdentifier);
        return true;
      });
      return [...adminGroupsToAdd, ...adminGroupsCollection];
    }
    return adminGroupsCollection;
  }
}
