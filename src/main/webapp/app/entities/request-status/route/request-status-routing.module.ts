import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RequestStatusComponent } from '../list/request-status.component';
import { RequestStatusDetailComponent } from '../detail/request-status-detail.component';
import { RequestStatusUpdateComponent } from '../update/request-status-update.component';
import { RequestStatusRoutingResolveService } from './request-status-routing-resolve.service';

const requestStatusRoute: Routes = [
  {
    path: '',
    component: RequestStatusComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RequestStatusDetailComponent,
    resolve: {
      requestStatus: RequestStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RequestStatusUpdateComponent,
    resolve: {
      requestStatus: RequestStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RequestStatusUpdateComponent,
    resolve: {
      requestStatus: RequestStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(requestStatusRoute)],
  exports: [RouterModule],
})
export class RequestStatusRoutingModule {}
