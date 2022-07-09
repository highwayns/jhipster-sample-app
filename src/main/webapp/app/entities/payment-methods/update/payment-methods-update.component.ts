import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPaymentMethods, PaymentMethods } from '../payment-methods.model';
import { PaymentMethodsService } from '../service/payment-methods.service';
import { PaymentMethod } from 'app/entities/enumerations/payment-method.model';

@Component({
  selector: 'jhi-payment-methods-update',
  templateUrl: './payment-methods-update.component.html',
})
export class PaymentMethodsUpdateComponent implements OnInit {
  isSaving = false;
  paymentMethodValues = Object.keys(PaymentMethod);

  editForm = this.fb.group({
    id: [],
    paymentMethod: [],
  });

  constructor(
    protected paymentMethodsService: PaymentMethodsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentMethods }) => {
      this.updateForm(paymentMethods);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentMethods = this.createFromForm();
    if (paymentMethods.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentMethodsService.update(paymentMethods));
    } else {
      this.subscribeToSaveResponse(this.paymentMethodsService.create(paymentMethods));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentMethods>>): void {
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

  protected updateForm(paymentMethods: IPaymentMethods): void {
    this.editForm.patchValue({
      id: paymentMethods.id,
      paymentMethod: paymentMethods.paymentMethod,
    });
  }

  protected createFromForm(): IPaymentMethods {
    return {
      ...new PaymentMethods(),
      id: this.editForm.get(['id'])!.value,
      paymentMethod: this.editForm.get(['paymentMethod'])!.value,
    };
  }
}
