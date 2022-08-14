import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdminOrder } from '../admin-order.model';
import { AdminOrderService } from '../service/admin-order.service';

@Component({
  templateUrl: './admin-order-delete-dialog.component.html',
})
export class AdminOrderDeleteDialogComponent {
  adminOrder?: IAdminOrder;

  constructor(protected adminOrderService: AdminOrderService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.adminOrderService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
