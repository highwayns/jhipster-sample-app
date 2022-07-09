import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAbuseReport } from '../abuse-report.model';
import { AbuseReportService } from '../service/abuse-report.service';
import { AbuseReportDeleteDialogComponent } from '../delete/abuse-report-delete-dialog.component';

@Component({
  selector: 'jhi-abuse-report',
  templateUrl: './abuse-report.component.html',
})
export class AbuseReportComponent implements OnInit {
  abuseReports?: IAbuseReport[];
  isLoading = false;

  constructor(protected abuseReportService: AbuseReportService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.abuseReportService.query().subscribe({
      next: (res: HttpResponse<IAbuseReport[]>) => {
        this.isLoading = false;
        this.abuseReports = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IAbuseReport): number {
    return item.id!;
  }

  delete(abuseReport: IAbuseReport): void {
    const modalRef = this.modalService.open(AbuseReportDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.abuseReport = abuseReport;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
