import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMonthlyReports } from '../monthly-reports.model';

@Component({
  selector: 'jhi-monthly-reports-detail',
  templateUrl: './monthly-reports-detail.component.html',
})
export class MonthlyReportsDetailComponent implements OnInit {
  monthlyReports: IMonthlyReports | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ monthlyReports }) => {
      this.monthlyReports = monthlyReports;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
