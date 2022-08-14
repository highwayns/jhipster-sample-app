import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStatusEscrows, getStatusEscrowsIdentifier } from '../status-escrows.model';

export type EntityResponseType = HttpResponse<IStatusEscrows>;
export type EntityArrayResponseType = HttpResponse<IStatusEscrows[]>;

@Injectable({ providedIn: 'root' })
export class StatusEscrowsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/status-escrows');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(statusEscrows: IStatusEscrows): Observable<EntityResponseType> {
    return this.http.post<IStatusEscrows>(this.resourceUrl, statusEscrows, { observe: 'response' });
  }

  update(statusEscrows: IStatusEscrows): Observable<EntityResponseType> {
    return this.http.put<IStatusEscrows>(`${this.resourceUrl}/${getStatusEscrowsIdentifier(statusEscrows) as number}`, statusEscrows, {
      observe: 'response',
    });
  }

  partialUpdate(statusEscrows: IStatusEscrows): Observable<EntityResponseType> {
    return this.http.patch<IStatusEscrows>(`${this.resourceUrl}/${getStatusEscrowsIdentifier(statusEscrows) as number}`, statusEscrows, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStatusEscrows>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStatusEscrows[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addStatusEscrowsToCollectionIfMissing(
    statusEscrowsCollection: IStatusEscrows[],
    ...statusEscrowsToCheck: (IStatusEscrows | null | undefined)[]
  ): IStatusEscrows[] {
    const statusEscrows: IStatusEscrows[] = statusEscrowsToCheck.filter(isPresent);
    if (statusEscrows.length > 0) {
      const statusEscrowsCollectionIdentifiers = statusEscrowsCollection.map(
        statusEscrowsItem => getStatusEscrowsIdentifier(statusEscrowsItem)!
      );
      const statusEscrowsToAdd = statusEscrows.filter(statusEscrowsItem => {
        const statusEscrowsIdentifier = getStatusEscrowsIdentifier(statusEscrowsItem);
        if (statusEscrowsIdentifier == null || statusEscrowsCollectionIdentifiers.includes(statusEscrowsIdentifier)) {
          return false;
        }
        statusEscrowsCollectionIdentifiers.push(statusEscrowsIdentifier);
        return true;
      });
      return [...statusEscrowsToAdd, ...statusEscrowsCollection];
    }
    return statusEscrowsCollection;
  }
}
