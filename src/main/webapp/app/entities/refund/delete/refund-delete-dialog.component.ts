import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRefund } from '../refund.model';
import { RefundService } from '../service/refund.service';

@Component({
  templateUrl: './refund-delete-dialog.component.html',
})
export class RefundDeleteDialogComponent {
  refund?: IRefund;

  constructor(protected refundService: RefundService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.refundService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
