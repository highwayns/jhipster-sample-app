import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBitcoindLog } from '../bitcoind-log.model';
import { BitcoindLogService } from '../service/bitcoind-log.service';

@Component({
  templateUrl: './bitcoind-log-delete-dialog.component.html',
})
export class BitcoindLogDeleteDialogComponent {
  bitcoindLog?: IBitcoindLog;

  constructor(protected bitcoindLogService: BitcoindLogService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bitcoindLogService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
