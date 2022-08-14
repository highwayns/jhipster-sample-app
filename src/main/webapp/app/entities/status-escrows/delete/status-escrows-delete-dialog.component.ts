import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IStatusEscrows } from '../status-escrows.model';
import { StatusEscrowsService } from '../service/status-escrows.service';

@Component({
  templateUrl: './status-escrows-delete-dialog.component.html',
})
export class StatusEscrowsDeleteDialogComponent {
  statusEscrows?: IStatusEscrows;

  constructor(protected statusEscrowsService: StatusEscrowsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.statusEscrowsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
