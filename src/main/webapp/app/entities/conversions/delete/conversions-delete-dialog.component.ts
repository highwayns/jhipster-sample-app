import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IConversions } from '../conversions.model';
import { ConversionsService } from '../service/conversions.service';

@Component({
  templateUrl: './conversions-delete-dialog.component.html',
})
export class ConversionsDeleteDialogComponent {
  conversions?: IConversions;

  constructor(protected conversionsService: ConversionsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.conversionsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
