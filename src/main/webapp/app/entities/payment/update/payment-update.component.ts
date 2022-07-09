import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPayment, Payment } from '../payment.model';
import { PaymentService } from '../service/payment.service';
import { IErrorReport } from 'app/entities/error-report/error-report.model';
import { ErrorReportService } from 'app/entities/error-report/service/error-report.service';
import { IAbuseReport } from 'app/entities/abuse-report/abuse-report.model';
import { AbuseReportService } from 'app/entities/abuse-report/service/abuse-report.service';
import { IPaymentAttributes } from 'app/entities/payment-attributes/payment-attributes.model';
import { PaymentAttributesService } from 'app/entities/payment-attributes/service/payment-attributes.service';
import { IPaymentMethods } from 'app/entities/payment-methods/payment-methods.model';
import { PaymentMethodsService } from 'app/entities/payment-methods/service/payment-methods.service';
import { IPaymentStep } from 'app/entities/payment-step/payment-step.model';
import { PaymentStepService } from 'app/entities/payment-step/service/payment-step.service';
import { IRefund } from 'app/entities/refund/refund.model';
import { RefundService } from 'app/entities/refund/service/refund.service';
import { ICapture } from 'app/entities/capture/capture.model';
import { CaptureService } from 'app/entities/capture/service/capture.service';
import { PaymentStatus } from 'app/entities/enumerations/payment-status.model';
import { Currency } from 'app/entities/enumerations/currency.model';

@Component({
  selector: 'jhi-payment-update',
  templateUrl: './payment-update.component.html',
})
export class PaymentUpdateComponent implements OnInit {
  isSaving = false;
  paymentStatusValues = Object.keys(PaymentStatus);
  currencyValues = Object.keys(Currency);

  lastErrorReportsCollection: IErrorReport[] = [];
  abuseReportsCollection: IAbuseReport[] = [];
  attributesCollection: IPaymentAttributes[] = [];
  paymentMethodsSharedCollection: IPaymentMethods[] = [];
  paymentStepsSharedCollection: IPaymentStep[] = [];
  refundsSharedCollection: IRefund[] = [];
  capturesSharedCollection: ICapture[] = [];

  editForm = this.fb.group({
    id: [],
    reference: [],
    createDateTimeUtc: [],
    status: [],
    amountToCollect: [],
    surchargeAmount: [],
    convertedTotalAmount: [],
    convertedCurrency: [],
    conversionRate: [],
    paidAmount: [],
    lastErrorReport: [],
    abuseReport: [],
    attributes: [],
    paymentMethods: [],
    steps: [],
    refunds: [],
    captures: [],
  });

  constructor(
    protected paymentService: PaymentService,
    protected errorReportService: ErrorReportService,
    protected abuseReportService: AbuseReportService,
    protected paymentAttributesService: PaymentAttributesService,
    protected paymentMethodsService: PaymentMethodsService,
    protected paymentStepService: PaymentStepService,
    protected refundService: RefundService,
    protected captureService: CaptureService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payment }) => {
      if (payment.id === undefined) {
        const today = dayjs().startOf('day');
        payment.createDateTimeUtc = today;
      }

      this.updateForm(payment);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const payment = this.createFromForm();
    if (payment.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentService.update(payment));
    } else {
      this.subscribeToSaveResponse(this.paymentService.create(payment));
    }
  }

  trackErrorReportById(_index: number, item: IErrorReport): number {
    return item.id!;
  }

  trackAbuseReportById(_index: number, item: IAbuseReport): number {
    return item.id!;
  }

  trackPaymentAttributesById(_index: number, item: IPaymentAttributes): number {
    return item.id!;
  }

  trackPaymentMethodsById(_index: number, item: IPaymentMethods): number {
    return item.id!;
  }

  trackPaymentStepById(_index: number, item: IPaymentStep): number {
    return item.id!;
  }

  trackRefundById(_index: number, item: IRefund): number {
    return item.id!;
  }

  trackCaptureById(_index: number, item: ICapture): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayment>>): void {
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

  protected updateForm(payment: IPayment): void {
    this.editForm.patchValue({
      id: payment.id,
      reference: payment.reference,
      createDateTimeUtc: payment.createDateTimeUtc ? payment.createDateTimeUtc.format(DATE_TIME_FORMAT) : null,
      status: payment.status,
      amountToCollect: payment.amountToCollect,
      surchargeAmount: payment.surchargeAmount,
      convertedTotalAmount: payment.convertedTotalAmount,
      convertedCurrency: payment.convertedCurrency,
      conversionRate: payment.conversionRate,
      paidAmount: payment.paidAmount,
      lastErrorReport: payment.lastErrorReport,
      abuseReport: payment.abuseReport,
      attributes: payment.attributes,
      paymentMethods: payment.paymentMethods,
      steps: payment.steps,
      refunds: payment.refunds,
      captures: payment.captures,
    });

    this.lastErrorReportsCollection = this.errorReportService.addErrorReportToCollectionIfMissing(
      this.lastErrorReportsCollection,
      payment.lastErrorReport
    );
    this.abuseReportsCollection = this.abuseReportService.addAbuseReportToCollectionIfMissing(
      this.abuseReportsCollection,
      payment.abuseReport
    );
    this.attributesCollection = this.paymentAttributesService.addPaymentAttributesToCollectionIfMissing(
      this.attributesCollection,
      payment.attributes
    );
    this.paymentMethodsSharedCollection = this.paymentMethodsService.addPaymentMethodsToCollectionIfMissing(
      this.paymentMethodsSharedCollection,
      payment.paymentMethods
    );
    this.paymentStepsSharedCollection = this.paymentStepService.addPaymentStepToCollectionIfMissing(
      this.paymentStepsSharedCollection,
      payment.steps
    );
    this.refundsSharedCollection = this.refundService.addRefundToCollectionIfMissing(this.refundsSharedCollection, payment.refunds);
    this.capturesSharedCollection = this.captureService.addCaptureToCollectionIfMissing(this.capturesSharedCollection, payment.captures);
  }

  protected loadRelationshipsOptions(): void {
    this.errorReportService
      .query({ filter: 'payment-is-null' })
      .pipe(map((res: HttpResponse<IErrorReport[]>) => res.body ?? []))
      .pipe(
        map((errorReports: IErrorReport[]) =>
          this.errorReportService.addErrorReportToCollectionIfMissing(errorReports, this.editForm.get('lastErrorReport')!.value)
        )
      )
      .subscribe((errorReports: IErrorReport[]) => (this.lastErrorReportsCollection = errorReports));

    this.abuseReportService
      .query({ filter: 'payment-is-null' })
      .pipe(map((res: HttpResponse<IAbuseReport[]>) => res.body ?? []))
      .pipe(
        map((abuseReports: IAbuseReport[]) =>
          this.abuseReportService.addAbuseReportToCollectionIfMissing(abuseReports, this.editForm.get('abuseReport')!.value)
        )
      )
      .subscribe((abuseReports: IAbuseReport[]) => (this.abuseReportsCollection = abuseReports));

    this.paymentAttributesService
      .query({ filter: 'payment-is-null' })
      .pipe(map((res: HttpResponse<IPaymentAttributes[]>) => res.body ?? []))
      .pipe(
        map((paymentAttributes: IPaymentAttributes[]) =>
          this.paymentAttributesService.addPaymentAttributesToCollectionIfMissing(paymentAttributes, this.editForm.get('attributes')!.value)
        )
      )
      .subscribe((paymentAttributes: IPaymentAttributes[]) => (this.attributesCollection = paymentAttributes));

    this.paymentMethodsService
      .query()
      .pipe(map((res: HttpResponse<IPaymentMethods[]>) => res.body ?? []))
      .pipe(
        map((paymentMethods: IPaymentMethods[]) =>
          this.paymentMethodsService.addPaymentMethodsToCollectionIfMissing(paymentMethods, this.editForm.get('paymentMethods')!.value)
        )
      )
      .subscribe((paymentMethods: IPaymentMethods[]) => (this.paymentMethodsSharedCollection = paymentMethods));

    this.paymentStepService
      .query()
      .pipe(map((res: HttpResponse<IPaymentStep[]>) => res.body ?? []))
      .pipe(
        map((paymentSteps: IPaymentStep[]) =>
          this.paymentStepService.addPaymentStepToCollectionIfMissing(paymentSteps, this.editForm.get('steps')!.value)
        )
      )
      .subscribe((paymentSteps: IPaymentStep[]) => (this.paymentStepsSharedCollection = paymentSteps));

    this.refundService
      .query()
      .pipe(map((res: HttpResponse<IRefund[]>) => res.body ?? []))
      .pipe(map((refunds: IRefund[]) => this.refundService.addRefundToCollectionIfMissing(refunds, this.editForm.get('refunds')!.value)))
      .subscribe((refunds: IRefund[]) => (this.refundsSharedCollection = refunds));

    this.captureService
      .query()
      .pipe(map((res: HttpResponse<ICapture[]>) => res.body ?? []))
      .pipe(
        map((captures: ICapture[]) => this.captureService.addCaptureToCollectionIfMissing(captures, this.editForm.get('captures')!.value))
      )
      .subscribe((captures: ICapture[]) => (this.capturesSharedCollection = captures));
  }

  protected createFromForm(): IPayment {
    return {
      ...new Payment(),
      id: this.editForm.get(['id'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      createDateTimeUtc: this.editForm.get(['createDateTimeUtc'])!.value
        ? dayjs(this.editForm.get(['createDateTimeUtc'])!.value, DATE_TIME_FORMAT)
        : undefined,
      status: this.editForm.get(['status'])!.value,
      amountToCollect: this.editForm.get(['amountToCollect'])!.value,
      surchargeAmount: this.editForm.get(['surchargeAmount'])!.value,
      convertedTotalAmount: this.editForm.get(['convertedTotalAmount'])!.value,
      convertedCurrency: this.editForm.get(['convertedCurrency'])!.value,
      conversionRate: this.editForm.get(['conversionRate'])!.value,
      paidAmount: this.editForm.get(['paidAmount'])!.value,
      lastErrorReport: this.editForm.get(['lastErrorReport'])!.value,
      abuseReport: this.editForm.get(['abuseReport'])!.value,
      attributes: this.editForm.get(['attributes'])!.value,
      paymentMethods: this.editForm.get(['paymentMethods'])!.value,
      steps: this.editForm.get(['steps'])!.value,
      refunds: this.editForm.get(['refunds'])!.value,
      captures: this.editForm.get(['captures'])!.value,
    };
  }
}
