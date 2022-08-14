import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmails } from '../emails.model';
import { EmailsService } from '../service/emails.service';

@Component({
  templateUrl: './emails-delete-dialog.component.html',
})
export class EmailsDeleteDialogComponent {
  emails?: IEmails;

  constructor(protected emailsService: EmailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
