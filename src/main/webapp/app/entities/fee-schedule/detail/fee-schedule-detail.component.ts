import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFeeSchedule } from '../fee-schedule.model';

@Component({
  selector: 'jhi-fee-schedule-detail',
  templateUrl: './fee-schedule-detail.component.html',
})
export class FeeScheduleDetailComponent implements OnInit {
  feeSchedule: IFeeSchedule | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feeSchedule }) => {
      this.feeSchedule = feeSchedule;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
