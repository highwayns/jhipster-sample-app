import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentJob } from '../payment-job.model';
import { PaymentJobService } from '../service/payment-job.service';
import { PaymentJobDeleteDialogComponent } from '../delete/payment-job-delete-dialog.component';

@Component({
  selector: 'jhi-payment-job',
  templateUrl: './payment-job.component.html',
})
export class PaymentJobComponent implements OnInit {
  paymentJobs?: IPaymentJob[];
  isLoading = false;

  constructor(protected paymentJobService: PaymentJobService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.paymentJobService.query().subscribe({
      next: (res: HttpResponse<IPaymentJob[]>) => {
        this.isLoading = false;
        this.paymentJobs = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPaymentJob): number {
    return item.id!;
  }

  delete(paymentJob: IPaymentJob): void {
    const modalRef = this.modalService.open(PaymentJobDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paymentJob = paymentJob;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
