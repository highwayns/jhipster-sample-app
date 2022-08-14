import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransactionTypes } from '../transaction-types.model';
import { TransactionTypesService } from '../service/transaction-types.service';

@Component({
  templateUrl: './transaction-types-delete-dialog.component.html',
})
export class TransactionTypesDeleteDialogComponent {
  transactionTypes?: ITransactionTypes;

  constructor(protected transactionTypesService: TransactionTypesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transactionTypesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
