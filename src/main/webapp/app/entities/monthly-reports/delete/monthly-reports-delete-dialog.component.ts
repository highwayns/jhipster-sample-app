import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMonthlyReports } from '../monthly-reports.model';
import { MonthlyReportsService } from '../service/monthly-reports.service';

@Component({
  templateUrl: './monthly-reports-delete-dialog.component.html',
})
export class MonthlyReportsDeleteDialogComponent {
  monthlyReports?: IMonthlyReports;

  constructor(protected monthlyReportsService: MonthlyReportsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.monthlyReportsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
