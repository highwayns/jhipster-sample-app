import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentJobAttributes } from '../payment-job-attributes.model';
import { PaymentJobAttributesService } from '../service/payment-job-attributes.service';

@Component({
  templateUrl: './payment-job-attributes-delete-dialog.component.html',
})
export class PaymentJobAttributesDeleteDialogComponent {
  paymentJobAttributes?: IPaymentJobAttributes;

  constructor(protected paymentJobAttributesService: PaymentJobAttributesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentJobAttributesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
