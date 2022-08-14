import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConversions } from '../conversions.model';

@Component({
  selector: 'jhi-conversions-detail',
  templateUrl: './conversions-detail.component.html',
})
export class ConversionsDetailComponent implements OnInit {
  conversions: IConversions | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conversions }) => {
      this.conversions = conversions;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
