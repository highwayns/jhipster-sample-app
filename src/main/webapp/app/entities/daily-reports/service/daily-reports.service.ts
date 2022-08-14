import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDailyReports, getDailyReportsIdentifier } from '../daily-reports.model';

export type EntityResponseType = HttpResponse<IDailyReports>;
export type EntityArrayResponseType = HttpResponse<IDailyReports[]>;

@Injectable({ providedIn: 'root' })
export class DailyReportsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/daily-reports');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dailyReports: IDailyReports): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dailyReports);
    return this.http
      .post<IDailyReports>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dailyReports: IDailyReports): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dailyReports);
    return this.http
      .put<IDailyReports>(`${this.resourceUrl}/${getDailyReportsIdentifier(dailyReports) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(dailyReports: IDailyReports): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dailyReports);
    return this.http
      .patch<IDailyReports>(`${this.resourceUrl}/${getDailyReportsIdentifier(dailyReports) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDailyReports>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDailyReports[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDailyReportsToCollectionIfMissing(
    dailyReportsCollection: IDailyReports[],
    ...dailyReportsToCheck: (IDailyReports | null | undefined)[]
  ): IDailyReports[] {
    const dailyReports: IDailyReports[] = dailyReportsToCheck.filter(isPresent);
    if (dailyReports.length > 0) {
      const dailyReportsCollectionIdentifiers = dailyReportsCollection.map(
        dailyReportsItem => getDailyReportsIdentifier(dailyReportsItem)!
      );
      const dailyReportsToAdd = dailyReports.filter(dailyReportsItem => {
        const dailyReportsIdentifier = getDailyReportsIdentifier(dailyReportsItem);
        if (dailyReportsIdentifier == null || dailyReportsCollectionIdentifiers.includes(dailyReportsIdentifier)) {
          return false;
        }
        dailyReportsCollectionIdentifiers.push(dailyReportsIdentifier);
        return true;
      });
      return [...dailyReportsToAdd, ...dailyReportsCollection];
    }
    return dailyReportsCollection;
  }

  protected convertDateFromClient(dailyReports: IDailyReports): IDailyReports {
    return Object.assign({}, dailyReports, {
      date: dailyReports.date?.isValid() ? dailyReports.date.toJSON() : undefined,
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
      res.body.forEach((dailyReports: IDailyReports) => {
        dailyReports.date = dailyReports.date ? dayjs(dailyReports.date) : undefined;
      });
    }
    return res;
  }
}
