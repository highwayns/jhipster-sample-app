import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRequestStatus } from '../request-status.model';
import { RequestStatusService } from '../service/request-status.service';

@Component({
  templateUrl: './request-status-delete-dialog.component.html',
})
export class RequestStatusDeleteDialogComponent {
  requestStatus?: IRequestStatus;

  constructor(protected requestStatusService: RequestStatusService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.requestStatusService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
