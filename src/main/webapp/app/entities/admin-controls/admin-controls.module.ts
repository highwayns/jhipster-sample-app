import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AdminControlsComponent } from './list/admin-controls.component';
import { AdminControlsDetailComponent } from './detail/admin-controls-detail.component';
import { AdminControlsUpdateComponent } from './update/admin-controls-update.component';
import { AdminControlsDeleteDialogComponent } from './delete/admin-controls-delete-dialog.component';
import { AdminControlsRoutingModule } from './route/admin-controls-routing.module';

@NgModule({
  imports: [SharedModule, AdminControlsRoutingModule],
  declarations: [AdminControlsComponent, AdminControlsDetailComponent, AdminControlsUpdateComponent, AdminControlsDeleteDialogComponent],
  entryComponents: [AdminControlsDeleteDialogComponent],
})
export class AdminControlsModule {}
