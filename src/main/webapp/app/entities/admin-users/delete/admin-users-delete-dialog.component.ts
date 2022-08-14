import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdminUsers } from '../admin-users.model';
import { AdminUsersService } from '../service/admin-users.service';

@Component({
  templateUrl: './admin-users-delete-dialog.component.html',
})
export class AdminUsersDeleteDialogComponent {
  adminUsers?: IAdminUsers;

  constructor(protected adminUsersService: AdminUsersService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.adminUsersService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
