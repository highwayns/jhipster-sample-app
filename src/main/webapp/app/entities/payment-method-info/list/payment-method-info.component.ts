import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentMethodInfo } from '../payment-method-info.model';
import { PaymentMethodInfoService } from '../service/payment-method-info.service';
import { PaymentMethodInfoDeleteDialogComponent } from '../delete/payment-method-info-delete-dialog.component';

@Component({
  selector: 'jhi-payment-method-info',
  templateUrl: './payment-method-info.component.html',
})
export class PaymentMethodInfoComponent implements OnInit {
  paymentMethodInfos?: IPaymentMethodInfo[];
  isLoading = false;

  constructor(protected paymentMethodInfoService: PaymentMethodInfoService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.paymentMethodInfoService.query().subscribe({
      next: (res: HttpResponse<IPaymentMethodInfo[]>) => {
        this.isLoading = false;
        this.paymentMethodInfos = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPaymentMethodInfo): number {
    return item.id!;
  }

  delete(paymentMethodInfo: IPaymentMethodInfo): void {
    const modalRef = this.modalService.open(PaymentMethodInfoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paymentMethodInfo = paymentMethodInfo;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
