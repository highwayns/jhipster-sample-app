import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentJob } from '../payment-job.model';

@Component({
  selector: 'jhi-payment-job-detail',
  templateUrl: './payment-job-detail.component.html',
})
export class PaymentJobDetailComponent implements OnInit {
  paymentJob: IPaymentJob | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentJob }) => {
      this.paymentJob = paymentJob;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
