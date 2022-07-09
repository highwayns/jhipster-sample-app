import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRecurrenceCriteria, getRecurrenceCriteriaIdentifier } from '../recurrence-criteria.model';

export type EntityResponseType = HttpResponse<IRecurrenceCriteria>;
export type EntityArrayResponseType = HttpResponse<IRecurrenceCriteria[]>;

@Injectable({ providedIn: 'root' })
export class RecurrenceCriteriaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/recurrence-criteria');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(recurrenceCriteria: IRecurrenceCriteria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recurrenceCriteria);
    return this.http
      .post<IRecurrenceCriteria>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(recurrenceCriteria: IRecurrenceCriteria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recurrenceCriteria);
    return this.http
      .put<IRecurrenceCriteria>(`${this.resourceUrl}/${getRecurrenceCriteriaIdentifier(recurrenceCriteria) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(recurrenceCriteria: IRecurrenceCriteria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recurrenceCriteria);
    return this.http
      .patch<IRecurrenceCriteria>(`${this.resourceUrl}/${getRecurrenceCriteriaIdentifier(recurrenceCriteria) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRecurrenceCriteria>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRecurrenceCriteria[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRecurrenceCriteriaToCollectionIfMissing(
    recurrenceCriteriaCollection: IRecurrenceCriteria[],
    ...recurrenceCriteriaToCheck: (IRecurrenceCriteria | null | undefined)[]
  ): IRecurrenceCriteria[] {
    const recurrenceCriteria: IRecurrenceCriteria[] = recurrenceCriteriaToCheck.filter(isPresent);
    if (recurrenceCriteria.length > 0) {
      const recurrenceCriteriaCollectionIdentifiers = recurrenceCriteriaCollection.map(
        recurrenceCriteriaItem => getRecurrenceCriteriaIdentifier(recurrenceCriteriaItem)!
      );
      const recurrenceCriteriaToAdd = recurrenceCriteria.filter(recurrenceCriteriaItem => {
        const recurrenceCriteriaIdentifier = getRecurrenceCriteriaIdentifier(recurrenceCriteriaItem);
        if (recurrenceCriteriaIdentifier == null || recurrenceCriteriaCollectionIdentifiers.includes(recurrenceCriteriaIdentifier)) {
          return false;
        }
        recurrenceCriteriaCollectionIdentifiers.push(recurrenceCriteriaIdentifier);
        return true;
      });
      return [...recurrenceCriteriaToAdd, ...recurrenceCriteriaCollection];
    }
    return recurrenceCriteriaCollection;
  }

  protected convertDateFromClient(recurrenceCriteria: IRecurrenceCriteria): IRecurrenceCriteria {
    return Object.assign({}, recurrenceCriteria, {
      recurringExpiry: recurrenceCriteria.recurringExpiry?.isValid() ? recurrenceCriteria.recurringExpiry.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.recurringExpiry = res.body.recurringExpiry ? dayjs(res.body.recurringExpiry) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((recurrenceCriteria: IRecurrenceCriteria) => {
        recurrenceCriteria.recurringExpiry = recurrenceCriteria.recurringExpiry ? dayjs(recurrenceCriteria.recurringExpiry) : undefined;
      });
    }
    return res;
  }
}
