import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRefundStep } from '../refund-step.model';
import { RefundStepService } from '../service/refund-step.service';

@Component({
  templateUrl: './refund-step-delete-dialog.component.html',
})
export class RefundStepDeleteDialogComponent {
  refundStep?: IRefundStep;

  constructor(protected refundStepService: RefundStepService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.refundStepService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
