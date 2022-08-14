import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRequestTypes } from '../request-types.model';

@Component({
  selector: 'jhi-request-types-detail',
  templateUrl: './request-types-detail.component.html',
})
export class RequestTypesDetailComponent implements OnInit {
  requestTypes: IRequestTypes | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requestTypes }) => {
      this.requestTypes = requestTypes;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
