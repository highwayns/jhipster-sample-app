import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentJobAttributes } from '../payment-job-attributes.model';
import { PaymentJobAttributesService } from '../service/payment-job-attributes.service';
import { PaymentJobAttributesDeleteDialogComponent } from '../delete/payment-job-attributes-delete-dialog.component';

@Component({
  selector: 'jhi-payment-job-attributes',
  templateUrl: './payment-job-attributes.component.html',
})
export class PaymentJobAttributesComponent implements OnInit {
  paymentJobAttributes?: IPaymentJobAttributes[];
  isLoading = false;

  constructor(protected paymentJobAttributesService: PaymentJobAttributesService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.paymentJobAttributesService.query().subscribe({
      next: (res: HttpResponse<IPaymentJobAttributes[]>) => {
        this.isLoading = false;
        this.paymentJobAttributes = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPaymentJobAttributes): number {
    return item.id!;
  }

  delete(paymentJobAttributes: IPaymentJobAttributes): void {
    const modalRef = this.modalService.open(PaymentJobAttributesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paymentJobAttributes = paymentJobAttributes;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
