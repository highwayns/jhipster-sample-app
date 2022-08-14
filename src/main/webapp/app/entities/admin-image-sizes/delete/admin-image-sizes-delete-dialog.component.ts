import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdminImageSizes } from '../admin-image-sizes.model';
import { AdminImageSizesService } from '../service/admin-image-sizes.service';

@Component({
  templateUrl: './admin-image-sizes-delete-dialog.component.html',
})
export class AdminImageSizesDeleteDialogComponent {
  adminImageSizes?: IAdminImageSizes;

  constructor(protected adminImageSizesService: AdminImageSizesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.adminImageSizesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
