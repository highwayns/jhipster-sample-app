import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICurrentStats } from '../current-stats.model';

@Component({
  selector: 'jhi-current-stats-detail',
  templateUrl: './current-stats-detail.component.html',
})
export class CurrentStatsDetailComponent implements OnInit {
  currentStats: ICurrentStats | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ currentStats }) => {
      this.currentStats = currentStats;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
