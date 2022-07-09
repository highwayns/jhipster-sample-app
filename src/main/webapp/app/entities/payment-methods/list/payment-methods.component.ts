import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentMethods } from '../payment-methods.model';
import { PaymentMethodsService } from '../service/payment-methods.service';
import { PaymentMethodsDeleteDialogComponent } from '../delete/payment-methods-delete-dialog.component';

@Component({
  selector: 'jhi-payment-methods',
  templateUrl: './payment-methods.component.html',
})
export class PaymentMethodsComponent implements OnInit {
  paymentMethods?: IPaymentMethods[];
  isLoading = false;

  constructor(protected paymentMethodsService: PaymentMethodsService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.paymentMethodsService.query().subscribe({
      next: (res: HttpResponse<IPaymentMethods[]>) => {
        this.isLoading = false;
        this.paymentMethods = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPaymentMethods): number {
    return item.id!;
  }

  delete(paymentMethods: IPaymentMethods): void {
    const modalRef = this.modalService.open(PaymentMethodsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paymentMethods = paymentMethods;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
