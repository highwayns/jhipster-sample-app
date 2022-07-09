import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AbuseReportComponent } from './list/abuse-report.component';
import { AbuseReportDetailComponent } from './detail/abuse-report-detail.component';
import { AbuseReportUpdateComponent } from './update/abuse-report-update.component';
import { AbuseReportDeleteDialogComponent } from './delete/abuse-report-delete-dialog.component';
import { AbuseReportRoutingModule } from './route/abuse-report-routing.module';

@NgModule({
  imports: [SharedModule, AbuseReportRoutingModule],
  declarations: [AbuseReportComponent, AbuseReportDetailComponent, AbuseReportUpdateComponent, AbuseReportDeleteDialogComponent],
  entryComponents: [AbuseReportDeleteDialogComponent],
})
export class AbuseReportModule {}
