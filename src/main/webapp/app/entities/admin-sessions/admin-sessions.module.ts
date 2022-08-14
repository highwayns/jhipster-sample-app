import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AdminSessionsComponent } from './list/admin-sessions.component';
import { AdminSessionsDetailComponent } from './detail/admin-sessions-detail.component';
import { AdminSessionsUpdateComponent } from './update/admin-sessions-update.component';
import { AdminSessionsDeleteDialogComponent } from './delete/admin-sessions-delete-dialog.component';
import { AdminSessionsRoutingModule } from './route/admin-sessions-routing.module';

@NgModule({
  imports: [SharedModule, AdminSessionsRoutingModule],
  declarations: [AdminSessionsComponent, AdminSessionsDetailComponent, AdminSessionsUpdateComponent, AdminSessionsDeleteDialogComponent],
  entryComponents: [AdminSessionsDeleteDialogComponent],
})
export class AdminSessionsModule {}
