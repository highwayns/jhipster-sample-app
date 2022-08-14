import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AdminTabsComponent } from './list/admin-tabs.component';
import { AdminTabsDetailComponent } from './detail/admin-tabs-detail.component';
import { AdminTabsUpdateComponent } from './update/admin-tabs-update.component';
import { AdminTabsDeleteDialogComponent } from './delete/admin-tabs-delete-dialog.component';
import { AdminTabsRoutingModule } from './route/admin-tabs-routing.module';

@NgModule({
  imports: [SharedModule, AdminTabsRoutingModule],
  declarations: [AdminTabsComponent, AdminTabsDetailComponent, AdminTabsUpdateComponent, AdminTabsDeleteDialogComponent],
  entryComponents: [AdminTabsDeleteDialogComponent],
})
export class AdminTabsModule {}
