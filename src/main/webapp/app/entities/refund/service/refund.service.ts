import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRefund, getRefundIdentifier } from '../refund.model';

export type EntityResponseType = HttpResponse<IRefund>;
export type EntityArrayResponseType = HttpResponse<IRefund[]>;

@Injectable({ providedIn: 'root' })
export class RefundService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/refunds');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(refund: IRefund): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(refund);
    return this.http
      .post<IRefund>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(refund: IRefund): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(refund);
    return this.http
      .put<IRefund>(`${this.resourceUrl}/${getRefundIdentifier(refund) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(refund: IRefund): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(refund);
    return this.http
      .patch<IRefund>(`${this.resourceUrl}/${getRefundIdentifier(refund) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRefund>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRefund[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRefundToCollectionIfMissing(refundCollection: IRefund[], ...refundsToCheck: (IRefund | null | undefined)[]): IRefund[] {
    const refunds: IRefund[] = refundsToCheck.filter(isPresent);
    if (refunds.length > 0) {
      const refundCollectionIdentifiers = refundCollection.map(refundItem => getRefundIdentifier(refundItem)!);
      const refundsToAdd = refunds.filter(refundItem => {
        const refundIdentifier = getRefundIdentifier(refundItem);
        if (refundIdentifier == null || refundCollectionIdentifiers.includes(refundIdentifier)) {
          return false;
        }
        refundCollectionIdentifiers.push(refundIdentifier);
        return true;
      });
      return [...refundsToAdd, ...refundCollection];
    }
    return refundCollection;
  }

  protected convertDateFromClient(refund: IRefund): IRefund {
    return Object.assign({}, refund, {
      createDateTimeUtc: refund.createDateTimeUtc?.isValid() ? refund.createDateTimeUtc.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createDateTimeUtc = res.body.createDateTimeUtc ? dayjs(res.body.createDateTimeUtc) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((refund: IRefund) => {
        refund.createDateTimeUtc = refund.createDateTimeUtc ? dayjs(refund.createDateTimeUtc) : undefined;
      });
    }
    return res;
  }
}
