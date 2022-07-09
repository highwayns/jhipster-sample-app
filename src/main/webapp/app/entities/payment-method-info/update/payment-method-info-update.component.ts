import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPaymentMethodInfo, PaymentMethodInfo } from '../payment-method-info.model';
import { PaymentMethodInfoService } from '../service/payment-method-info.service';
import { Currency } from 'app/entities/enumerations/currency.model';

@Component({
  selector: 'jhi-payment-method-info-update',
  templateUrl: './payment-method-info-update.component.html',
})
export class PaymentMethodInfoUpdateComponent implements OnInit {
  isSaving = false;
  currencyValues = Object.keys(Currency);

  editForm = this.fb.group({
    id: [],
    paymentMethod: [],
    logo: [],
    supportsTokenisation: [],
    currencies: [],
    surchargeAmount: [],
    surchargeAmountExclVat: [],
    surchargeAmountVat: [],
    surchargeVatPercentage: [],
    description: [],
  });

  constructor(
    protected paymentMethodInfoService: PaymentMethodInfoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentMethodInfo }) => {
      this.updateForm(paymentMethodInfo);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentMethodInfo = this.createFromForm();
    if (paymentMethodInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentMethodInfoService.update(paymentMethodInfo));
    } else {
      this.subscribeToSaveResponse(this.paymentMethodInfoService.create(paymentMethodInfo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentMethodInfo>>): void {
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

  protected updateForm(paymentMethodInfo: IPaymentMethodInfo): void {
    this.editForm.patchValue({
      id: paymentMethodInfo.id,
      paymentMethod: paymentMethodInfo.paymentMethod,
      logo: paymentMethodInfo.logo,
      supportsTokenisation: paymentMethodInfo.supportsTokenisation,
      currencies: paymentMethodInfo.currencies,
      surchargeAmount: paymentMethodInfo.surchargeAmount,
      surchargeAmountExclVat: paymentMethodInfo.surchargeAmountExclVat,
      surchargeAmountVat: paymentMethodInfo.surchargeAmountVat,
      surchargeVatPercentage: paymentMethodInfo.surchargeVatPercentage,
      description: paymentMethodInfo.description,
    });
  }

  protected createFromForm(): IPaymentMethodInfo {
    return {
      ...new PaymentMethodInfo(),
      id: this.editForm.get(['id'])!.value,
      paymentMethod: this.editForm.get(['paymentMethod'])!.value,
      logo: this.editForm.get(['logo'])!.value,
      supportsTokenisation: this.editForm.get(['supportsTokenisation'])!.value,
      currencies: this.editForm.get(['currencies'])!.value,
      surchargeAmount: this.editForm.get(['surchargeAmount'])!.value,
      surchargeAmountExclVat: this.editForm.get(['surchargeAmountExclVat'])!.value,
      surchargeAmountVat: this.editForm.get(['surchargeAmountVat'])!.value,
      surchargeVatPercentage: this.editForm.get(['surchargeVatPercentage'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }
}
