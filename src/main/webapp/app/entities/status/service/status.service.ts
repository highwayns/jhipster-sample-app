import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStatus, getStatusIdentifier } from '../status.model';

export type EntityResponseType = HttpResponse<IStatus>;
export type EntityArrayResponseType = HttpResponse<IStatus[]>;

@Injectable({ providedIn: 'root' })
export class StatusService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/statuses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(status: IStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(status);
    return this.http
      .post<IStatus>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(status: IStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(status);
    return this.http
      .put<IStatus>(`${this.resourceUrl}/${getStatusIdentifier(status) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(status: IStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(status);
    return this.http
      .patch<IStatus>(`${this.resourceUrl}/${getStatusIdentifier(status) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStatus[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addStatusToCollectionIfMissing(statusCollection: IStatus[], ...statusesToCheck: (IStatus | null | undefined)[]): IStatus[] {
    const statuses: IStatus[] = statusesToCheck.filter(isPresent);
    if (statuses.length > 0) {
      const statusCollectionIdentifiers = statusCollection.map(statusItem => getStatusIdentifier(statusItem)!);
      const statusesToAdd = statuses.filter(statusItem => {
        const statusIdentifier = getStatusIdentifier(statusItem);
        if (statusIdentifier == null || statusCollectionIdentifiers.includes(statusIdentifier)) {
          return false;
        }
        statusCollectionIdentifiers.push(statusIdentifier);
        return true;
      });
      return [...statusesToAdd, ...statusCollection];
    }
    return statusCollection;
  }

  protected convertDateFromClient(status: IStatus): IStatus {
    return Object.assign({}, status, {
      lastSweep: status.lastSweep?.isValid() ? status.lastSweep.toJSON() : undefined,
      cronDailyStats: status.cronDailyStats?.isValid() ? status.cronDailyStats.toJSON() : undefined,
      cronGetStats: status.cronGetStats?.isValid() ? status.cronGetStats.toJSON() : undefined,
      cronMaintenance: status.cronMaintenance?.isValid() ? status.cronMaintenance.toJSON() : undefined,
      cronMonthlyStats: status.cronMonthlyStats?.isValid() ? status.cronMonthlyStats.toJSON() : undefined,
      cronReceiveBitcoin: status.cronReceiveBitcoin?.isValid() ? status.cronReceiveBitcoin.toJSON() : undefined,
      cronSendBitcoin: status.cronSendBitcoin?.isValid() ? status.cronSendBitcoin.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastSweep = res.body.lastSweep ? dayjs(res.body.lastSweep) : undefined;
      res.body.cronDailyStats = res.body.cronDailyStats ? dayjs(res.body.cronDailyStats) : undefined;
      res.body.cronGetStats = res.body.cronGetStats ? dayjs(res.body.cronGetStats) : undefined;
      res.body.cronMaintenance = res.body.cronMaintenance ? dayjs(res.body.cronMaintenance) : undefined;
      res.body.cronMonthlyStats = res.body.cronMonthlyStats ? dayjs(res.body.cronMonthlyStats) : undefined;
      res.body.cronReceiveBitcoin = res.body.cronReceiveBitcoin ? dayjs(res.body.cronReceiveBitcoin) : undefined;
      res.body.cronSendBitcoin = res.body.cronSendBitcoin ? dayjs(res.body.cronSendBitcoin) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((status: IStatus) => {
        status.lastSweep = status.lastSweep ? dayjs(status.lastSweep) : undefined;
        status.cronDailyStats = status.cronDailyStats ? dayjs(status.cronDailyStats) : undefined;
        status.cronGetStats = status.cronGetStats ? dayjs(status.cronGetStats) : undefined;
        status.cronMaintenance = status.cronMaintenance ? dayjs(status.cronMaintenance) : undefined;
        status.cronMonthlyStats = status.cronMonthlyStats ? dayjs(status.cronMonthlyStats) : undefined;
        status.cronReceiveBitcoin = status.cronReceiveBitcoin ? dayjs(status.cronReceiveBitcoin) : undefined;
        status.cronSendBitcoin = status.cronSendBitcoin ? dayjs(status.cronSendBitcoin) : undefined;
      });
    }
    return res;
  }
}
