import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IConversions, getConversionsIdentifier } from '../conversions.model';

export type EntityResponseType = HttpResponse<IConversions>;
export type EntityArrayResponseType = HttpResponse<IConversions[]>;

@Injectable({ providedIn: 'root' })
export class ConversionsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/conversions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(conversions: IConversions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(conversions);
    return this.http
      .post<IConversions>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(conversions: IConversions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(conversions);
    return this.http
      .put<IConversions>(`${this.resourceUrl}/${getConversionsIdentifier(conversions) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(conversions: IConversions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(conversions);
    return this.http
      .patch<IConversions>(`${this.resourceUrl}/${getConversionsIdentifier(conversions) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IConversions>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IConversions[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addConversionsToCollectionIfMissing(
    conversionsCollection: IConversions[],
    ...conversionsToCheck: (IConversions | null | undefined)[]
  ): IConversions[] {
    const conversions: IConversions[] = conversionsToCheck.filter(isPresent);
    if (conversions.length > 0) {
      const conversionsCollectionIdentifiers = conversionsCollection.map(conversionsItem => getConversionsIdentifier(conversionsItem)!);
      const conversionsToAdd = conversions.filter(conversionsItem => {
        const conversionsIdentifier = getConversionsIdentifier(conversionsItem);
        if (conversionsIdentifier == null || conversionsCollectionIdentifiers.includes(conversionsIdentifier)) {
          return false;
        }
        conversionsCollectionIdentifiers.push(conversionsIdentifier);
        return true;
      });
      return [...conversionsToAdd, ...conversionsCollection];
    }
    return conversionsCollection;
  }

  protected convertDateFromClient(conversions: IConversions): IConversions {
    return Object.assign({}, conversions, {
      date: conversions.date?.isValid() ? conversions.date.toJSON() : undefined,
      date1: conversions.date1?.isValid() ? conversions.date1.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? dayjs(res.body.date) : undefined;
      res.body.date1 = res.body.date1 ? dayjs(res.body.date1) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((conversions: IConversions) => {
        conversions.date = conversions.date ? dayjs(conversions.date) : undefined;
        conversions.date1 = conversions.date1 ? dayjs(conversions.date1) : undefined;
      });
    }
    return res;
  }
}
