import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HistoryActionsComponent } from '../list/history-actions.component';
import { HistoryActionsDetailComponent } from '../detail/history-actions-detail.component';
import { HistoryActionsUpdateComponent } from '../update/history-actions-update.component';
import { HistoryActionsRoutingResolveService } from './history-actions-routing-resolve.service';

const historyActionsRoute: Routes = [
  {
    path: '',
    component: HistoryActionsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HistoryActionsDetailComponent,
    resolve: {
      historyActions: HistoryActionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HistoryActionsUpdateComponent,
    resolve: {
      historyActions: HistoryActionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HistoryActionsUpdateComponent,
    resolve: {
      historyActions: HistoryActionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(historyActionsRoute)],
  exports: [RouterModule],
})
export class HistoryActionsRoutingModule {}
