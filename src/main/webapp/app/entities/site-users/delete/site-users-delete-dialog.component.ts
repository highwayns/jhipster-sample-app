import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISiteUsers } from '../site-users.model';
import { SiteUsersService } from '../service/site-users.service';

@Component({
  templateUrl: './site-users-delete-dialog.component.html',
})
export class SiteUsersDeleteDialogComponent {
  siteUsers?: ISiteUsers;

  constructor(protected siteUsersService: SiteUsersService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.siteUsersService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
