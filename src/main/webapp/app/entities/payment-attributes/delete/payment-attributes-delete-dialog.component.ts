import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentAttributes } from '../payment-attributes.model';
import { PaymentAttributesService } from '../service/payment-attributes.service';

@Component({
  templateUrl: './payment-attributes-delete-dialog.component.html',
})
export class PaymentAttributesDeleteDialogComponent {
  paymentAttributes?: IPaymentAttributes;

  constructor(protected paymentAttributesService: PaymentAttributesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentAttributesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
