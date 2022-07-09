import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PaymentJobAttributesComponent } from '../list/payment-job-attributes.component';
import { PaymentJobAttributesDetailComponent } from '../detail/payment-job-attributes-detail.component';
import { PaymentJobAttributesUpdateComponent } from '../update/payment-job-attributes-update.component';
import { PaymentJobAttributesRoutingResolveService } from './payment-job-attributes-routing-resolve.service';

const paymentJobAttributesRoute: Routes = [
  {
    path: '',
    component: PaymentJobAttributesComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentJobAttributesDetailComponent,
    resolve: {
      paymentJobAttributes: PaymentJobAttributesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentJobAttributesUpdateComponent,
    resolve: {
      paymentJobAttributes: PaymentJobAttributesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentJobAttributesUpdateComponent,
    resolve: {
      paymentJobAttributes: PaymentJobAttributesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(paymentJobAttributesRoute)],
  exports: [RouterModule],
})
export class PaymentJobAttributesRoutingModule {}
