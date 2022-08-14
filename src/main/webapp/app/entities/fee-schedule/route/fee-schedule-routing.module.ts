import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FeeScheduleComponent } from '../list/fee-schedule.component';
import { FeeScheduleDetailComponent } from '../detail/fee-schedule-detail.component';
import { FeeScheduleUpdateComponent } from '../update/fee-schedule-update.component';
import { FeeScheduleRoutingResolveService } from './fee-schedule-routing-resolve.service';

const feeScheduleRoute: Routes = [
  {
    path: '',
    component: FeeScheduleComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FeeScheduleDetailComponent,
    resolve: {
      feeSchedule: FeeScheduleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FeeScheduleUpdateComponent,
    resolve: {
      feeSchedule: FeeScheduleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FeeScheduleUpdateComponent,
    resolve: {
      feeSchedule: FeeScheduleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(feeScheduleRoute)],
  exports: [RouterModule],
})
export class FeeScheduleRoutingModule {}
