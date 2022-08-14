import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrderTypesComponent } from '../list/order-types.component';
import { OrderTypesDetailComponent } from '../detail/order-types-detail.component';
import { OrderTypesUpdateComponent } from '../update/order-types-update.component';
import { OrderTypesRoutingResolveService } from './order-types-routing-resolve.service';

const orderTypesRoute: Routes = [
  {
    path: '',
    component: OrderTypesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrderTypesDetailComponent,
    resolve: {
      orderTypes: OrderTypesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrderTypesUpdateComponent,
    resolve: {
      orderTypes: OrderTypesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrderTypesUpdateComponent,
    resolve: {
      orderTypes: OrderTypesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(orderTypesRoute)],
  exports: [RouterModule],
})
export class OrderTypesRoutingModule {}
