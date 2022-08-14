import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BitcoindLogComponent } from './list/bitcoind-log.component';
import { BitcoindLogDetailComponent } from './detail/bitcoind-log-detail.component';
import { BitcoindLogUpdateComponent } from './update/bitcoind-log-update.component';
import { BitcoindLogDeleteDialogComponent } from './delete/bitcoind-log-delete-dialog.component';
import { BitcoindLogRoutingModule } from './route/bitcoind-log-routing.module';

@NgModule({
  imports: [SharedModule, BitcoindLogRoutingModule],
  declarations: [BitcoindLogComponent, BitcoindLogDetailComponent, BitcoindLogUpdateComponent, BitcoindLogDeleteDialogComponent],
  entryComponents: [BitcoindLogDeleteDialogComponent],
})
export class BitcoindLogModule {}
