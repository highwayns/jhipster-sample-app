import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHistoryActions } from '../history-actions.model';
import { HistoryActionsService } from '../service/history-actions.service';

@Component({
  templateUrl: './history-actions-delete-dialog.component.html',
})
export class HistoryActionsDeleteDialogComponent {
  historyActions?: IHistoryActions;

  constructor(protected historyActionsService: HistoryActionsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.historyActionsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
