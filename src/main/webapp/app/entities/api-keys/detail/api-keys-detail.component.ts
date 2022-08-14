import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiKeys } from '../api-keys.model';

@Component({
  selector: 'jhi-api-keys-detail',
  templateUrl: './api-keys-detail.component.html',
})
export class ApiKeysDetailComponent implements OnInit {
  apiKeys: IApiKeys | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ apiKeys }) => {
      this.apiKeys = apiKeys;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
