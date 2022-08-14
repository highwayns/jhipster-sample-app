import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MonthlyReportsComponent } from '../list/monthly-reports.component';
import { MonthlyReportsDetailComponent } from '../detail/monthly-reports-detail.component';
import { MonthlyReportsUpdateComponent } from '../update/monthly-reports-update.component';
import { MonthlyReportsRoutingResolveService } from './monthly-reports-routing-resolve.service';

const monthlyReportsRoute: Routes = [
  {
    path: '',
    component: MonthlyReportsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MonthlyReportsDetailComponent,
    resolve: {
      monthlyReports: MonthlyReportsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MonthlyReportsUpdateComponent,
    resolve: {
      monthlyReports: MonthlyReportsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MonthlyReportsUpdateComponent,
    resolve: {
      monthlyReports: MonthlyReportsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(monthlyReportsRoute)],
  exports: [RouterModule],
})
export class MonthlyReportsRoutingModule {}
