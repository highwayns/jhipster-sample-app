import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAbuseTrigger } from '../abuse-trigger.model';
import { AbuseTriggerService } from '../service/abuse-trigger.service';

@Component({
  templateUrl: './abuse-trigger-delete-dialog.component.html',
})
export class AbuseTriggerDeleteDialogComponent {
  abuseTrigger?: IAbuseTrigger;

  constructor(protected abuseTriggerService: AbuseTriggerService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.abuseTriggerService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
