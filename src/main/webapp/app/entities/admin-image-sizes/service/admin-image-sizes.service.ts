import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdminImageSizes, getAdminImageSizesIdentifier } from '../admin-image-sizes.model';

export type EntityResponseType = HttpResponse<IAdminImageSizes>;
export type EntityArrayResponseType = HttpResponse<IAdminImageSizes[]>;

@Injectable({ providedIn: 'root' })
export class AdminImageSizesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/admin-image-sizes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(adminImageSizes: IAdminImageSizes): Observable<EntityResponseType> {
    return this.http.post<IAdminImageSizes>(this.resourceUrl, adminImageSizes, { observe: 'response' });
  }

  update(adminImageSizes: IAdminImageSizes): Observable<EntityResponseType> {
    return this.http.put<IAdminImageSizes>(
      `${this.resourceUrl}/${getAdminImageSizesIdentifier(adminImageSizes) as number}`,
      adminImageSizes,
      { observe: 'response' }
    );
  }

  partialUpdate(adminImageSizes: IAdminImageSizes): Observable<EntityResponseType> {
    return this.http.patch<IAdminImageSizes>(
      `${this.resourceUrl}/${getAdminImageSizesIdentifier(adminImageSizes) as number}`,
      adminImageSizes,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdminImageSizes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdminImageSizes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAdminImageSizesToCollectionIfMissing(
    adminImageSizesCollection: IAdminImageSizes[],
    ...adminImageSizesToCheck: (IAdminImageSizes | null | undefined)[]
  ): IAdminImageSizes[] {
    const adminImageSizes: IAdminImageSizes[] = adminImageSizesToCheck.filter(isPresent);
    if (adminImageSizes.length > 0) {
      const adminImageSizesCollectionIdentifiers = adminImageSizesCollection.map(
        adminImageSizesItem => getAdminImageSizesIdentifier(adminImageSizesItem)!
      );
      const adminImageSizesToAdd = adminImageSizes.filter(adminImageSizesItem => {
        const adminImageSizesIdentifier = getAdminImageSizesIdentifier(adminImageSizesItem);
        if (adminImageSizesIdentifier == null || adminImageSizesCollectionIdentifiers.includes(adminImageSizesIdentifier)) {
          return false;
        }
        adminImageSizesCollectionIdentifiers.push(adminImageSizesIdentifier);
        return true;
      });
      return [...adminImageSizesToAdd, ...adminImageSizesCollection];
    }
    return adminImageSizesCollection;
  }
}
