import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdminOrder, getAdminOrderIdentifier } from '../admin-order.model';

export type EntityResponseType = HttpResponse<IAdminOrder>;
export type EntityArrayResponseType = HttpResponse<IAdminOrder[]>;

@Injectable({ providedIn: 'root' })
export class AdminOrderService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/admin-orders');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(adminOrder: IAdminOrder): Observable<EntityResponseType> {
    return this.http.post<IAdminOrder>(this.resourceUrl, adminOrder, { observe: 'response' });
  }

  update(adminOrder: IAdminOrder): Observable<EntityResponseType> {
    return this.http.put<IAdminOrder>(`${this.resourceUrl}/${getAdminOrderIdentifier(adminOrder) as number}`, adminOrder, {
      observe: 'response',
    });
  }

  partialUpdate(adminOrder: IAdminOrder): Observable<EntityResponseType> {
    return this.http.patch<IAdminOrder>(`${this.resourceUrl}/${getAdminOrderIdentifier(adminOrder) as number}`, adminOrder, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdminOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdminOrder[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAdminOrderToCollectionIfMissing(
    adminOrderCollection: IAdminOrder[],
    ...adminOrdersToCheck: (IAdminOrder | null | undefined)[]
  ): IAdminOrder[] {
    const adminOrders: IAdminOrder[] = adminOrdersToCheck.filter(isPresent);
    if (adminOrders.length > 0) {
      const adminOrderCollectionIdentifiers = adminOrderCollection.map(adminOrderItem => getAdminOrderIdentifier(adminOrderItem)!);
      const adminOrdersToAdd = adminOrders.filter(adminOrderItem => {
        const adminOrderIdentifier = getAdminOrderIdentifier(adminOrderItem);
        if (adminOrderIdentifier == null || adminOrderCollectionIdentifiers.includes(adminOrderIdentifier)) {
          return false;
        }
        adminOrderCollectionIdentifiers.push(adminOrderIdentifier);
        return true;
      });
      return [...adminOrdersToAdd, ...adminOrderCollection];
    }
    return adminOrderCollection;
  }
}
