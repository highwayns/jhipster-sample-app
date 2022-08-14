import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISiteUsersAccess } from '../site-users-access.model';
import { SiteUsersAccessService } from '../service/site-users-access.service';

@Component({
  templateUrl: './site-users-access-delete-dialog.component.html',
})
export class SiteUsersAccessDeleteDialogComponent {
  siteUsersAccess?: ISiteUsersAccess;

  constructor(protected siteUsersAccessService: SiteUsersAccessService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.siteUsersAccessService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
