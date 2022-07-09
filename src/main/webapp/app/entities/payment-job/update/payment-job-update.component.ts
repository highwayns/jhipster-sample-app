import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPaymentJob, PaymentJob } from '../payment-job.model';
import { PaymentJobService } from '../service/payment-job.service';
import { IOrder } from 'app/entities/order/order.model';
import { OrderService } from 'app/entities/order/service/order.service';
import { IPaymentJobAttributes } from 'app/entities/payment-job-attributes/payment-job-attributes.model';
import { PaymentJobAttributesService } from 'app/entities/payment-job-attributes/service/payment-job-attributes.service';
import { IRecurrenceCriteria } from 'app/entities/recurrence-criteria/recurrence-criteria.model';
import { RecurrenceCriteriaService } from 'app/entities/recurrence-criteria/service/recurrence-criteria.service';
import { PaymentJobType } from 'app/entities/enumerations/payment-job-type.model';
import { Locale } from 'app/entities/enumerations/locale.model';
import { Currency } from 'app/entities/enumerations/currency.model';

@Component({
  selector: 'jhi-payment-job-update',
  templateUrl: './payment-job-update.component.html',
})
export class PaymentJobUpdateComponent implements OnInit {
  isSaving = false;
  paymentJobTypeValues = Object.keys(PaymentJobType);
  localeValues = Object.keys(Locale);
  currencyValues = Object.keys(Currency);

  ordersCollection: IOrder[] = [];
  attributesCollection: IPaymentJobAttributes[] = [];
  recurrenceCriteriaCollection: IRecurrenceCriteria[] = [];

  editForm = this.fb.group({
    id: [],
    reference: [],
    createDateTimeUtc: [],
    type: [],
    traceReference: [],
    configurationId: [],
    domain: [],
    locale: [],
    timeZone: [],
    parentPaymentJobReference: [],
    displayUrl: [],
    currency: [],
    amountToCollect: [],
    amountCollected: [],
    paidAmount: [],
    paidDateTimeUtc: [],
    expirationDateTimeUtc: [],
    dueDateTimeUtc: [],
    lastUpdateTimeUtc: [],
    lastProcessedTimeUtc: [],
    order: [],
    attributes: [],
    recurrenceCriteria: [],
  });

  constructor(
    protected paymentJobService: PaymentJobService,
    protected orderService: OrderService,
    protected paymentJobAttributesService: PaymentJobAttributesService,
    protected recurrenceCriteriaService: RecurrenceCriteriaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentJob }) => {
      if (paymentJob.id === undefined) {
        const today = dayjs().startOf('day');
        paymentJob.createDateTimeUtc = today;
        paymentJob.paidDateTimeUtc = today;
        paymentJob.expirationDateTimeUtc = today;
        paymentJob.dueDateTimeUtc = today;
        paymentJob.lastUpdateTimeUtc = today;
        paymentJob.lastProcessedTimeUtc = today;
      }

      this.updateForm(paymentJob);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentJob = this.createFromForm();
    if (paymentJob.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentJobService.update(paymentJob));
    } else {
      this.subscribeToSaveResponse(this.paymentJobService.create(paymentJob));
    }
  }

  trackOrderById(_index: number, item: IOrder): number {
    return item.id!;
  }

  trackPaymentJobAttributesById(_index: number, item: IPaymentJobAttributes): number {
    return item.id!;
  }

  trackRecurrenceCriteriaById(_index: number, item: IRecurrenceCriteria): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentJob>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(paymentJob: IPaymentJob): void {
    this.editForm.patchValue({
      id: paymentJob.id,
      reference: paymentJob.reference,
      createDateTimeUtc: paymentJob.createDateTimeUtc ? paymentJob.createDateTimeUtc.format(DATE_TIME_FORMAT) : null,
      type: paymentJob.type,
      traceReference: paymentJob.traceReference,
      configurationId: paymentJob.configurationId,
      domain: paymentJob.domain,
      locale: paymentJob.locale,
      timeZone: paymentJob.timeZone,
      parentPaymentJobReference: paymentJob.parentPaymentJobReference,
      displayUrl: paymentJob.displayUrl,
      currency: paymentJob.currency,
      amountToCollect: paymentJob.amountToCollect,
      amountCollected: paymentJob.amountCollected,
      paidAmount: paymentJob.paidAmount,
      paidDateTimeUtc: paymentJob.paidDateTimeUtc ? paymentJob.paidDateTimeUtc.format(DATE_TIME_FORMAT) : null,
      expirationDateTimeUtc: paymentJob.expirationDateTimeUtc ? paymentJob.expirationDateTimeUtc.format(DATE_TIME_FORMAT) : null,
      dueDateTimeUtc: paymentJob.dueDateTimeUtc ? paymentJob.dueDateTimeUtc.format(DATE_TIME_FORMAT) : null,
      lastUpdateTimeUtc: paymentJob.lastUpdateTimeUtc ? paymentJob.lastUpdateTimeUtc.format(DATE_TIME_FORMAT) : null,
      lastProcessedTimeUtc: paymentJob.lastProcessedTimeUtc ? paymentJob.lastProcessedTimeUtc.format(DATE_TIME_FORMAT) : null,
      order: paymentJob.order,
      attributes: paymentJob.attributes,
      recurrenceCriteria: paymentJob.recurrenceCriteria,
    });

    this.ordersCollection = this.orderService.addOrderToCollectionIfMissing(this.ordersCollection, paymentJob.order);
    this.attributesCollection = this.paymentJobAttributesService.addPaymentJobAttributesToCollectionIfMissing(
      this.attributesCollection,
      paymentJob.attributes
    );
    this.recurrenceCriteriaCollection = this.recurrenceCriteriaService.addRecurrenceCriteriaToCollectionIfMissing(
      this.recurrenceCriteriaCollection,
      paymentJob.recurrenceCriteria
    );
  }

  protected loadRelationshipsOptions(): void {
    this.orderService
      .query({ filter: 'paymentjob-is-null' })
      .pipe(map((res: HttpResponse<IOrder[]>) => res.body ?? []))
      .pipe(map((orders: IOrder[]) => this.orderService.addOrderToCollectionIfMissing(orders, this.editForm.get('order')!.value)))
      .subscribe((orders: IOrder[]) => (this.ordersCollection = orders));

    this.paymentJobAttributesService
      .query({ filter: 'paymentjob-is-null' })
      .pipe(map((res: HttpResponse<IPaymentJobAttributes[]>) => res.body ?? []))
      .pipe(
        map((paymentJobAttributes: IPaymentJobAttributes[]) =>
          this.paymentJobAttributesService.addPaymentJobAttributesToCollectionIfMissing(
            paymentJobAttributes,
            this.editForm.get('attributes')!.value
          )
        )
      )
      .subscribe((paymentJobAttributes: IPaymentJobAttributes[]) => (this.attributesCollection = paymentJobAttributes));

    this.recurrenceCriteriaService
      .query({ filter: 'paymentjob-is-null' })
      .pipe(map((res: HttpResponse<IRecurrenceCriteria[]>) => res.body ?? []))
      .pipe(
        map((recurrenceCriteria: IRecurrenceCriteria[]) =>
          this.recurrenceCriteriaService.addRecurrenceCriteriaToCollectionIfMissing(
            recurrenceCriteria,
            this.editForm.get('recurrenceCriteria')!.value
          )
        )
      )
      .subscribe((recurrenceCriteria: IRecurrenceCriteria[]) => (this.recurrenceCriteriaCollection = recurrenceCriteria));
  }

  protected createFromForm(): IPaymentJob {
    return {
      ...new PaymentJob(),
      id: this.editForm.get(['id'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      createDateTimeUtc: this.editForm.get(['createDateTimeUtc'])!.value
        ? dayjs(this.editForm.get(['createDateTimeUtc'])!.value, DATE_TIME_FORMAT)
        : undefined,
      type: this.editForm.get(['type'])!.value,
      traceReference: this.editForm.get(['traceReference'])!.value,
      configurationId: this.editForm.get(['configurationId'])!.value,
      domain: this.editForm.get(['domain'])!.value,
      locale: this.editForm.get(['locale'])!.value,
      timeZone: this.editForm.get(['timeZone'])!.value,
      parentPaymentJobReference: this.editForm.get(['parentPaymentJobReference'])!.value,
      displayUrl: this.editForm.get(['displayUrl'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      amountToCollect: this.editForm.get(['amountToCollect'])!.value,
      amountCollected: this.editForm.get(['amountCollected'])!.value,
      paidAmount: this.editForm.get(['paidAmount'])!.value,
      paidDateTimeUtc: this.editForm.get(['paidDateTimeUtc'])!.value
        ? dayjs(this.editForm.get(['paidDateTimeUtc'])!.value, DATE_TIME_FORMAT)
        : undefined,
      expirationDateTimeUtc: this.editForm.get(['expirationDateTimeUtc'])!.value
        ? dayjs(this.editForm.get(['expirationDateTimeUtc'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dueDateTimeUtc: this.editForm.get(['dueDateTimeUtc'])!.value
        ? dayjs(this.editForm.get(['dueDateTimeUtc'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastUpdateTimeUtc: this.editForm.get(['lastUpdateTimeUtc'])!.value
        ? dayjs(this.editForm.get(['lastUpdateTimeUtc'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastProcessedTimeUtc: this.editForm.get(['lastProcessedTimeUtc'])!.value
        ? dayjs(this.editForm.get(['lastProcessedTimeUtc'])!.value, DATE_TIME_FORMAT)
        : undefined,
      order: this.editForm.get(['order'])!.value,
      attributes: this.editForm.get(['attributes'])!.value,
      recurrenceCriteria: this.editForm.get(['recurrenceCriteria'])!.value,
    };
  }
}
