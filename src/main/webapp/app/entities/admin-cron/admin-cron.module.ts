import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AdminCronComponent } from './list/admin-cron.component';
import { AdminCronDetailComponent } from './detail/admin-cron-detail.component';
import { AdminCronUpdateComponent } from './update/admin-cron-update.component';
import { AdminCronDeleteDialogComponent } from './delete/admin-cron-delete-dialog.component';
import { AdminCronRoutingModule } from './route/admin-cron-routing.module';

@NgModule({
  imports: [SharedModule, AdminCronRoutingModule],
  declarations: [AdminCronComponent, AdminCronDetailComponent, AdminCronUpdateComponent, AdminCronDeleteDialogComponent],
  entryComponents: [AdminCronDeleteDialogComponent],
})
export class AdminCronModule {}
