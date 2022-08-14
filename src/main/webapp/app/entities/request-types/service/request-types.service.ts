import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRequestTypes, getRequestTypesIdentifier } from '../request-types.model';

export type EntityResponseType = HttpResponse<IRequestTypes>;
export type EntityArrayResponseType = HttpResponse<IRequestTypes[]>;

@Injectable({ providedIn: 'root' })
export class RequestTypesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/request-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(requestTypes: IRequestTypes): Observable<EntityResponseType> {
    return this.http.post<IRequestTypes>(this.resourceUrl, requestTypes, { observe: 'response' });
  }

  update(requestTypes: IRequestTypes): Observable<EntityResponseType> {
    return this.http.put<IRequestTypes>(`${this.resourceUrl}/${getRequestTypesIdentifier(requestTypes) as number}`, requestTypes, {
      observe: 'response',
    });
  }

  partialUpdate(requestTypes: IRequestTypes): Observable<EntityResponseType> {
    return this.http.patch<IRequestTypes>(`${this.resourceUrl}/${getRequestTypesIdentifier(requestTypes) as number}`, requestTypes, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRequestTypes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRequestTypes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRequestTypesToCollectionIfMissing(
    requestTypesCollection: IRequestTypes[],
    ...requestTypesToCheck: (IRequestTypes | null | undefined)[]
  ): IRequestTypes[] {
    const requestTypes: IRequestTypes[] = requestTypesToCheck.filter(isPresent);
    if (requestTypes.length > 0) {
      const requestTypesCollectionIdentifiers = requestTypesCollection.map(
        requestTypesItem => getRequestTypesIdentifier(requestTypesItem)!
      );
      const requestTypesToAdd = requestTypes.filter(requestTypesItem => {
        const requestTypesIdentifier = getRequestTypesIdentifier(requestTypesItem);
        if (requestTypesIdentifier == null || requestTypesCollectionIdentifiers.includes(requestTypesIdentifier)) {
          return false;
        }
        requestTypesCollectionIdentifiers.push(requestTypesIdentifier);
        return true;
      });
      return [...requestTypesToAdd, ...requestTypesCollection];
    }
    return requestTypesCollection;
  }
}
