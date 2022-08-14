import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRequestStatus } from '../request-status.model';

@Component({
  selector: 'jhi-request-status-detail',
  templateUrl: './request-status-detail.component.html',
})
export class RequestStatusDetailComponent implements OnInit {
  requestStatus: IRequestStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requestStatus }) => {
      this.requestStatus = requestStatus;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
