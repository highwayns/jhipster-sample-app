import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPaymentStep, PaymentStep } from '../payment-step.model';
import { PaymentStepService } from '../service/payment-step.service';
import { IPaymentMethods } from 'app/entities/payment-methods/payment-methods.model';
import { PaymentMethodsService } from 'app/entities/payment-methods/service/payment-methods.service';
import { PaymentStepAction } from 'app/entities/enumerations/payment-step-action.model';
import { PaymentStatus } from 'app/entities/enumerations/payment-status.model';

@Component({
  selector: 'jhi-payment-step-update',
  templateUrl: './payment-step-update.component.html',
})
export class PaymentStepUpdateComponent implements OnInit {
  isSaving = false;
  paymentStepActionValues = Object.keys(PaymentStepAction);
  paymentStatusValues = Object.keys(PaymentStatus);

  paymentMethodsSharedCollection: IPaymentMethods[] = [];

  editForm = this.fb.group({
    id: [],
    reference: [],
    createDateTimeUtc: [],
    action: [],
    status: [],
    amountToCollect: [],
    paymentMethods: [],
  });

  constructor(
    protected paymentStepService: PaymentStepService,
    protected paymentMethodsService: PaymentMethodsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentStep }) => {
      if (paymentStep.id === undefined) {
        const today = dayjs().startOf('day');
        paymentStep.createDateTimeUtc = today;
      }

      this.updateForm(paymentStep);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentStep = this.createFromForm();
    if (paymentStep.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentStepService.update(paymentStep));
    } else {
      this.subscribeToSaveResponse(this.paymentStepService.create(paymentStep));
    }
  }

  trackPaymentMethodsById(_index: number, item: IPaymentMethods): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentStep>>): void {
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

  protected updateForm(paymentStep: IPaymentStep): void {
    this.editForm.patchValue({
      id: paymentStep.id,
      reference: paymentStep.reference,
      createDateTimeUtc: paymentStep.createDateTimeUtc ? paymentStep.createDateTimeUtc.format(DATE_TIME_FORMAT) : null,
      action: paymentStep.action,
      status: paymentStep.status,
      amountToCollect: paymentStep.amountToCollect,
      paymentMethods: paymentStep.paymentMethods,
    });

    this.paymentMethodsSharedCollection = this.paymentMethodsService.addPaymentMethodsToCollectionIfMissing(
      this.paymentMethodsSharedCollection,
      paymentStep.paymentMethods
    );
  }

  protected loadRelationshipsOptions(): void {
    this.paymentMethodsService
      .query()
      .pipe(map((res: HttpResponse<IPaymentMethods[]>) => res.body ?? []))
      .pipe(
        map((paymentMethods: IPaymentMethods[]) =>
          this.paymentMethodsService.addPaymentMethodsToCollectionIfMissing(paymentMethods, this.editForm.get('paymentMethods')!.value)
        )
      )
      .subscribe((paymentMethods: IPaymentMethods[]) => (this.paymentMethodsSharedCollection = paymentMethods));
  }

  protected createFromForm(): IPaymentStep {
    return {
      ...new PaymentStep(),
      id: this.editForm.get(['id'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      createDateTimeUtc: this.editForm.get(['createDateTimeUtc'])!.value
        ? dayjs(this.editForm.get(['createDateTimeUtc'])!.value, DATE_TIME_FORMAT)
        : undefined,
      action: this.editForm.get(['action'])!.value,
      status: this.editForm.get(['status'])!.value,
      amountToCollect: this.editForm.get(['amountToCollect'])!.value,
      paymentMethods: this.editForm.get(['paymentMethods'])!.value,
    };
  }
}
