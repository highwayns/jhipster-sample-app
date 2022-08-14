import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdminGroupsPages, getAdminGroupsPagesIdentifier } from '../admin-groups-pages.model';

export type EntityResponseType = HttpResponse<IAdminGroupsPages>;
export type EntityArrayResponseType = HttpResponse<IAdminGroupsPages[]>;

@Injectable({ providedIn: 'root' })
export class AdminGroupsPagesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/admin-groups-pages');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(adminGroupsPages: IAdminGroupsPages): Observable<EntityResponseType> {
    return this.http.post<IAdminGroupsPages>(this.resourceUrl, adminGroupsPages, { observe: 'response' });
  }

  update(adminGroupsPages: IAdminGroupsPages): Observable<EntityResponseType> {
    return this.http.put<IAdminGroupsPages>(
      `${this.resourceUrl}/${getAdminGroupsPagesIdentifier(adminGroupsPages) as number}`,
      adminGroupsPages,
      { observe: 'response' }
    );
  }

  partialUpdate(adminGroupsPages: IAdminGroupsPages): Observable<EntityResponseType> {
    return this.http.patch<IAdminGroupsPages>(
      `${this.resourceUrl}/${getAdminGroupsPagesIdentifier(adminGroupsPages) as number}`,
      adminGroupsPages,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdminGroupsPages>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdminGroupsPages[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAdminGroupsPagesToCollectionIfMissing(
    adminGroupsPagesCollection: IAdminGroupsPages[],
    ...adminGroupsPagesToCheck: (IAdminGroupsPages | null | undefined)[]
  ): IAdminGroupsPages[] {
    const adminGroupsPages: IAdminGroupsPages[] = adminGroupsPagesToCheck.filter(isPresent);
    if (adminGroupsPages.length > 0) {
      const adminGroupsPagesCollectionIdentifiers = adminGroupsPagesCollection.map(
        adminGroupsPagesItem => getAdminGroupsPagesIdentifier(adminGroupsPagesItem)!
      );
      const adminGroupsPagesToAdd = adminGroupsPages.filter(adminGroupsPagesItem => {
        const adminGroupsPagesIdentifier = getAdminGroupsPagesIdentifier(adminGroupsPagesItem);
        if (adminGroupsPagesIdentifier == null || adminGroupsPagesCollectionIdentifiers.includes(adminGroupsPagesIdentifier)) {
          return false;
        }
        adminGroupsPagesCollectionIdentifiers.push(adminGroupsPagesIdentifier);
        return true;
      });
      return [...adminGroupsPagesToAdd, ...adminGroupsPagesCollection];
    }
    return adminGroupsPagesCollection;
  }
}
