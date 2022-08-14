import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRequestDescriptions, getRequestDescriptionsIdentifier } from '../request-descriptions.model';

export type EntityResponseType = HttpResponse<IRequestDescriptions>;
export type EntityArrayResponseType = HttpResponse<IRequestDescriptions[]>;

@Injectable({ providedIn: 'root' })
export class RequestDescriptionsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/request-descriptions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(requestDescriptions: IRequestDescriptions): Observable<EntityResponseType> {
    return this.http.post<IRequestDescriptions>(this.resourceUrl, requestDescriptions, { observe: 'response' });
  }

  update(requestDescriptions: IRequestDescriptions): Observable<EntityResponseType> {
    return this.http.put<IRequestDescriptions>(
      `${this.resourceUrl}/${getRequestDescriptionsIdentifier(requestDescriptions) as number}`,
      requestDescriptions,
      { observe: 'response' }
    );
  }

  partialUpdate(requestDescriptions: IRequestDescriptions): Observable<EntityResponseType> {
    return this.http.patch<IRequestDescriptions>(
      `${this.resourceUrl}/${getRequestDescriptionsIdentifier(requestDescriptions) as number}`,
      requestDescriptions,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRequestDescriptions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRequestDescriptions[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRequestDescriptionsToCollectionIfMissing(
    requestDescriptionsCollection: IRequestDescriptions[],
    ...requestDescriptionsToCheck: (IRequestDescriptions | null | undefined)[]
  ): IRequestDescriptions[] {
    const requestDescriptions: IRequestDescriptions[] = requestDescriptionsToCheck.filter(isPresent);
    if (requestDescriptions.length > 0) {
      const requestDescriptionsCollectionIdentifiers = requestDescriptionsCollection.map(
        requestDescriptionsItem => getRequestDescriptionsIdentifier(requestDescriptionsItem)!
      );
      const requestDescriptionsToAdd = requestDescriptions.filter(requestDescriptionsItem => {
        const requestDescriptionsIdentifier = getRequestDescriptionsIdentifier(requestDescriptionsItem);
        if (requestDescriptionsIdentifier == null || requestDescriptionsCollectionIdentifiers.includes(requestDescriptionsIdentifier)) {
          return false;
        }
        requestDescriptionsCollectionIdentifiers.push(requestDescriptionsIdentifier);
        return true;
      });
      return [...requestDescriptionsToAdd, ...requestDescriptionsCollection];
    }
    return requestDescriptionsCollection;
  }
}
