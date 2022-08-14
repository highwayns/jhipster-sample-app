import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRequests, getRequestsIdentifier } from '../requests.model';

export type EntityResponseType = HttpResponse<IRequests>;
export type EntityArrayResponseType = HttpResponse<IRequests[]>;

@Injectable({ providedIn: 'root' })
export class RequestsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/requests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(requests: IRequests): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requests);
    return this.http
      .post<IRequests>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(requests: IRequests): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requests);
    return this.http
      .put<IRequests>(`${this.resourceUrl}/${getRequestsIdentifier(requests) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(requests: IRequests): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requests);
    return this.http
      .patch<IRequests>(`${this.resourceUrl}/${getRequestsIdentifier(requests) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRequests>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRequests[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRequestsToCollectionIfMissing(requestsCollection: IRequests[], ...requestsToCheck: (IRequests | null | undefined)[]): IRequests[] {
    const requests: IRequests[] = requestsToCheck.filter(isPresent);
    if (requests.length > 0) {
      const requestsCollectionIdentifiers = requestsCollection.map(requestsItem => getRequestsIdentifier(requestsItem)!);
      const requestsToAdd = requests.filter(requestsItem => {
        const requestsIdentifier = getRequestsIdentifier(requestsItem);
        if (requestsIdentifier == null || requestsCollectionIdentifiers.includes(requestsIdentifier)) {
          return false;
        }
        requestsCollectionIdentifiers.push(requestsIdentifier);
        return true;
      });
      return [...requestsToAdd, ...requestsCollection];
    }
    return requestsCollection;
  }

  protected convertDateFromClient(requests: IRequests): IRequests {
    return Object.assign({}, requests, {
      date: requests.date?.isValid() ? requests.date.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? dayjs(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((requests: IRequests) => {
        requests.date = requests.date ? dayjs(requests.date) : undefined;
      });
    }
    return res;
  }
}
