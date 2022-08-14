import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StatusEscrowsComponent } from './list/status-escrows.component';
import { StatusEscrowsDetailComponent } from './detail/status-escrows-detail.component';
import { StatusEscrowsUpdateComponent } from './update/status-escrows-update.component';
import { StatusEscrowsDeleteDialogComponent } from './delete/status-escrows-delete-dialog.component';
import { StatusEscrowsRoutingModule } from './route/status-escrows-routing.module';

@NgModule({
  imports: [SharedModule, StatusEscrowsRoutingModule],
  declarations: [StatusEscrowsComponent, StatusEscrowsDetailComponent, StatusEscrowsUpdateComponent, StatusEscrowsDeleteDialogComponent],
  entryComponents: [StatusEscrowsDeleteDialogComponent],
})
export class StatusEscrowsModule {}
