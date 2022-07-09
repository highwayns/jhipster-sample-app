import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRefundStep } from '../refund-step.model';

@Component({
  selector: 'jhi-refund-step-detail',
  templateUrl: './refund-step-detail.component.html',
})
export class RefundStepDetailComponent implements OnInit {
  refundStep: IRefundStep | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ refundStep }) => {
      this.refundStep = refundStep;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
