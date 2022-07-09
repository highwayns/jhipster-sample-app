import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaymentStep, getPaymentStepIdentifier } from '../payment-step.model';

export type EntityResponseType = HttpResponse<IPaymentStep>;
export type EntityArrayResponseType = HttpResponse<IPaymentStep[]>;

@Injectable({ providedIn: 'root' })
export class PaymentStepService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payment-steps');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(paymentStep: IPaymentStep): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentStep);
    return this.http
      .post<IPaymentStep>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(paymentStep: IPaymentStep): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentStep);
    return this.http
      .put<IPaymentStep>(`${this.resourceUrl}/${getPaymentStepIdentifier(paymentStep) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(paymentStep: IPaymentStep): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentStep);
    return this.http
      .patch<IPaymentStep>(`${this.resourceUrl}/${getPaymentStepIdentifier(paymentStep) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPaymentStep>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPaymentStep[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPaymentStepToCollectionIfMissing(
    paymentStepCollection: IPaymentStep[],
    ...paymentStepsToCheck: (IPaymentStep | null | undefined)[]
  ): IPaymentStep[] {
    const paymentSteps: IPaymentStep[] = paymentStepsToCheck.filter(isPresent);
    if (paymentSteps.length > 0) {
      const paymentStepCollectionIdentifiers = paymentStepCollection.map(paymentStepItem => getPaymentStepIdentifier(paymentStepItem)!);
      const paymentStepsToAdd = paymentSteps.filter(paymentStepItem => {
        const paymentStepIdentifier = getPaymentStepIdentifier(paymentStepItem);
        if (paymentStepIdentifier == null || paymentStepCollectionIdentifiers.includes(paymentStepIdentifier)) {
          return false;
        }
        paymentStepCollectionIdentifiers.push(paymentStepIdentifier);
        return true;
      });
      return [...paymentStepsToAdd, ...paymentStepCollection];
    }
    return paymentStepCollection;
  }

  protected convertDateFromClient(paymentStep: IPaymentStep): IPaymentStep {
    return Object.assign({}, paymentStep, {
      createDateTimeUtc: paymentStep.createDateTimeUtc?.isValid() ? paymentStep.createDateTimeUtc.toJSON() : undefined,
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
      res.body.forEach((paymentStep: IPaymentStep) => {
        paymentStep.createDateTimeUtc = paymentStep.createDateTimeUtc ? dayjs(paymentStep.createDateTimeUtc) : undefined;
      });
    }
    return res;
  }
}
