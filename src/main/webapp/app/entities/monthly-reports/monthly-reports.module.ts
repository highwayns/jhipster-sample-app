import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MonthlyReportsComponent } from './list/monthly-reports.component';
import { MonthlyReportsDetailComponent } from './detail/monthly-reports-detail.component';
import { MonthlyReportsUpdateComponent } from './update/monthly-reports-update.component';
import { MonthlyReportsDeleteDialogComponent } from './delete/monthly-reports-delete-dialog.component';
import { MonthlyReportsRoutingModule } from './route/monthly-reports-routing.module';

@NgModule({
  imports: [SharedModule, MonthlyReportsRoutingModule],
  declarations: [
    MonthlyReportsComponent,
    MonthlyReportsDetailComponent,
    MonthlyReportsUpdateComponent,
    MonthlyReportsDeleteDialogComponent,
  ],
  entryComponents: [MonthlyReportsDeleteDialogComponent],
})
export class MonthlyReportsModule {}
