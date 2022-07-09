import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAbuseTrigger } from '../abuse-trigger.model';

@Component({
  selector: 'jhi-abuse-trigger-detail',
  templateUrl: './abuse-trigger-detail.component.html',
})
export class AbuseTriggerDetailComponent implements OnInit {
  abuseTrigger: IAbuseTrigger | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ abuseTrigger }) => {
      this.abuseTrigger = abuseTrigger;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
