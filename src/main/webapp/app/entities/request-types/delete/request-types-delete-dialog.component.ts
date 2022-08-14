import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRequestTypes } from '../request-types.model';
import { RequestTypesService } from '../service/request-types.service';

@Component({
  templateUrl: './request-types-delete-dialog.component.html',
})
export class RequestTypesDeleteDialogComponent {
  requestTypes?: IRequestTypes;

  constructor(protected requestTypesService: RequestTypesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.requestTypesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
