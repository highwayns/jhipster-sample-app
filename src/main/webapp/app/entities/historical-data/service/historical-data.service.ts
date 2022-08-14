import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHistoricalData, getHistoricalDataIdentifier } from '../historical-data.model';

export type EntityResponseType = HttpResponse<IHistoricalData>;
export type EntityArrayResponseType = HttpResponse<IHistoricalData[]>;

@Injectable({ providedIn: 'root' })
export class HistoricalDataService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/historical-data');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(historicalData: IHistoricalData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historicalData);
    return this.http
      .post<IHistoricalData>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(historicalData: IHistoricalData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historicalData);
    return this.http
      .put<IHistoricalData>(`${this.resourceUrl}/${getHistoricalDataIdentifier(historicalData) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(historicalData: IHistoricalData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historicalData);
    return this.http
      .patch<IHistoricalData>(`${this.resourceUrl}/${getHistoricalDataIdentifier(historicalData) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHistoricalData>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHistoricalData[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addHistoricalDataToCollectionIfMissing(
    historicalDataCollection: IHistoricalData[],
    ...historicalDataToCheck: (IHistoricalData | null | undefined)[]
  ): IHistoricalData[] {
    const historicalData: IHistoricalData[] = historicalDataToCheck.filter(isPresent);
    if (historicalData.length > 0) {
      const historicalDataCollectionIdentifiers = historicalDataCollection.map(
        historicalDataItem => getHistoricalDataIdentifier(historicalDataItem)!
      );
      const historicalDataToAdd = historicalData.filter(historicalDataItem => {
        const historicalDataIdentifier = getHistoricalDataIdentifier(historicalDataItem);
        if (historicalDataIdentifier == null || historicalDataCollectionIdentifiers.includes(historicalDataIdentifier)) {
          return false;
        }
        historicalDataCollectionIdentifiers.push(historicalDataIdentifier);
        return true;
      });
      return [...historicalDataToAdd, ...historicalDataCollection];
    }
    return historicalDataCollection;
  }

  protected convertDateFromClient(historicalData: IHistoricalData): IHistoricalData {
    return Object.assign({}, historicalData, {
      date: historicalData.date?.isValid() ? historicalData.date.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((historicalData: IHistoricalData) => {
        historicalData.date = historicalData.date ? dayjs(historicalData.date) : undefined;
      });
    }
    return res;
  }
}
