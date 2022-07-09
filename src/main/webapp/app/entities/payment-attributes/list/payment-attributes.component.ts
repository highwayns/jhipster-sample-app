import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentAttributes } from '../payment-attributes.model';
import { PaymentAttributesService } from '../service/payment-attributes.service';
import { PaymentAttributesDeleteDialogComponent } from '../delete/payment-attributes-delete-dialog.component';

@Component({
  selector: 'jhi-payment-attributes',
  templateUrl: './payment-attributes.component.html',
})
export class PaymentAttributesComponent implements OnInit {
  paymentAttributes?: IPaymentAttributes[];
  isLoading = false;

  constructor(protected paymentAttributesService: PaymentAttributesService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.paymentAttributesService.query().subscribe({
      next: (res: HttpResponse<IPaymentAttributes[]>) => {
        this.isLoading = false;
        this.paymentAttributes = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPaymentAttributes): number {
    return item.id!;
  }

  delete(paymentAttributes: IPaymentAttributes): void {
    const modalRef = this.modalService.open(PaymentAttributesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paymentAttributes = paymentAttributes;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
