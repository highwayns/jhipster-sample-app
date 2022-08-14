import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdminGroupsPages } from '../admin-groups-pages.model';
import { AdminGroupsPagesService } from '../service/admin-groups-pages.service';

@Component({
  templateUrl: './admin-groups-pages-delete-dialog.component.html',
})
export class AdminGroupsPagesDeleteDialogComponent {
  adminGroupsPages?: IAdminGroupsPages;

  constructor(protected adminGroupsPagesService: AdminGroupsPagesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.adminGroupsPagesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
