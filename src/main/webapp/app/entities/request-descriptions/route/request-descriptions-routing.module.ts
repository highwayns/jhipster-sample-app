import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RequestDescriptionsComponent } from '../list/request-descriptions.component';
import { RequestDescriptionsDetailComponent } from '../detail/request-descriptions-detail.component';
import { RequestDescriptionsUpdateComponent } from '../update/request-descriptions-update.component';
import { RequestDescriptionsRoutingResolveService } from './request-descriptions-routing-resolve.service';

const requestDescriptionsRoute: Routes = [
  {
    path: '',
    component: RequestDescriptionsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RequestDescriptionsDetailComponent,
    resolve: {
      requestDescriptions: RequestDescriptionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RequestDescriptionsUpdateComponent,
    resolve: {
      requestDescriptions: RequestDescriptionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RequestDescriptionsUpdateComponent,
    resolve: {
      requestDescriptions: RequestDescriptionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(requestDescriptionsRoute)],
  exports: [RouterModule],
})
export class RequestDescriptionsRoutingModule {}
