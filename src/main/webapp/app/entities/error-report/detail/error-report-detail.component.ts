import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IErrorReport } from '../error-report.model';

@Component({
  selector: 'jhi-error-report-detail',
  templateUrl: './error-report-detail.component.html',
})
export class ErrorReportDetailComponent implements OnInit {
  errorReport: IErrorReport | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ errorReport }) => {
      this.errorReport = errorReport;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
