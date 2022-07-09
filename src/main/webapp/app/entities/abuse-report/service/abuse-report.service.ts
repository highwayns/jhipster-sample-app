import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAbuseReport, getAbuseReportIdentifier } from '../abuse-report.model';

export type EntityResponseType = HttpResponse<IAbuseReport>;
export type EntityArrayResponseType = HttpResponse<IAbuseReport[]>;

@Injectable({ providedIn: 'root' })
export class AbuseReportService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/abuse-reports');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(abuseReport: IAbuseReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(abuseReport);
    return this.http
      .post<IAbuseReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(abuseReport: IAbuseReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(abuseReport);
    return this.http
      .put<IAbuseReport>(`${this.resourceUrl}/${getAbuseReportIdentifier(abuseReport) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(abuseReport: IAbuseReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(abuseReport);
    return this.http
      .patch<IAbuseReport>(`${this.resourceUrl}/${getAbuseReportIdentifier(abuseReport) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAbuseReport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAbuseReport[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAbuseReportToCollectionIfMissing(
    abuseReportCollection: IAbuseReport[],
    ...abuseReportsToCheck: (IAbuseReport | null | undefined)[]
  ): IAbuseReport[] {
    const abuseReports: IAbuseReport[] = abuseReportsToCheck.filter(isPresent);
    if (abuseReports.length > 0) {
      const abuseReportCollectionIdentifiers = abuseReportCollection.map(abuseReportItem => getAbuseReportIdentifier(abuseReportItem)!);
      const abuseReportsToAdd = abuseReports.filter(abuseReportItem => {
        const abuseReportIdentifier = getAbuseReportIdentifier(abuseReportItem);
        if (abuseReportIdentifier == null || abuseReportCollectionIdentifiers.includes(abuseReportIdentifier)) {
          return false;
        }
        abuseReportCollectionIdentifiers.push(abuseReportIdentifier);
        return true;
      });
      return [...abuseReportsToAdd, ...abuseReportCollection];
    }
    return abuseReportCollection;
  }

  protected convertDateFromClient(abuseReport: IAbuseReport): IAbuseReport {
    return Object.assign({}, abuseReport, {
      createdDateTimeUtc: abuseReport.createdDateTimeUtc?.isValid() ? abuseReport.createdDateTimeUtc.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDateTimeUtc = res.body.createdDateTimeUtc ? dayjs(res.body.createdDateTimeUtc) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((abuseReport: IAbuseReport) => {
        abuseReport.createdDateTimeUtc = abuseReport.createdDateTimeUtc ? dayjs(abuseReport.createdDateTimeUtc) : undefined;
      });
    }
    return res;
  }
}
