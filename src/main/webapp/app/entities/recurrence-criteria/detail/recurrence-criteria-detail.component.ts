import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecurrenceCriteria } from '../recurrence-criteria.model';

@Component({
  selector: 'jhi-recurrence-criteria-detail',
  templateUrl: './recurrence-criteria-detail.component.html',
})
export class RecurrenceCriteriaDetailComponent implements OnInit {
  recurrenceCriteria: IRecurrenceCriteria | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recurrenceCriteria }) => {
      this.recurrenceCriteria = recurrenceCriteria;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
