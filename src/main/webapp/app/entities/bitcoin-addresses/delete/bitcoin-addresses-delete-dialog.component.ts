import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBitcoinAddresses } from '../bitcoin-addresses.model';
import { BitcoinAddressesService } from '../service/bitcoin-addresses.service';

@Component({
  templateUrl: './bitcoin-addresses-delete-dialog.component.html',
})
export class BitcoinAddressesDeleteDialogComponent {
  bitcoinAddresses?: IBitcoinAddresses;

  constructor(protected bitcoinAddressesService: BitcoinAddressesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bitcoinAddressesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
