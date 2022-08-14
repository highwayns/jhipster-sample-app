import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HistoricalDataComponent } from '../list/historical-data.component';
import { HistoricalDataDetailComponent } from '../detail/historical-data-detail.component';
import { HistoricalDataUpdateComponent } from '../update/historical-data-update.component';
import { HistoricalDataRoutingResolveService } from './historical-data-routing-resolve.service';

const historicalDataRoute: Routes = [
  {
    path: '',
    component: HistoricalDataComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HistoricalDataDetailComponent,
    resolve: {
      historicalData: HistoricalDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HistoricalDataUpdateComponent,
    resolve: {
      historicalData: HistoricalDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HistoricalDataUpdateComponent,
    resolve: {
      historicalData: HistoricalDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(historicalDataRoute)],
  exports: [RouterModule],
})
export class HistoricalDataRoutingModule {}
