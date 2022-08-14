import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AdminGroupsTabsComponent } from './list/admin-groups-tabs.component';
import { AdminGroupsTabsDetailComponent } from './detail/admin-groups-tabs-detail.component';
import { AdminGroupsTabsUpdateComponent } from './update/admin-groups-tabs-update.component';
import { AdminGroupsTabsDeleteDialogComponent } from './delete/admin-groups-tabs-delete-dialog.component';
import { AdminGroupsTabsRoutingModule } from './route/admin-groups-tabs-routing.module';

@NgModule({
  imports: [SharedModule, AdminGroupsTabsRoutingModule],
  declarations: [
    AdminGroupsTabsComponent,
    AdminGroupsTabsDetailComponent,
    AdminGroupsTabsUpdateComponent,
    AdminGroupsTabsDeleteDialogComponent,
  ],
  entryComponents: [AdminGroupsTabsDeleteDialogComponent],
})
export class AdminGroupsTabsModule {}
