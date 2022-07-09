import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IErrorReport } from '../error-report.model';
import { ErrorReportService } from '../service/error-report.service';
import { ErrorReportDeleteDialogComponent } from '../delete/error-report-delete-dialog.component';

@Component({
  selector: 'jhi-error-report',
  templateUrl: './error-report.component.html',
})
export class ErrorReportComponent implements OnInit {
  errorReports?: IErrorReport[];
  isLoading = false;

  constructor(protected errorReportService: ErrorReportService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.errorReportService.query().subscribe({
      next: (res: HttpResponse<IErrorReport[]>) => {
        this.isLoading = false;
        this.errorReports = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IErrorReport): number {
    return item.id!;
  }

  delete(errorReport: IErrorReport): void {
    const modalRef = this.modalService.open(ErrorReportDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.errorReport = errorReport;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
