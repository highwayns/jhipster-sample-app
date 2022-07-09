import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITokenisedCard } from '../tokenised-card.model';
import { TokenisedCardService } from '../service/tokenised-card.service';
import { TokenisedCardDeleteDialogComponent } from '../delete/tokenised-card-delete-dialog.component';

@Component({
  selector: 'jhi-tokenised-card',
  templateUrl: './tokenised-card.component.html',
})
export class TokenisedCardComponent implements OnInit {
  tokenisedCards?: ITokenisedCard[];
  isLoading = false;

  constructor(protected tokenisedCardService: TokenisedCardService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.tokenisedCardService.query().subscribe({
      next: (res: HttpResponse<ITokenisedCard[]>) => {
        this.isLoading = false;
        this.tokenisedCards = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ITokenisedCard): number {
    return item.id!;
  }

  delete(tokenisedCard: ITokenisedCard): void {
    const modalRef = this.modalService.open(TokenisedCardDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tokenisedCard = tokenisedCard;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
