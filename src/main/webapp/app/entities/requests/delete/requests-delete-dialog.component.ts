import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRequests } from '../requests.model';
import { RequestsService } from '../service/requests.service';

@Component({
  templateUrl: './requests-delete-dialog.component.html',
})
export class RequestsDeleteDialogComponent {
  requests?: IRequests;

  constructor(protected requestsService: RequestsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.requestsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
