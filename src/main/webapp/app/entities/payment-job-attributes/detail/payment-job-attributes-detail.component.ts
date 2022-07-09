import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentJobAttributes } from '../payment-job-attributes.model';

@Component({
  selector: 'jhi-payment-job-attributes-detail',
  templateUrl: './payment-job-attributes-detail.component.html',
})
export class PaymentJobAttributesDetailComponent implements OnInit {
  paymentJobAttributes: IPaymentJobAttributes | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentJobAttributes }) => {
      this.paymentJobAttributes = paymentJobAttributes;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
