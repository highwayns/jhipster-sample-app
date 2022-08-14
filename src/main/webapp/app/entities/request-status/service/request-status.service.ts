import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRequestStatus, getRequestStatusIdentifier } from '../request-status.model';

export type EntityResponseType = HttpResponse<IRequestStatus>;
export type EntityArrayResponseType = HttpResponse<IRequestStatus[]>;

@Injectable({ providedIn: 'root' })
export class RequestStatusService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/request-statuses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(requestStatus: IRequestStatus): Observable<EntityResponseType> {
    return this.http.post<IRequestStatus>(this.resourceUrl, requestStatus, { observe: 'response' });
  }

  update(requestStatus: IRequestStatus): Observable<EntityResponseType> {
    return this.http.put<IRequestStatus>(`${this.resourceUrl}/${getRequestStatusIdentifier(requestStatus) as number}`, requestStatus, {
      observe: 'response',
    });
  }

  partialUpdate(requestStatus: IRequestStatus): Observable<EntityResponseType> {
    return this.http.patch<IRequestStatus>(`${this.resourceUrl}/${getRequestStatusIdentifier(requestStatus) as number}`, requestStatus, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRequestStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRequestStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRequestStatusToCollectionIfMissing(
    requestStatusCollection: IRequestStatus[],
    ...requestStatusesToCheck: (IRequestStatus | null | undefined)[]
  ): IRequestStatus[] {
    const requestStatuses: IRequestStatus[] = requestStatusesToCheck.filter(isPresent);
    if (requestStatuses.length > 0) {
      const requestStatusCollectionIdentifiers = requestStatusCollection.map(
        requestStatusItem => getRequestStatusIdentifier(requestStatusItem)!
      );
      const requestStatusesToAdd = requestStatuses.filter(requestStatusItem => {
        const requestStatusIdentifier = getRequestStatusIdentifier(requestStatusItem);
        if (requestStatusIdentifier == null || requestStatusCollectionIdentifiers.includes(requestStatusIdentifier)) {
          return false;
        }
        requestStatusCollectionIdentifiers.push(requestStatusIdentifier);
        return true;
      });
      return [...requestStatusesToAdd, ...requestStatusCollection];
    }
    return requestStatusCollection;
  }
}
