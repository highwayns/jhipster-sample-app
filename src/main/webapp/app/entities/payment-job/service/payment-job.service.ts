import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaymentJob, getPaymentJobIdentifier } from '../payment-job.model';

export type EntityResponseType = HttpResponse<IPaymentJob>;
export type EntityArrayResponseType = HttpResponse<IPaymentJob[]>;

@Injectable({ providedIn: 'root' })
export class PaymentJobService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payment-jobs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(paymentJob: IPaymentJob): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentJob);
    return this.http
      .post<IPaymentJob>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(paymentJob: IPaymentJob): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentJob);
    return this.http
      .put<IPaymentJob>(`${this.resourceUrl}/${getPaymentJobIdentifier(paymentJob) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(paymentJob: IPaymentJob): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentJob);
    return this.http
      .patch<IPaymentJob>(`${this.resourceUrl}/${getPaymentJobIdentifier(paymentJob) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPaymentJob>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPaymentJob[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPaymentJobToCollectionIfMissing(
    paymentJobCollection: IPaymentJob[],
    ...paymentJobsToCheck: (IPaymentJob | null | undefined)[]
  ): IPaymentJob[] {
    const paymentJobs: IPaymentJob[] = paymentJobsToCheck.filter(isPresent);
    if (paymentJobs.length > 0) {
      const paymentJobCollectionIdentifiers = paymentJobCollection.map(paymentJobItem => getPaymentJobIdentifier(paymentJobItem)!);
      const paymentJobsToAdd = paymentJobs.filter(paymentJobItem => {
        const paymentJobIdentifier = getPaymentJobIdentifier(paymentJobItem);
        if (paymentJobIdentifier == null || paymentJobCollectionIdentifiers.includes(paymentJobIdentifier)) {
          return false;
        }
        paymentJobCollectionIdentifiers.push(paymentJobIdentifier);
        return true;
      });
      return [...paymentJobsToAdd, ...paymentJobCollection];
    }
    return paymentJobCollection;
  }

  protected convertDateFromClient(paymentJob: IPaymentJob): IPaymentJob {
    return Object.assign({}, paymentJob, {
      createDateTimeUtc: paymentJob.createDateTimeUtc?.isValid() ? paymentJob.createDateTimeUtc.toJSON() : undefined,
      paidDateTimeUtc: paymentJob.paidDateTimeUtc?.isValid() ? paymentJob.paidDateTimeUtc.toJSON() : undefined,
      expirationDateTimeUtc: paymentJob.expirationDateTimeUtc?.isValid() ? paymentJob.expirationDateTimeUtc.toJSON() : undefined,
      dueDateTimeUtc: paymentJob.dueDateTimeUtc?.isValid() ? paymentJob.dueDateTimeUtc.toJSON() : undefined,
      lastUpdateTimeUtc: paymentJob.lastUpdateTimeUtc?.isValid() ? paymentJob.lastUpdateTimeUtc.toJSON() : undefined,
      lastProcessedTimeUtc: paymentJob.lastProcessedTimeUtc?.isValid() ? paymentJob.lastProcessedTimeUtc.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createDateTimeUtc = res.body.createDateTimeUtc ? dayjs(res.body.createDateTimeUtc) : undefined;
      res.body.paidDateTimeUtc = res.body.paidDateTimeUtc ? dayjs(res.body.paidDateTimeUtc) : undefined;
      res.body.expirationDateTimeUtc = res.body.expirationDateTimeUtc ? dayjs(res.body.expirationDateTimeUtc) : undefined;
      res.body.dueDateTimeUtc = res.body.dueDateTimeUtc ? dayjs(res.body.dueDateTimeUtc) : undefined;
      res.body.lastUpdateTimeUtc = res.body.lastUpdateTimeUtc ? dayjs(res.body.lastUpdateTimeUtc) : undefined;
      res.body.lastProcessedTimeUtc = res.body.lastProcessedTimeUtc ? dayjs(res.body.lastProcessedTimeUtc) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((paymentJob: IPaymentJob) => {
        paymentJob.createDateTimeUtc = paymentJob.createDateTimeUtc ? dayjs(paymentJob.createDateTimeUtc) : undefined;
        paymentJob.paidDateTimeUtc = paymentJob.paidDateTimeUtc ? dayjs(paymentJob.paidDateTimeUtc) : undefined;
        paymentJob.expirationDateTimeUtc = paymentJob.expirationDateTimeUtc ? dayjs(paymentJob.expirationDateTimeUtc) : undefined;
        paymentJob.dueDateTimeUtc = paymentJob.dueDateTimeUtc ? dayjs(paymentJob.dueDateTimeUtc) : undefined;
        paymentJob.lastUpdateTimeUtc = paymentJob.lastUpdateTimeUtc ? dayjs(paymentJob.lastUpdateTimeUtc) : undefined;
        paymentJob.lastProcessedTimeUtc = paymentJob.lastProcessedTimeUtc ? dayjs(paymentJob.lastProcessedTimeUtc) : undefined;
      });
    }
    return res;
  }
}
