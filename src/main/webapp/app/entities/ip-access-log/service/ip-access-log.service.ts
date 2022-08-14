import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIpAccessLog, getIpAccessLogIdentifier } from '../ip-access-log.model';

export type EntityResponseType = HttpResponse<IIpAccessLog>;
export type EntityArrayResponseType = HttpResponse<IIpAccessLog[]>;

@Injectable({ providedIn: 'root' })
export class IpAccessLogService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ip-access-logs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ipAccessLog: IIpAccessLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ipAccessLog);
    return this.http
      .post<IIpAccessLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ipAccessLog: IIpAccessLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ipAccessLog);
    return this.http
      .put<IIpAccessLog>(`${this.resourceUrl}/${getIpAccessLogIdentifier(ipAccessLog) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(ipAccessLog: IIpAccessLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ipAccessLog);
    return this.http
      .patch<IIpAccessLog>(`${this.resourceUrl}/${getIpAccessLogIdentifier(ipAccessLog) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIpAccessLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIpAccessLog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addIpAccessLogToCollectionIfMissing(
    ipAccessLogCollection: IIpAccessLog[],
    ...ipAccessLogsToCheck: (IIpAccessLog | null | undefined)[]
  ): IIpAccessLog[] {
    const ipAccessLogs: IIpAccessLog[] = ipAccessLogsToCheck.filter(isPresent);
    if (ipAccessLogs.length > 0) {
      const ipAccessLogCollectionIdentifiers = ipAccessLogCollection.map(ipAccessLogItem => getIpAccessLogIdentifier(ipAccessLogItem)!);
      const ipAccessLogsToAdd = ipAccessLogs.filter(ipAccessLogItem => {
        const ipAccessLogIdentifier = getIpAccessLogIdentifier(ipAccessLogItem);
        if (ipAccessLogIdentifier == null || ipAccessLogCollectionIdentifiers.includes(ipAccessLogIdentifier)) {
          return false;
        }
        ipAccessLogCollectionIdentifiers.push(ipAccessLogIdentifier);
        return true;
      });
      return [...ipAccessLogsToAdd, ...ipAccessLogCollection];
    }
    return ipAccessLogCollection;
  }

  protected convertDateFromClient(ipAccessLog: IIpAccessLog): IIpAccessLog {
    return Object.assign({}, ipAccessLog, {
      timestamp: ipAccessLog.timestamp?.isValid() ? ipAccessLog.timestamp.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.timestamp = res.body.timestamp ? dayjs(res.body.timestamp) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((ipAccessLog: IIpAccessLog) => {
        ipAccessLog.timestamp = ipAccessLog.timestamp ? dayjs(ipAccessLog.timestamp) : undefined;
      });
    }
    return res;
  }
}
