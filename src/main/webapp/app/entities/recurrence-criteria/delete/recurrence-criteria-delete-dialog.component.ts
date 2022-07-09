import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRecurrenceCriteria } from '../recurrence-criteria.model';
import { RecurrenceCriteriaService } from '../service/recurrence-criteria.service';

@Component({
  templateUrl: './recurrence-criteria-delete-dialog.component.html',
})
export class RecurrenceCriteriaDeleteDialogComponent {
  recurrenceCriteria?: IRecurrenceCriteria;

  constructor(protected recurrenceCriteriaService: RecurrenceCriteriaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.recurrenceCriteriaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
