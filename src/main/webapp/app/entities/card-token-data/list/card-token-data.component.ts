import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICardTokenData } from '../card-token-data.model';
import { CardTokenDataService } from '../service/card-token-data.service';
import { CardTokenDataDeleteDialogComponent } from '../delete/card-token-data-delete-dialog.component';

@Component({
  selector: 'jhi-card-token-data',
  templateUrl: './card-token-data.component.html',
})
export class CardTokenDataComponent implements OnInit {
  cardTokenData?: ICardTokenData[];
  isLoading = false;

  constructor(protected cardTokenDataService: CardTokenDataService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.cardTokenDataService.query().subscribe({
      next: (res: HttpResponse<ICardTokenData[]>) => {
        this.isLoading = false;
        this.cardTokenData = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ICardTokenData): number {
    return item.id!;
  }

  delete(cardTokenData: ICardTokenData): void {
    const modalRef = this.modalService.open(CardTokenDataDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cardTokenData = cardTokenData;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
