import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAbuseReport } from '../abuse-report.model';
import { AbuseReportService } from '../service/abuse-report.service';

@Component({
  templateUrl: './abuse-report-delete-dialog.component.html',
})
export class AbuseReportDeleteDialogComponent {
  abuseReport?: IAbuseReport;

  constructor(protected abuseReportService: AbuseReportService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.abuseReportService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
