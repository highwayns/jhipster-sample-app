import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMonthlyReports, getMonthlyReportsIdentifier } from '../monthly-reports.model';

export type EntityResponseType = HttpResponse<IMonthlyReports>;
export type EntityArrayResponseType = HttpResponse<IMonthlyReports[]>;

@Injectable({ providedIn: 'root' })
export class MonthlyReportsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/monthly-reports');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(monthlyReports: IMonthlyReports): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(monthlyReports);
    return this.http
      .post<IMonthlyReports>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(monthlyReports: IMonthlyReports): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(monthlyReports);
    return this.http
      .put<IMonthlyReports>(`${this.resourceUrl}/${getMonthlyReportsIdentifier(monthlyReports) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(monthlyReports: IMonthlyReports): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(monthlyReports);
    return this.http
      .patch<IMonthlyReports>(`${this.resourceUrl}/${getMonthlyReportsIdentifier(monthlyReports) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMonthlyReports>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMonthlyReports[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMonthlyReportsToCollectionIfMissing(
    monthlyReportsCollection: IMonthlyReports[],
    ...monthlyReportsToCheck: (IMonthlyReports | null | undefined)[]
  ): IMonthlyReports[] {
    const monthlyReports: IMonthlyReports[] = monthlyReportsToCheck.filter(isPresent);
    if (monthlyReports.length > 0) {
      const monthlyReportsCollectionIdentifiers = monthlyReportsCollection.map(
        monthlyReportsItem => getMonthlyReportsIdentifier(monthlyReportsItem)!
      );
      const monthlyReportsToAdd = monthlyReports.filter(monthlyReportsItem => {
        const monthlyReportsIdentifier = getMonthlyReportsIdentifier(monthlyReportsItem);
        if (monthlyReportsIdentifier == null || monthlyReportsCollectionIdentifiers.includes(monthlyReportsIdentifier)) {
          return false;
        }
        monthlyReportsCollectionIdentifiers.push(monthlyReportsIdentifier);
        return true;
      });
      return [...monthlyReportsToAdd, ...monthlyReportsCollection];
    }
    return monthlyReportsCollection;
  }

  protected convertDateFromClient(monthlyReports: IMonthlyReports): IMonthlyReports {
    return Object.assign({}, monthlyReports, {
      date: monthlyReports.date?.isValid() ? monthlyReports.date.toJSON() : undefined,
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
      res.body.forEach((monthlyReports: IMonthlyReports) => {
        monthlyReports.date = monthlyReports.date ? dayjs(monthlyReports.date) : undefined;
      });
    }
    return res;
  }
}
