import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRefundStep, getRefundStepIdentifier } from '../refund-step.model';

export type EntityResponseType = HttpResponse<IRefundStep>;
export type EntityArrayResponseType = HttpResponse<IRefundStep[]>;

@Injectable({ providedIn: 'root' })
export class RefundStepService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/refund-steps');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(refundStep: IRefundStep): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(refundStep);
    return this.http
      .post<IRefundStep>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(refundStep: IRefundStep): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(refundStep);
    return this.http
      .put<IRefundStep>(`${this.resourceUrl}/${getRefundStepIdentifier(refundStep) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(refundStep: IRefundStep): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(refundStep);
    return this.http
      .patch<IRefundStep>(`${this.resourceUrl}/${getRefundStepIdentifier(refundStep) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRefundStep>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRefundStep[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRefundStepToCollectionIfMissing(
    refundStepCollection: IRefundStep[],
    ...refundStepsToCheck: (IRefundStep | null | undefined)[]
  ): IRefundStep[] {
    const refundSteps: IRefundStep[] = refundStepsToCheck.filter(isPresent);
    if (refundSteps.length > 0) {
      const refundStepCollectionIdentifiers = refundStepCollection.map(refundStepItem => getRefundStepIdentifier(refundStepItem)!);
      const refundStepsToAdd = refundSteps.filter(refundStepItem => {
        const refundStepIdentifier = getRefundStepIdentifier(refundStepItem);
        if (refundStepIdentifier == null || refundStepCollectionIdentifiers.includes(refundStepIdentifier)) {
          return false;
        }
        refundStepCollectionIdentifiers.push(refundStepIdentifier);
        return true;
      });
      return [...refundStepsToAdd, ...refundStepCollection];
    }
    return refundStepCollection;
  }

  protected convertDateFromClient(refundStep: IRefundStep): IRefundStep {
    return Object.assign({}, refundStep, {
      createDateTimeUtc: refundStep.createDateTimeUtc?.isValid() ? refundStep.createDateTimeUtc.toJSON() : undefined,
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
      res.body.forEach((refundStep: IRefundStep) => {
        refundStep.createDateTimeUtc = refundStep.createDateTimeUtc ? dayjs(refundStep.createDateTimeUtc) : undefined;
      });
    }
    return res;
  }
}
