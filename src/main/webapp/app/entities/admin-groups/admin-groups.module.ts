import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AdminGroupsComponent } from './list/admin-groups.component';
import { AdminGroupsDetailComponent } from './detail/admin-groups-detail.component';
import { AdminGroupsUpdateComponent } from './update/admin-groups-update.component';
import { AdminGroupsDeleteDialogComponent } from './delete/admin-groups-delete-dialog.component';
import { AdminGroupsRoutingModule } from './route/admin-groups-routing.module';

@NgModule({
  imports: [SharedModule, AdminGroupsRoutingModule],
  declarations: [AdminGroupsComponent, AdminGroupsDetailComponent, AdminGroupsUpdateComponent, AdminGroupsDeleteDialogComponent],
  entryComponents: [AdminGroupsDeleteDialogComponent],
})
export class AdminGroupsModule {}
