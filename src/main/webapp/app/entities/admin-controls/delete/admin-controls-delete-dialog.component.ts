import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdminControls } from '../admin-controls.model';
import { AdminControlsService } from '../service/admin-controls.service';

@Component({
  templateUrl: './admin-controls-delete-dialog.component.html',
})
export class AdminControlsDeleteDialogComponent {
  adminControls?: IAdminControls;

  constructor(protected adminControlsService: AdminControlsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.adminControlsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
