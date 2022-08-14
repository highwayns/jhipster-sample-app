import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdminTabs } from '../admin-tabs.model';
import { AdminTabsService } from '../service/admin-tabs.service';

@Component({
  templateUrl: './admin-tabs-delete-dialog.component.html',
})
export class AdminTabsDeleteDialogComponent {
  adminTabs?: IAdminTabs;

  constructor(protected adminTabsService: AdminTabsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.adminTabsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
