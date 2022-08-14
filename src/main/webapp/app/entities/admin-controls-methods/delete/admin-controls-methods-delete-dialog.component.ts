import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdminControlsMethods } from '../admin-controls-methods.model';
import { AdminControlsMethodsService } from '../service/admin-controls-methods.service';

@Component({
  templateUrl: './admin-controls-methods-delete-dialog.component.html',
})
export class AdminControlsMethodsDeleteDialogComponent {
  adminControlsMethods?: IAdminControlsMethods;

  constructor(protected adminControlsMethodsService: AdminControlsMethodsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.adminControlsMethodsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
