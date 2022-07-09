import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentStep } from '../payment-step.model';
import { PaymentStepService } from '../service/payment-step.service';

@Component({
  templateUrl: './payment-step-delete-dialog.component.html',
})
export class PaymentStepDeleteDialogComponent {
  paymentStep?: IPaymentStep;

  constructor(protected paymentStepService: PaymentStepService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentStepService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
