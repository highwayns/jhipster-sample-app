import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ErrorReportComponent } from './list/error-report.component';
import { ErrorReportDetailComponent } from './detail/error-report-detail.component';
import { ErrorReportUpdateComponent } from './update/error-report-update.component';
import { ErrorReportDeleteDialogComponent } from './delete/error-report-delete-dialog.component';
import { ErrorReportRoutingModule } from './route/error-report-routing.module';

@NgModule({
  imports: [SharedModule, ErrorReportRoutingModule],
  declarations: [ErrorReportComponent, ErrorReportDetailComponent, ErrorReportUpdateComponent, ErrorReportDeleteDialogComponent],
  entryComponents: [ErrorReportDeleteDialogComponent],
})
export class ErrorReportModule {}
