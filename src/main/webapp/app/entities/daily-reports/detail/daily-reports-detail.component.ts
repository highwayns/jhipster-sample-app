import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDailyReports } from '../daily-reports.model';

@Component({
  selector: 'jhi-daily-reports-detail',
  templateUrl: './daily-reports-detail.component.html',
})
export class DailyReportsDetailComponent implements OnInit {
  dailyReports: IDailyReports | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dailyReports }) => {
      this.dailyReports = dailyReports;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
