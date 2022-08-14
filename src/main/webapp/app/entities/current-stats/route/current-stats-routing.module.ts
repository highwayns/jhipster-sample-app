import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CurrentStatsComponent } from '../list/current-stats.component';
import { CurrentStatsDetailComponent } from '../detail/current-stats-detail.component';
import { CurrentStatsUpdateComponent } from '../update/current-stats-update.component';
import { CurrentStatsRoutingResolveService } from './current-stats-routing-resolve.service';

const currentStatsRoute: Routes = [
  {
    path: '',
    component: CurrentStatsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CurrentStatsDetailComponent,
    resolve: {
      currentStats: CurrentStatsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CurrentStatsUpdateComponent,
    resolve: {
      currentStats: CurrentStatsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CurrentStatsUpdateComponent,
    resolve: {
      currentStats: CurrentStatsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(currentStatsRoute)],
  exports: [RouterModule],
})
export class CurrentStatsRoutingModule {}
