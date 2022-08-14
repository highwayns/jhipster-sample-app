import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrderLog } from '../order-log.model';
import { OrderLogService } from '../service/order-log.service';

@Component({
  templateUrl: './order-log-delete-dialog.component.html',
})
export class OrderLogDeleteDialogComponent {
  orderLog?: IOrderLog;

  constructor(protected orderLogService: OrderLogService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderLogService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
