import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInnodbLockMonitor, getInnodbLockMonitorIdentifier } from '../innodb-lock-monitor.model';

export type EntityResponseType = HttpResponse<IInnodbLockMonitor>;
export type EntityArrayResponseType = HttpResponse<IInnodbLockMonitor[]>;

@Injectable({ providedIn: 'root' })
export class InnodbLockMonitorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/innodb-lock-monitors');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(innodbLockMonitor: IInnodbLockMonitor): Observable<EntityResponseType> {
    return this.http.post<IInnodbLockMonitor>(this.resourceUrl, innodbLockMonitor, { observe: 'response' });
  }

  update(innodbLockMonitor: IInnodbLockMonitor): Observable<EntityResponseType> {
    return this.http.put<IInnodbLockMonitor>(
      `${this.resourceUrl}/${getInnodbLockMonitorIdentifier(innodbLockMonitor) as number}`,
      innodbLockMonitor,
      { observe: 'response' }
    );
  }

  partialUpdate(innodbLockMonitor: IInnodbLockMonitor): Observable<EntityResponseType> {
    return this.http.patch<IInnodbLockMonitor>(
      `${this.resourceUrl}/${getInnodbLockMonitorIdentifier(innodbLockMonitor) as number}`,
      innodbLockMonitor,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInnodbLockMonitor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInnodbLockMonitor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addInnodbLockMonitorToCollectionIfMissing(
    innodbLockMonitorCollection: IInnodbLockMonitor[],
    ...innodbLockMonitorsToCheck: (IInnodbLockMonitor | null | undefined)[]
  ): IInnodbLockMonitor[] {
    const innodbLockMonitors: IInnodbLockMonitor[] = innodbLockMonitorsToCheck.filter(isPresent);
    if (innodbLockMonitors.length > 0) {
      const innodbLockMonitorCollectionIdentifiers = innodbLockMonitorCollection.map(
        innodbLockMonitorItem => getInnodbLockMonitorIdentifier(innodbLockMonitorItem)!
      );
      const innodbLockMonitorsToAdd = innodbLockMonitors.filter(innodbLockMonitorItem => {
        const innodbLockMonitorIdentifier = getInnodbLockMonitorIdentifier(innodbLockMonitorItem);
        if (innodbLockMonitorIdentifier == null || innodbLockMonitorCollectionIdentifiers.includes(innodbLockMonitorIdentifier)) {
          return false;
        }
        innodbLockMonitorCollectionIdentifiers.push(innodbLockMonitorIdentifier);
        return true;
      });
      return [...innodbLockMonitorsToAdd, ...innodbLockMonitorCollection];
    }
    return innodbLockMonitorCollection;
  }
}
