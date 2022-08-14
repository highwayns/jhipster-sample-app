import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrderLog, getOrderLogIdentifier } from '../order-log.model';

export type EntityResponseType = HttpResponse<IOrderLog>;
export type EntityArrayResponseType = HttpResponse<IOrderLog[]>;

@Injectable({ providedIn: 'root' })
export class OrderLogService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/order-logs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(orderLog: IOrderLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderLog);
    return this.http
      .post<IOrderLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(orderLog: IOrderLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderLog);
    return this.http
      .put<IOrderLog>(`${this.resourceUrl}/${getOrderLogIdentifier(orderLog) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(orderLog: IOrderLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderLog);
    return this.http
      .patch<IOrderLog>(`${this.resourceUrl}/${getOrderLogIdentifier(orderLog) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrderLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrderLog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addOrderLogToCollectionIfMissing(orderLogCollection: IOrderLog[], ...orderLogsToCheck: (IOrderLog | null | undefined)[]): IOrderLog[] {
    const orderLogs: IOrderLog[] = orderLogsToCheck.filter(isPresent);
    if (orderLogs.length > 0) {
      const orderLogCollectionIdentifiers = orderLogCollection.map(orderLogItem => getOrderLogIdentifier(orderLogItem)!);
      const orderLogsToAdd = orderLogs.filter(orderLogItem => {
        const orderLogIdentifier = getOrderLogIdentifier(orderLogItem);
        if (orderLogIdentifier == null || orderLogCollectionIdentifiers.includes(orderLogIdentifier)) {
          return false;
        }
        orderLogCollectionIdentifiers.push(orderLogIdentifier);
        return true;
      });
      return [...orderLogsToAdd, ...orderLogCollection];
    }
    return orderLogCollection;
  }

  protected convertDateFromClient(orderLog: IOrderLog): IOrderLog {
    return Object.assign({}, orderLog, {
      date: orderLog.date?.isValid() ? orderLog.date.toJSON() : undefined,
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
      res.body.forEach((orderLog: IOrderLog) => {
        orderLog.date = orderLog.date ? dayjs(orderLog.date) : undefined;
      });
    }
    return res;
  }
}
