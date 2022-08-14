import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHistoricalData } from '../historical-data.model';

@Component({
  selector: 'jhi-historical-data-detail',
  templateUrl: './historical-data-detail.component.html',
})
export class HistoricalDataDetailComponent implements OnInit {
  historicalData: IHistoricalData | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ historicalData }) => {
      this.historicalData = historicalData;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
