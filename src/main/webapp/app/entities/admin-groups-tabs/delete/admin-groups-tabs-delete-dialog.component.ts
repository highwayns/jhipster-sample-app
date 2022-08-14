import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdminGroupsTabs } from '../admin-groups-tabs.model';
import { AdminGroupsTabsService } from '../service/admin-groups-tabs.service';

@Component({
  templateUrl: './admin-groups-tabs-delete-dialog.component.html',
})
export class AdminGroupsTabsDeleteDialogComponent {
  adminGroupsTabs?: IAdminGroupsTabs;

  constructor(protected adminGroupsTabsService: AdminGroupsTabsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.adminGroupsTabsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
