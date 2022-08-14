import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdminCron } from '../admin-cron.model';
import { AdminCronService } from '../service/admin-cron.service';

@Component({
  templateUrl: './admin-cron-delete-dialog.component.html',
})
export class AdminCronDeleteDialogComponent {
  adminCron?: IAdminCron;

  constructor(protected adminCronService: AdminCronService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.adminCronService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
