import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentJob } from '../payment-job.model';
import { PaymentJobService } from '../service/payment-job.service';

@Component({
  templateUrl: './payment-job-delete-dialog.component.html',
})
export class PaymentJobDeleteDialogComponent {
  paymentJob?: IPaymentJob;

  constructor(protected paymentJobService: PaymentJobService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentJobService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
