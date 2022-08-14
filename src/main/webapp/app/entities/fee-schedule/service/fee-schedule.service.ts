import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFeeSchedule, getFeeScheduleIdentifier } from '../fee-schedule.model';

export type EntityResponseType = HttpResponse<IFeeSchedule>;
export type EntityArrayResponseType = HttpResponse<IFeeSchedule[]>;

@Injectable({ providedIn: 'root' })
export class FeeScheduleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fee-schedules');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(feeSchedule: IFeeSchedule): Observable<EntityResponseType> {
    return this.http.post<IFeeSchedule>(this.resourceUrl, feeSchedule, { observe: 'response' });
  }

  update(feeSchedule: IFeeSchedule): Observable<EntityResponseType> {
    return this.http.put<IFeeSchedule>(`${this.resourceUrl}/${getFeeScheduleIdentifier(feeSchedule) as number}`, feeSchedule, {
      observe: 'response',
    });
  }

  partialUpdate(feeSchedule: IFeeSchedule): Observable<EntityResponseType> {
    return this.http.patch<IFeeSchedule>(`${this.resourceUrl}/${getFeeScheduleIdentifier(feeSchedule) as number}`, feeSchedule, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFeeSchedule>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFeeSchedule[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFeeScheduleToCollectionIfMissing(
    feeScheduleCollection: IFeeSchedule[],
    ...feeSchedulesToCheck: (IFeeSchedule | null | undefined)[]
  ): IFeeSchedule[] {
    const feeSchedules: IFeeSchedule[] = feeSchedulesToCheck.filter(isPresent);
    if (feeSchedules.length > 0) {
      const feeScheduleCollectionIdentifiers = feeScheduleCollection.map(feeScheduleItem => getFeeScheduleIdentifier(feeScheduleItem)!);
      const feeSchedulesToAdd = feeSchedules.filter(feeScheduleItem => {
        const feeScheduleIdentifier = getFeeScheduleIdentifier(feeScheduleItem);
        if (feeScheduleIdentifier == null || feeScheduleCollectionIdentifiers.includes(feeScheduleIdentifier)) {
          return false;
        }
        feeScheduleCollectionIdentifiers.push(feeScheduleIdentifier);
        return true;
      });
      return [...feeSchedulesToAdd, ...feeScheduleCollection];
    }
    return feeScheduleCollection;
  }
}
