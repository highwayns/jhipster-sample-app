import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdminUsers, getAdminUsersIdentifier } from '../admin-users.model';

export type EntityResponseType = HttpResponse<IAdminUsers>;
export type EntityArrayResponseType = HttpResponse<IAdminUsers[]>;

@Injectable({ providedIn: 'root' })
export class AdminUsersService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/admin-users');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(adminUsers: IAdminUsers): Observable<EntityResponseType> {
    return this.http.post<IAdminUsers>(this.resourceUrl, adminUsers, { observe: 'response' });
  }

  update(adminUsers: IAdminUsers): Observable<EntityResponseType> {
    return this.http.put<IAdminUsers>(`${this.resourceUrl}/${getAdminUsersIdentifier(adminUsers) as number}`, adminUsers, {
      observe: 'response',
    });
  }

  partialUpdate(adminUsers: IAdminUsers): Observable<EntityResponseType> {
    return this.http.patch<IAdminUsers>(`${this.resourceUrl}/${getAdminUsersIdentifier(adminUsers) as number}`, adminUsers, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdminUsers>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdminUsers[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAdminUsersToCollectionIfMissing(
    adminUsersCollection: IAdminUsers[],
    ...adminUsersToCheck: (IAdminUsers | null | undefined)[]
  ): IAdminUsers[] {
    const adminUsers: IAdminUsers[] = adminUsersToCheck.filter(isPresent);
    if (adminUsers.length > 0) {
      const adminUsersCollectionIdentifiers = adminUsersCollection.map(adminUsersItem => getAdminUsersIdentifier(adminUsersItem)!);
      const adminUsersToAdd = adminUsers.filter(adminUsersItem => {
        const adminUsersIdentifier = getAdminUsersIdentifier(adminUsersItem);
        if (adminUsersIdentifier == null || adminUsersCollectionIdentifiers.includes(adminUsersIdentifier)) {
          return false;
        }
        adminUsersCollectionIdentifiers.push(adminUsersIdentifier);
        return true;
      });
      return [...adminUsersToAdd, ...adminUsersCollection];
    }
    return adminUsersCollection;
  }
}
