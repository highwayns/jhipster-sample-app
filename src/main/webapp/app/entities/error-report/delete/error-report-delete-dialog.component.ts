import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IErrorReport } from '../error-report.model';
import { ErrorReportService } from '../service/error-report.service';

@Component({
  templateUrl: './error-report-delete-dialog.component.html',
})
export class ErrorReportDeleteDialogComponent {
  errorReport?: IErrorReport;

  constructor(protected errorReportService: ErrorReportService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.errorReportService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
