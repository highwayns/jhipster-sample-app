import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICardTokenData } from '../card-token-data.model';

@Component({
  selector: 'jhi-card-token-data-detail',
  templateUrl: './card-token-data-detail.component.html',
})
export class CardTokenDataDetailComponent implements OnInit {
  cardTokenData: ICardTokenData | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cardTokenData }) => {
      this.cardTokenData = cardTokenData;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
