import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FeeScheduleComponent } from './list/fee-schedule.component';
import { FeeScheduleDetailComponent } from './detail/fee-schedule-detail.component';
import { FeeScheduleUpdateComponent } from './update/fee-schedule-update.component';
import { FeeScheduleDeleteDialogComponent } from './delete/fee-schedule-delete-dialog.component';
import { FeeScheduleRoutingModule } from './route/fee-schedule-routing.module';

@NgModule({
  imports: [SharedModule, FeeScheduleRoutingModule],
  declarations: [FeeScheduleComponent, FeeScheduleDetailComponent, FeeScheduleUpdateComponent, FeeScheduleDeleteDialogComponent],
  entryComponents: [FeeScheduleDeleteDialogComponent],
})
export class FeeScheduleModule {}
