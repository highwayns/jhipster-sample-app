import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAbuseReport } from '../abuse-report.model';

@Component({
  selector: 'jhi-abuse-report-detail',
  templateUrl: './abuse-report-detail.component.html',
})
export class AbuseReportDetailComponent implements OnInit {
  abuseReport: IAbuseReport | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ abuseReport }) => {
      this.abuseReport = abuseReport;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
