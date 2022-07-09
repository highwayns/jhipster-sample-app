import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentStep } from '../payment-step.model';

@Component({
  selector: 'jhi-payment-step-detail',
  templateUrl: './payment-step-detail.component.html',
})
export class PaymentStepDetailComponent implements OnInit {
  paymentStep: IPaymentStep | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentStep }) => {
      this.paymentStep = paymentStep;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
