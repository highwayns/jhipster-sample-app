import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrderTypes } from '../order-types.model';
import { OrderTypesService } from '../service/order-types.service';

@Component({
  templateUrl: './order-types-delete-dialog.component.html',
})
export class OrderTypesDeleteDialogComponent {
  orderTypes?: IOrderTypes;

  constructor(protected orderTypesService: OrderTypesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderTypesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
