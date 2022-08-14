import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DailyReportsComponent } from '../list/daily-reports.component';
import { DailyReportsDetailComponent } from '../detail/daily-reports-detail.component';
import { DailyReportsUpdateComponent } from '../update/daily-reports-update.component';
import { DailyReportsRoutingResolveService } from './daily-reports-routing-resolve.service';

const dailyReportsRoute: Routes = [
  {
    path: '',
    component: DailyReportsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DailyReportsDetailComponent,
    resolve: {
      dailyReports: DailyReportsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DailyReportsUpdateComponent,
    resolve: {
      dailyReports: DailyReportsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DailyReportsUpdateComponent,
    resolve: {
      dailyReports: DailyReportsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dailyReportsRoute)],
  exports: [RouterModule],
})
export class DailyReportsRoutingModule {}
