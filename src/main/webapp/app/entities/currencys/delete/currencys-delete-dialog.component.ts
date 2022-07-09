import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICurrencys } from '../currencys.model';
import { CurrencysService } from '../service/currencys.service';

@Component({
  templateUrl: './currencys-delete-dialog.component.html',
})
export class CurrencysDeleteDialogComponent {
  currencys?: ICurrencys;

  constructor(protected currencysService: CurrencysService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.currencysService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
