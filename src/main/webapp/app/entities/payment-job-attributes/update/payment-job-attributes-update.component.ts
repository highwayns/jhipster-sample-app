import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPaymentJobAttributes, PaymentJobAttributes } from '../payment-job-attributes.model';
import { PaymentJobAttributesService } from '../service/payment-job-attributes.service';

@Component({
  selector: 'jhi-payment-job-attributes-update',
  templateUrl: './payment-job-attributes-update.component.html',
})
export class PaymentJobAttributesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    webhookUrl: [],
    googleAnalyticsClientId: [],
    allowedParentFrameDomains: [],
    paymentPageReference: [],
  });

  constructor(
    protected paymentJobAttributesService: PaymentJobAttributesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentJobAttributes }) => {
      this.updateForm(paymentJobAttributes);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentJobAttributes = this.createFromForm();
    if (paymentJobAttributes.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentJobAttributesService.update(paymentJobAttributes));
    } else {
      this.subscribeToSaveResponse(this.paymentJobAttributesService.create(paymentJobAttributes));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentJobAttributes>>): void {
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

  protected updateForm(paymentJobAttributes: IPaymentJobAttributes): void {
    this.editForm.patchValue({
      id: paymentJobAttributes.id,
      webhookUrl: paymentJobAttributes.webhookUrl,
      googleAnalyticsClientId: paymentJobAttributes.googleAnalyticsClientId,
      allowedParentFrameDomains: paymentJobAttributes.allowedParentFrameDomains,
      paymentPageReference: paymentJobAttributes.paymentPageReference,
    });
  }

  protected createFromForm(): IPaymentJobAttributes {
    return {
      ...new PaymentJobAttributes(),
      id: this.editForm.get(['id'])!.value,
      webhookUrl: this.editForm.get(['webhookUrl'])!.value,
      googleAnalyticsClientId: this.editForm.get(['googleAnalyticsClientId'])!.value,
      allowedParentFrameDomains: this.editForm.get(['allowedParentFrameDomains'])!.value,
      paymentPageReference: this.editForm.get(['paymentPageReference'])!.value,
    };
  }
}
