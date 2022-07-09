import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentMethodInfo } from '../payment-method-info.model';
import { PaymentMethodInfoService } from '../service/payment-method-info.service';

@Component({
  templateUrl: './payment-method-info-delete-dialog.component.html',
})
export class PaymentMethodInfoDeleteDialogComponent {
  paymentMethodInfo?: IPaymentMethodInfo;

  constructor(protected paymentMethodInfoService: PaymentMethodInfoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentMethodInfoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
