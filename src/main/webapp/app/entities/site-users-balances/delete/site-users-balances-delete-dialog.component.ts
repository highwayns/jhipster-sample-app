import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISiteUsersBalances } from '../site-users-balances.model';
import { SiteUsersBalancesService } from '../service/site-users-balances.service';

@Component({
  templateUrl: './site-users-balances-delete-dialog.component.html',
})
export class SiteUsersBalancesDeleteDialogComponent {
  siteUsersBalances?: ISiteUsersBalances;

  constructor(protected siteUsersBalancesService: SiteUsersBalancesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.siteUsersBalancesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
