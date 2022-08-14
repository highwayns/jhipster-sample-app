import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdminPages } from '../admin-pages.model';
import { AdminPagesService } from '../service/admin-pages.service';

@Component({
  templateUrl: './admin-pages-delete-dialog.component.html',
})
export class AdminPagesDeleteDialogComponent {
  adminPages?: IAdminPages;

  constructor(protected adminPagesService: AdminPagesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.adminPagesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
