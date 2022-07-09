import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ErrorReportComponent } from '../list/error-report.component';
import { ErrorReportDetailComponent } from '../detail/error-report-detail.component';
import { ErrorReportUpdateComponent } from '../update/error-report-update.component';
import { ErrorReportRoutingResolveService } from './error-report-routing-resolve.service';

const errorReportRoute: Routes = [
  {
    path: '',
    component: ErrorReportComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ErrorReportDetailComponent,
    resolve: {
      errorReport: ErrorReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ErrorReportUpdateComponent,
    resolve: {
      errorReport: ErrorReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ErrorReportUpdateComponent,
    resolve: {
      errorReport: ErrorReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(errorReportRoute)],
  exports: [RouterModule],
})
export class ErrorReportRoutingModule {}
