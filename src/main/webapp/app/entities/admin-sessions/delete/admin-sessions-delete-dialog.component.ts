import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdminSessions } from '../admin-sessions.model';
import { AdminSessionsService } from '../service/admin-sessions.service';

@Component({
  templateUrl: './admin-sessions-delete-dialog.component.html',
})
export class AdminSessionsDeleteDialogComponent {
  adminSessions?: IAdminSessions;

  constructor(protected adminSessionsService: AdminSessionsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.adminSessionsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
