import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AdminControlsMethodsComponent } from './list/admin-controls-methods.component';
import { AdminControlsMethodsDetailComponent } from './detail/admin-controls-methods-detail.component';
import { AdminControlsMethodsUpdateComponent } from './update/admin-controls-methods-update.component';
import { AdminControlsMethodsDeleteDialogComponent } from './delete/admin-controls-methods-delete-dialog.component';
import { AdminControlsMethodsRoutingModule } from './route/admin-controls-methods-routing.module';

@NgModule({
  imports: [SharedModule, AdminControlsMethodsRoutingModule],
  declarations: [
    AdminControlsMethodsComponent,
    AdminControlsMethodsDetailComponent,
    AdminControlsMethodsUpdateComponent,
    AdminControlsMethodsDeleteDialogComponent,
  ],
  entryComponents: [AdminControlsMethodsDeleteDialogComponent],
})
export class AdminControlsMethodsModule {}
