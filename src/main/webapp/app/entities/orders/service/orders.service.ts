import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrders, getOrdersIdentifier } from '../orders.model';

export type EntityResponseType = HttpResponse<IOrders>;
export type EntityArrayResponseType = HttpResponse<IOrders[]>;

@Injectable({ providedIn: 'root' })
export class OrdersService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/orders');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(orders: IOrders): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orders);
    return this.http
      .post<IOrders>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(orders: IOrders): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orders);
    return this.http
      .put<IOrders>(`${this.resourceUrl}/${getOrdersIdentifier(orders) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(orders: IOrders): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orders);
    return this.http
      .patch<IOrders>(`${this.resourceUrl}/${getOrdersIdentifier(orders) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrders>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrders[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addOrdersToCollectionIfMissing(ordersCollection: IOrders[], ...ordersToCheck: (IOrders | null | undefined)[]): IOrders[] {
    const orders: IOrders[] = ordersToCheck.filter(isPresent);
    if (orders.length > 0) {
      const ordersCollectionIdentifiers = ordersCollection.map(ordersItem => getOrdersIdentifier(ordersItem)!);
      const ordersToAdd = orders.filter(ordersItem => {
        const ordersIdentifier = getOrdersIdentifier(ordersItem);
        if (ordersIdentifier == null || ordersCollectionIdentifiers.includes(ordersIdentifier)) {
          return false;
        }
        ordersCollectionIdentifiers.push(ordersIdentifier);
        return true;
      });
      return [...ordersToAdd, ...ordersCollection];
    }
    return ordersCollection;
  }

  protected convertDateFromClient(orders: IOrders): IOrders {
    return Object.assign({}, orders, {
      date: orders.date?.isValid() ? orders.date.toJSON() : undefined,
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
      res.body.forEach((orders: IOrders) => {
        orders.date = orders.date ? dayjs(orders.date) : undefined;
      });
    }
    return res;
  }
}
