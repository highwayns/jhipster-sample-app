import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IRefund, Refund } from '../refund.model';
import { RefundService } from '../service/refund.service';
import { IRefundStep } from 'app/entities/refund-step/refund-step.model';
import { RefundStepService } from 'app/entities/refund-step/service/refund-step.service';
import { RefundStatus } from 'app/entities/enumerations/refund-status.model';
import { Currency } from 'app/entities/enumerations/currency.model';

@Component({
  selector: 'jhi-refund-update',
  templateUrl: './refund-update.component.html',
})
export class RefundUpdateComponent implements OnInit {
  isSaving = false;
  refundStatusValues = Object.keys(RefundStatus);
  currencyValues = Object.keys(Currency);

  refundStepsSharedCollection: IRefundStep[] = [];

  editForm = this.fb.group({
    id: [],
    reference: [],
    createDateTimeUtc: [],
    refundNumber: [],
    status: [],
    amountToRefund: [],
    convertedAmountToRefund: [],
    convertedCurrency: [],
    conversionRate: [],
    steps: [],
  });

  constructor(
    protected refundService: RefundService,
    protected refundStepService: RefundStepService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ refund }) => {
      if (refund.id === undefined) {
        const today = dayjs().startOf('day');
        refund.createDateTimeUtc = today;
      }

      this.updateForm(refund);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const refund = this.createFromForm();
    if (refund.id !== undefined) {
      this.subscribeToSaveResponse(this.refundService.update(refund));
    } else {
      this.subscribeToSaveResponse(this.refundService.create(refund));
    }
  }

  trackRefundStepById(_index: number, item: IRefundStep): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRefund>>): void {
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

  protected updateForm(refund: IRefund): void {
    this.editForm.patchValue({
      id: refund.id,
      reference: refund.reference,
      createDateTimeUtc: refund.createDateTimeUtc ? refund.createDateTimeUtc.format(DATE_TIME_FORMAT) : null,
      refundNumber: refund.refundNumber,
      status: refund.status,
      amountToRefund: refund.amountToRefund,
      convertedAmountToRefund: refund.convertedAmountToRefund,
      convertedCurrency: refund.convertedCurrency,
      conversionRate: refund.conversionRate,
      steps: refund.steps,
    });

    this.refundStepsSharedCollection = this.refundStepService.addRefundStepToCollectionIfMissing(
      this.refundStepsSharedCollection,
      refund.steps
    );
  }

  protected loadRelationshipsOptions(): void {
    this.refundStepService
      .query()
      .pipe(map((res: HttpResponse<IRefundStep[]>) => res.body ?? []))
      .pipe(
        map((refundSteps: IRefundStep[]) =>
          this.refundStepService.addRefundStepToCollectionIfMissing(refundSteps, this.editForm.get('steps')!.value)
        )
      )
      .subscribe((refundSteps: IRefundStep[]) => (this.refundStepsSharedCollection = refundSteps));
  }

  protected createFromForm(): IRefund {
    return {
      ...new Refund(),
      id: this.editForm.get(['id'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      createDateTimeUtc: this.editForm.get(['createDateTimeUtc'])!.value
        ? dayjs(this.editForm.get(['createDateTimeUtc'])!.value, DATE_TIME_FORMAT)
        : undefined,
      refundNumber: this.editForm.get(['refundNumber'])!.value,
      status: this.editForm.get(['status'])!.value,
      amountToRefund: this.editForm.get(['amountToRefund'])!.value,
      convertedAmountToRefund: this.editForm.get(['convertedAmountToRefund'])!.value,
      convertedCurrency: this.editForm.get(['convertedCurrency'])!.value,
      conversionRate: this.editForm.get(['conversionRate'])!.value,
      steps: this.editForm.get(['steps'])!.value,
    };
  }
}
