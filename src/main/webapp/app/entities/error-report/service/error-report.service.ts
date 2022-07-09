import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IErrorReport, getErrorReportIdentifier } from '../error-report.model';

export type EntityResponseType = HttpResponse<IErrorReport>;
export type EntityArrayResponseType = HttpResponse<IErrorReport[]>;

@Injectable({ providedIn: 'root' })
export class ErrorReportService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/error-reports');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(errorReport: IErrorReport): Observable<EntityResponseType> {
    return this.http.post<IErrorReport>(this.resourceUrl, errorReport, { observe: 'response' });
  }

  update(errorReport: IErrorReport): Observable<EntityResponseType> {
    return this.http.put<IErrorReport>(`${this.resourceUrl}/${getErrorReportIdentifier(errorReport) as number}`, errorReport, {
      observe: 'response',
    });
  }

  partialUpdate(errorReport: IErrorReport): Observable<EntityResponseType> {
    return this.http.patch<IErrorReport>(`${this.resourceUrl}/${getErrorReportIdentifier(errorReport) as number}`, errorReport, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IErrorReport>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IErrorReport[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addErrorReportToCollectionIfMissing(
    errorReportCollection: IErrorReport[],
    ...errorReportsToCheck: (IErrorReport | null | undefined)[]
  ): IErrorReport[] {
    const errorReports: IErrorReport[] = errorReportsToCheck.filter(isPresent);
    if (errorReports.length > 0) {
      const errorReportCollectionIdentifiers = errorReportCollection.map(errorReportItem => getErrorReportIdentifier(errorReportItem)!);
      const errorReportsToAdd = errorReports.filter(errorReportItem => {
        const errorReportIdentifier = getErrorReportIdentifier(errorReportItem);
        if (errorReportIdentifier == null || errorReportCollectionIdentifiers.includes(errorReportIdentifier)) {
          return false;
        }
        errorReportCollectionIdentifiers.push(errorReportIdentifier);
        return true;
      });
      return [...errorReportsToAdd, ...errorReportCollection];
    }
    return errorReportCollection;
  }
}
