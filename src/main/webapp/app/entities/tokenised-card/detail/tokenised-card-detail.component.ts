import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITokenisedCard } from '../tokenised-card.model';

@Component({
  selector: 'jhi-tokenised-card-detail',
  templateUrl: './tokenised-card-detail.component.html',
})
export class TokenisedCardDetailComponent implements OnInit {
  tokenisedCard: ITokenisedCard | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tokenisedCard }) => {
      this.tokenisedCard = tokenisedCard;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
