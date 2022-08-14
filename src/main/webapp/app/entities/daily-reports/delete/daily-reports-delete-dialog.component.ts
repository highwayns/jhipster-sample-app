import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDailyReports } from '../daily-reports.model';
import { DailyReportsService } from '../service/daily-reports.service';

@Component({
  templateUrl: './daily-reports-delete-dialog.component.html',
})
export class DailyReportsDeleteDialogComponent {
  dailyReports?: IDailyReports;

  constructor(protected dailyReportsService: DailyReportsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dailyReportsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
