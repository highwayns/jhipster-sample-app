import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrderTypes, getOrderTypesIdentifier } from '../order-types.model';

export type EntityResponseType = HttpResponse<IOrderTypes>;
export type EntityArrayResponseType = HttpResponse<IOrderTypes[]>;

@Injectable({ providedIn: 'root' })
export class OrderTypesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/order-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(orderTypes: IOrderTypes): Observable<EntityResponseType> {
    return this.http.post<IOrderTypes>(this.resourceUrl, orderTypes, { observe: 'response' });
  }

  update(orderTypes: IOrderTypes): Observable<EntityResponseType> {
    return this.http.put<IOrderTypes>(`${this.resourceUrl}/${getOrderTypesIdentifier(orderTypes) as number}`, orderTypes, {
      observe: 'response',
    });
  }

  partialUpdate(orderTypes: IOrderTypes): Observable<EntityResponseType> {
    return this.http.patch<IOrderTypes>(`${this.resourceUrl}/${getOrderTypesIdentifier(orderTypes) as number}`, orderTypes, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrderTypes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderTypes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addOrderTypesToCollectionIfMissing(
    orderTypesCollection: IOrderTypes[],
    ...orderTypesToCheck: (IOrderTypes | null | undefined)[]
  ): IOrderTypes[] {
    const orderTypes: IOrderTypes[] = orderTypesToCheck.filter(isPresent);
    if (orderTypes.length > 0) {
      const orderTypesCollectionIdentifiers = orderTypesCollection.map(orderTypesItem => getOrderTypesIdentifier(orderTypesItem)!);
      const orderTypesToAdd = orderTypes.filter(orderTypesItem => {
        const orderTypesIdentifier = getOrderTypesIdentifier(orderTypesItem);
        if (orderTypesIdentifier == null || orderTypesCollectionIdentifiers.includes(orderTypesIdentifier)) {
          return false;
        }
        orderTypesCollectionIdentifiers.push(orderTypesIdentifier);
        return true;
      });
      return [...orderTypesToAdd, ...orderTypesCollection];
    }
    return orderTypesCollection;
  }
}
