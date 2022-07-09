import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPaymentAttributes, PaymentAttributes } from '../payment-attributes.model';
import { PaymentAttributesService } from '../service/payment-attributes.service';
import { PaymentStatus } from 'app/entities/enumerations/payment-status.model';

@Component({
  selector: 'jhi-payment-attributes-update',
  templateUrl: './payment-attributes-update.component.html',
})
export class PaymentAttributesUpdateComponent implements OnInit {
  isSaving = false;
  paymentStatusValues = Object.keys(PaymentStatus);

  editForm = this.fb.group({
    id: [],
    originatingIpAddress: [],
    originHeader: [],
    userAgent: [],
    returnUrlSuccess: [],
    returnUrlFailed: [],
    returnUrlCancelled: [],
    simulatedStatus: [],
    idealBic: [],
    paymentMethodTransactionId: [],
    paymentMethodVoidTransactionId: [],
    token: [],
    cashFlowsAcquiringDetails: [],
    descriptor: [],
    ewalletType: [],
    paymentStatus: [],
  });

  constructor(
    protected paymentAttributesService: PaymentAttributesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentAttributes }) => {
      this.updateForm(paymentAttributes);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentAttributes = this.createFromForm();
    if (paymentAttributes.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentAttributesService.update(paymentAttributes));
    } else {
      this.subscribeToSaveResponse(this.paymentAttributesService.create(paymentAttributes));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentAttributes>>): void {
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

  protected updateForm(paymentAttributes: IPaymentAttributes): void {
    this.editForm.patchValue({
      id: paymentAttributes.id,
      originatingIpAddress: paymentAttributes.originatingIpAddress,
      originHeader: paymentAttributes.originHeader,
      userAgent: paymentAttributes.userAgent,
      returnUrlSuccess: paymentAttributes.returnUrlSuccess,
      returnUrlFailed: paymentAttributes.returnUrlFailed,
      returnUrlCancelled: paymentAttributes.returnUrlCancelled,
      simulatedStatus: paymentAttributes.simulatedStatus,
      idealBic: paymentAttributes.idealBic,
      paymentMethodTransactionId: paymentAttributes.paymentMethodTransactionId,
      paymentMethodVoidTransactionId: paymentAttributes.paymentMethodVoidTransactionId,
      token: paymentAttributes.token,
      cashFlowsAcquiringDetails: paymentAttributes.cashFlowsAcquiringDetails,
      descriptor: paymentAttributes.descriptor,
      ewalletType: paymentAttributes.ewalletType,
      paymentStatus: paymentAttributes.paymentStatus,
    });
  }

  protected createFromForm(): IPaymentAttributes {
    return {
      ...new PaymentAttributes(),
      id: this.editForm.get(['id'])!.value,
      originatingIpAddress: this.editForm.get(['originatingIpAddress'])!.value,
      originHeader: this.editForm.get(['originHeader'])!.value,
      userAgent: this.editForm.get(['userAgent'])!.value,
      returnUrlSuccess: this.editForm.get(['returnUrlSuccess'])!.value,
      returnUrlFailed: this.editForm.get(['returnUrlFailed'])!.value,
      returnUrlCancelled: this.editForm.get(['returnUrlCancelled'])!.value,
      simulatedStatus: this.editForm.get(['simulatedStatus'])!.value,
      idealBic: this.editForm.get(['idealBic'])!.value,
      paymentMethodTransactionId: this.editForm.get(['paymentMethodTransactionId'])!.value,
      paymentMethodVoidTransactionId: this.editForm.get(['paymentMethodVoidTransactionId'])!.value,
      token: this.editForm.get(['token'])!.value,
      cashFlowsAcquiringDetails: this.editForm.get(['cashFlowsAcquiringDetails'])!.value,
      descriptor: this.editForm.get(['descriptor'])!.value,
      ewalletType: this.editForm.get(['ewalletType'])!.value,
      paymentStatus: this.editForm.get(['paymentStatus'])!.value,
    };
  }
}
