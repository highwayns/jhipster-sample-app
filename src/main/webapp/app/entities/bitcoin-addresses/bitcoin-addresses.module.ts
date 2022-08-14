import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BitcoinAddressesComponent } from './list/bitcoin-addresses.component';
import { BitcoinAddressesDetailComponent } from './detail/bitcoin-addresses-detail.component';
import { BitcoinAddressesUpdateComponent } from './update/bitcoin-addresses-update.component';
import { BitcoinAddressesDeleteDialogComponent } from './delete/bitcoin-addresses-delete-dialog.component';
import { BitcoinAddressesRoutingModule } from './route/bitcoin-addresses-routing.module';

@NgModule({
  imports: [SharedModule, BitcoinAddressesRoutingModule],
  declarations: [
    BitcoinAddressesComponent,
    BitcoinAddressesDetailComponent,
    BitcoinAddressesUpdateComponent,
    BitcoinAddressesDeleteDialogComponent,
  ],
  entryComponents: [BitcoinAddressesDeleteDialogComponent],
})
export class BitcoinAddressesModule {}
