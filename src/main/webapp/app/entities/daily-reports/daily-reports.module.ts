import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DailyReportsComponent } from './list/daily-reports.component';
import { DailyReportsDetailComponent } from './detail/daily-reports-detail.component';
import { DailyReportsUpdateComponent } from './update/daily-reports-update.component';
import { DailyReportsDeleteDialogComponent } from './delete/daily-reports-delete-dialog.component';
import { DailyReportsRoutingModule } from './route/daily-reports-routing.module';

@NgModule({
  imports: [SharedModule, DailyReportsRoutingModule],
  declarations: [DailyReportsComponent, DailyReportsDetailComponent, DailyReportsUpdateComponent, DailyReportsDeleteDialogComponent],
  entryComponents: [DailyReportsDeleteDialogComponent],
})
export class DailyReportsModule {}
