import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHistoryActions } from '../history-actions.model';

@Component({
  selector: 'jhi-history-actions-detail',
  templateUrl: './history-actions-detail.component.html',
})
export class HistoryActionsDetailComponent implements OnInit {
  historyActions: IHistoryActions | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ historyActions }) => {
      this.historyActions = historyActions;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
