import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISiteUsersCatch } from '../site-users-catch.model';
import { SiteUsersCatchService } from '../service/site-users-catch.service';

@Component({
  templateUrl: './site-users-catch-delete-dialog.component.html',
})
export class SiteUsersCatchDeleteDialogComponent {
  siteUsersCatch?: ISiteUsersCatch;

  constructor(protected siteUsersCatchService: SiteUsersCatchService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.siteUsersCatchService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
