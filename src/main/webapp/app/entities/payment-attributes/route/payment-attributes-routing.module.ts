import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PaymentAttributesComponent } from '../list/payment-attributes.component';
import { PaymentAttributesDetailComponent } from '../detail/payment-attributes-detail.component';
import { PaymentAttributesUpdateComponent } from '../update/payment-attributes-update.component';
import { PaymentAttributesRoutingResolveService } from './payment-attributes-routing-resolve.service';

const paymentAttributesRoute: Routes = [
  {
    path: '',
    component: PaymentAttributesComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentAttributesDetailComponent,
    resolve: {
      paymentAttributes: PaymentAttributesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentAttributesUpdateComponent,
    resolve: {
      paymentAttributes: PaymentAttributesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentAttributesUpdateComponent,
    resolve: {
      paymentAttributes: PaymentAttributesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(paymentAttributesRoute)],
  exports: [RouterModule],
})
export class PaymentAttributesRoutingModule {}
