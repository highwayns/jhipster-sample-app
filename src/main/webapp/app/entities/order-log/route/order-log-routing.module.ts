import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrderLogComponent } from '../list/order-log.component';
import { OrderLogDetailComponent } from '../detail/order-log-detail.component';
import { OrderLogUpdateComponent } from '../update/order-log-update.component';
import { OrderLogRoutingResolveService } from './order-log-routing-resolve.service';

const orderLogRoute: Routes = [
  {
    path: '',
    component: OrderLogComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrderLogDetailComponent,
    resolve: {
      orderLog: OrderLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrderLogUpdateComponent,
    resolve: {
      orderLog: OrderLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrderLogUpdateComponent,
    resolve: {
      orderLog: OrderLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(orderLogRoute)],
  exports: [RouterModule],
})
export class OrderLogRoutingModule {}
