import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICardTokenData } from '../card-token-data.model';
import { CardTokenDataService } from '../service/card-token-data.service';

@Component({
  templateUrl: './card-token-data-delete-dialog.component.html',
})
export class CardTokenDataDeleteDialogComponent {
  cardTokenData?: ICardTokenData;

  constructor(protected cardTokenDataService: CardTokenDataService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cardTokenDataService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
