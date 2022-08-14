import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdminGroups } from '../admin-groups.model';
import { AdminGroupsService } from '../service/admin-groups.service';

@Component({
  templateUrl: './admin-groups-delete-dialog.component.html',
})
export class AdminGroupsDeleteDialogComponent {
  adminGroups?: IAdminGroups;

  constructor(protected adminGroupsService: AdminGroupsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.adminGroupsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
