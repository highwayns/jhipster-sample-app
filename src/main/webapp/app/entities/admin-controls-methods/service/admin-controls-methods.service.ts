import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdminControlsMethods, getAdminControlsMethodsIdentifier } from '../admin-controls-methods.model';

export type EntityResponseType = HttpResponse<IAdminControlsMethods>;
export type EntityArrayResponseType = HttpResponse<IAdminControlsMethods[]>;

@Injectable({ providedIn: 'root' })
export class AdminControlsMethodsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/admin-controls-methods');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(adminControlsMethods: IAdminControlsMethods): Observable<EntityResponseType> {
    return this.http.post<IAdminControlsMethods>(this.resourceUrl, adminControlsMethods, { observe: 'response' });
  }

  update(adminControlsMethods: IAdminControlsMethods): Observable<EntityResponseType> {
    return this.http.put<IAdminControlsMethods>(
      `${this.resourceUrl}/${getAdminControlsMethodsIdentifier(adminControlsMethods) as number}`,
      adminControlsMethods,
      { observe: 'response' }
    );
  }

  partialUpdate(adminControlsMethods: IAdminControlsMethods): Observable<EntityResponseType> {
    return this.http.patch<IAdminControlsMethods>(
      `${this.resourceUrl}/${getAdminControlsMethodsIdentifier(adminControlsMethods) as number}`,
      adminControlsMethods,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdminControlsMethods>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdminControlsMethods[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAdminControlsMethodsToCollectionIfMissing(
    adminControlsMethodsCollection: IAdminControlsMethods[],
    ...adminControlsMethodsToCheck: (IAdminControlsMethods | null | undefined)[]
  ): IAdminControlsMethods[] {
    const adminControlsMethods: IAdminControlsMethods[] = adminControlsMethodsToCheck.filter(isPresent);
    if (adminControlsMethods.length > 0) {
      const adminControlsMethodsCollectionIdentifiers = adminControlsMethodsCollection.map(
        adminControlsMethodsItem => getAdminControlsMethodsIdentifier(adminControlsMethodsItem)!
      );
      const adminControlsMethodsToAdd = adminControlsMethods.filter(adminControlsMethodsItem => {
        const adminControlsMethodsIdentifier = getAdminControlsMethodsIdentifier(adminControlsMethodsItem);
        if (adminControlsMethodsIdentifier == null || adminControlsMethodsCollectionIdentifiers.includes(adminControlsMethodsIdentifier)) {
          return false;
        }
        adminControlsMethodsCollectionIdentifiers.push(adminControlsMethodsIdentifier);
        return true;
      });
      return [...adminControlsMethodsToAdd, ...adminControlsMethodsCollection];
    }
    return adminControlsMethodsCollection;
  }
}
