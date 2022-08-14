import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRequestDescriptions } from '../request-descriptions.model';
import { RequestDescriptionsService } from '../service/request-descriptions.service';

@Component({
  templateUrl: './request-descriptions-delete-dialog.component.html',
})
export class RequestDescriptionsDeleteDialogComponent {
  requestDescriptions?: IRequestDescriptions;

  constructor(protected requestDescriptionsService: RequestDescriptionsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.requestDescriptionsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
