import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBankAccounts } from '../bank-accounts.model';
import { BankAccountsService } from '../service/bank-accounts.service';

@Component({
  templateUrl: './bank-accounts-delete-dialog.component.html',
})
export class BankAccountsDeleteDialogComponent {
  bankAccounts?: IBankAccounts;

  constructor(protected bankAccountsService: BankAccountsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bankAccountsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
