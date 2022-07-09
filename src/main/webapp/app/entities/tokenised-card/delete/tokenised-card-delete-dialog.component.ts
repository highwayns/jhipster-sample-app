import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITokenisedCard } from '../tokenised-card.model';
import { TokenisedCardService } from '../service/tokenised-card.service';

@Component({
  templateUrl: './tokenised-card-delete-dialog.component.html',
})
export class TokenisedCardDeleteDialogComponent {
  tokenisedCard?: ITokenisedCard;

  constructor(protected tokenisedCardService: TokenisedCardService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tokenisedCardService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
