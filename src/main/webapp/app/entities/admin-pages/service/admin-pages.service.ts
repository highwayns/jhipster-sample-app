import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdminPages, getAdminPagesIdentifier } from '../admin-pages.model';

export type EntityResponseType = HttpResponse<IAdminPages>;
export type EntityArrayResponseType = HttpResponse<IAdminPages[]>;

@Injectable({ providedIn: 'root' })
export class AdminPagesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/admin-pages');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(adminPages: IAdminPages): Observable<EntityResponseType> {
    return this.http.post<IAdminPages>(this.resourceUrl, adminPages, { observe: 'response' });
  }

  update(adminPages: IAdminPages): Observable<EntityResponseType> {
    return this.http.put<IAdminPages>(`${this.resourceUrl}/${getAdminPagesIdentifier(adminPages) as number}`, adminPages, {
      observe: 'response',
    });
  }

  partialUpdate(adminPages: IAdminPages): Observable<EntityResponseType> {
    return this.http.patch<IAdminPages>(`${this.resourceUrl}/${getAdminPagesIdentifier(adminPages) as number}`, adminPages, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdminPages>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdminPages[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAdminPagesToCollectionIfMissing(
    adminPagesCollection: IAdminPages[],
    ...adminPagesToCheck: (IAdminPages | null | undefined)[]
  ): IAdminPages[] {
    const adminPages: IAdminPages[] = adminPagesToCheck.filter(isPresent);
    if (adminPages.length > 0) {
      const adminPagesCollectionIdentifiers = adminPagesCollection.map(adminPagesItem => getAdminPagesIdentifier(adminPagesItem)!);
      const adminPagesToAdd = adminPages.filter(adminPagesItem => {
        const adminPagesIdentifier = getAdminPagesIdentifier(adminPagesItem);
        if (adminPagesIdentifier == null || adminPagesCollectionIdentifiers.includes(adminPagesIdentifier)) {
          return false;
        }
        adminPagesCollectionIdentifiers.push(adminPagesIdentifier);
        return true;
      });
      return [...adminPagesToAdd, ...adminPagesCollection];
    }
    return adminPagesCollection;
  }
}
