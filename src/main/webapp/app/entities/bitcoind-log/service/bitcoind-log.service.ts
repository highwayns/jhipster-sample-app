import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBitcoindLog, getBitcoindLogIdentifier } from '../bitcoind-log.model';

export type EntityResponseType = HttpResponse<IBitcoindLog>;
export type EntityArrayResponseType = HttpResponse<IBitcoindLog[]>;

@Injectable({ providedIn: 'root' })
export class BitcoindLogService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bitcoind-logs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bitcoindLog: IBitcoindLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bitcoindLog);
    return this.http
      .post<IBitcoindLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bitcoindLog: IBitcoindLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bitcoindLog);
    return this.http
      .put<IBitcoindLog>(`${this.resourceUrl}/${getBitcoindLogIdentifier(bitcoindLog) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(bitcoindLog: IBitcoindLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bitcoindLog);
    return this.http
      .patch<IBitcoindLog>(`${this.resourceUrl}/${getBitcoindLogIdentifier(bitcoindLog) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBitcoindLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBitcoindLog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBitcoindLogToCollectionIfMissing(
    bitcoindLogCollection: IBitcoindLog[],
    ...bitcoindLogsToCheck: (IBitcoindLog | null | undefined)[]
  ): IBitcoindLog[] {
    const bitcoindLogs: IBitcoindLog[] = bitcoindLogsToCheck.filter(isPresent);
    if (bitcoindLogs.length > 0) {
      const bitcoindLogCollectionIdentifiers = bitcoindLogCollection.map(bitcoindLogItem => getBitcoindLogIdentifier(bitcoindLogItem)!);
      const bitcoindLogsToAdd = bitcoindLogs.filter(bitcoindLogItem => {
        const bitcoindLogIdentifier = getBitcoindLogIdentifier(bitcoindLogItem);
        if (bitcoindLogIdentifier == null || bitcoindLogCollectionIdentifiers.includes(bitcoindLogIdentifier)) {
          return false;
        }
        bitcoindLogCollectionIdentifiers.push(bitcoindLogIdentifier);
        return true;
      });
      return [...bitcoindLogsToAdd, ...bitcoindLogCollection];
    }
    return bitcoindLogCollection;
  }

  protected convertDateFromClient(bitcoindLog: IBitcoindLog): IBitcoindLog {
    return Object.assign({}, bitcoindLog, {
      date: bitcoindLog.date?.isValid() ? bitcoindLog.date.toJSON() : undefined,
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
      res.body.forEach((bitcoindLog: IBitcoindLog) => {
        bitcoindLog.date = bitcoindLog.date ? dayjs(bitcoindLog.date) : undefined;
      });
    }
    return res;
  }
}
