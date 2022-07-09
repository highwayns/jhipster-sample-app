import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentAttributes } from '../payment-attributes.model';

@Component({
  selector: 'jhi-payment-attributes-detail',
  templateUrl: './payment-attributes-detail.component.html',
})
export class PaymentAttributesDetailComponent implements OnInit {
  paymentAttributes: IPaymentAttributes | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentAttributes }) => {
      this.paymentAttributes = paymentAttributes;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
