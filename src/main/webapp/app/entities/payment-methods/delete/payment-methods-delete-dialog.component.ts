import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentMethods } from '../payment-methods.model';
import { PaymentMethodsService } from '../service/payment-methods.service';

@Component({
  templateUrl: './payment-methods-delete-dialog.component.html',
})
export class PaymentMethodsDeleteDialogComponent {
  paymentMethods?: IPaymentMethods;

  constructor(protected paymentMethodsService: PaymentMethodsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentMethodsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
