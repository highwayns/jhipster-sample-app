import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentStep } from '../payment-step.model';
import { PaymentStepService } from '../service/payment-step.service';
import { PaymentStepDeleteDialogComponent } from '../delete/payment-step-delete-dialog.component';

@Component({
  selector: 'jhi-payment-step',
  templateUrl: './payment-step.component.html',
})
export class PaymentStepComponent implements OnInit {
  paymentSteps?: IPaymentStep[];
  isLoading = false;

  constructor(protected paymentStepService: PaymentStepService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.paymentStepService.query().subscribe({
      next: (res: HttpResponse<IPaymentStep[]>) => {
        this.isLoading = false;
        this.paymentSteps = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPaymentStep): number {
    return item.id!;
  }

  delete(paymentStep: IPaymentStep): void {
    const modalRef = this.modalService.open(PaymentStepDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paymentStep = paymentStep;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
