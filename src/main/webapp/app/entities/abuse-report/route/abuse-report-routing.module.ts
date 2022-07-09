import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AbuseReportComponent } from '../list/abuse-report.component';
import { AbuseReportDetailComponent } from '../detail/abuse-report-detail.component';
import { AbuseReportUpdateComponent } from '../update/abuse-report-update.component';
import { AbuseReportRoutingResolveService } from './abuse-report-routing-resolve.service';

const abuseReportRoute: Routes = [
  {
    path: '',
    component: AbuseReportComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AbuseReportDetailComponent,
    resolve: {
      abuseReport: AbuseReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AbuseReportUpdateComponent,
    resolve: {
      abuseReport: AbuseReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AbuseReportUpdateComponent,
    resolve: {
      abuseReport: AbuseReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(abuseReportRoute)],
  exports: [RouterModule],
})
export class AbuseReportRoutingModule {}
