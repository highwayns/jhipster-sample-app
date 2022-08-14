import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRequestDescriptions } from '../request-descriptions.model';

@Component({
  selector: 'jhi-request-descriptions-detail',
  templateUrl: './request-descriptions-detail.component.html',
})
export class RequestDescriptionsDetailComponent implements OnInit {
  requestDescriptions: IRequestDescriptions | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requestDescriptions }) => {
      this.requestDescriptions = requestDescriptions;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
