import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentMethodInfo } from '../payment-method-info.model';

@Component({
  selector: 'jhi-payment-method-info-detail',
  templateUrl: './payment-method-info-detail.component.html',
})
export class PaymentMethodInfoDetailComponent implements OnInit {
  paymentMethodInfo: IPaymentMethodInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentMethodInfo }) => {
      this.paymentMethodInfo = paymentMethodInfo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
