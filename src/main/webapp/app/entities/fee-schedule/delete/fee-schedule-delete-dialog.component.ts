import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFeeSchedule } from '../fee-schedule.model';
import { FeeScheduleService } from '../service/fee-schedule.service';

@Component({
  templateUrl: './fee-schedule-delete-dialog.component.html',
})
export class FeeScheduleDeleteDialogComponent {
  feeSchedule?: IFeeSchedule;

  constructor(protected feeScheduleService: FeeScheduleService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.feeScheduleService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
