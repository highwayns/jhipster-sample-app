import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdminControls, getAdminControlsIdentifier } from '../admin-controls.model';

export type EntityResponseType = HttpResponse<IAdminControls>;
export type EntityArrayResponseType = HttpResponse<IAdminControls[]>;

@Injectable({ providedIn: 'root' })
export class AdminControlsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/admin-controls');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(adminControls: IAdminControls): Observable<EntityResponseType> {
    return this.http.post<IAdminControls>(this.resourceUrl, adminControls, { observe: 'response' });
  }

  update(adminControls: IAdminControls): Observable<EntityResponseType> {
    return this.http.put<IAdminControls>(`${this.resourceUrl}/${getAdminControlsIdentifier(adminControls) as number}`, adminControls, {
      observe: 'response',
    });
  }

  partialUpdate(adminControls: IAdminControls): Observable<EntityResponseType> {
    return this.http.patch<IAdminControls>(`${this.resourceUrl}/${getAdminControlsIdentifier(adminControls) as number}`, adminControls, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdminControls>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdminControls[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAdminControlsToCollectionIfMissing(
    adminControlsCollection: IAdminControls[],
    ...adminControlsToCheck: (IAdminControls | null | undefined)[]
  ): IAdminControls[] {
    const adminControls: IAdminControls[] = adminControlsToCheck.filter(isPresent);
    if (adminControls.length > 0) {
      const adminControlsCollectionIdentifiers = adminControlsCollection.map(
        adminControlsItem => getAdminControlsIdentifier(adminControlsItem)!
      );
      const adminControlsToAdd = adminControls.filter(adminControlsItem => {
        const adminControlsIdentifier = getAdminControlsIdentifier(adminControlsItem);
        if (adminControlsIdentifier == null || adminControlsCollectionIdentifiers.includes(adminControlsIdentifier)) {
          return false;
        }
        adminControlsCollectionIdentifiers.push(adminControlsIdentifier);
        return true;
      });
      return [...adminControlsToAdd, ...adminControlsCollection];
    }
    return adminControlsCollection;
  }
}
