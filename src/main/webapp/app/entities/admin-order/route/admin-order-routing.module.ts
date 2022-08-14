import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdminOrderComponent } from '../list/admin-order.component';
import { AdminOrderDetailComponent } from '../detail/admin-order-detail.component';
import { AdminOrderUpdateComponent } from '../update/admin-order-update.component';
import { AdminOrderRoutingResolveService } from './admin-order-routing-resolve.service';

const adminOrderRoute: Routes = [
  {
    path: '',
    component: AdminOrderComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdminOrderDetailComponent,
    resolve: {
      adminOrder: AdminOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdminOrderUpdateComponent,
    resolve: {
      adminOrder: AdminOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdminOrderUpdateComponent,
    resolve: {
      adminOrder: AdminOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(adminOrderRoute)],
  exports: [RouterModule],
})
export class AdminOrderRoutingModule {}
