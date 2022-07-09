import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PaymentMethodInfoComponent } from '../list/payment-method-info.component';
import { PaymentMethodInfoDetailComponent } from '../detail/payment-method-info-detail.component';
import { PaymentMethodInfoUpdateComponent } from '../update/payment-method-info-update.component';
import { PaymentMethodInfoRoutingResolveService } from './payment-method-info-routing-resolve.service';

const paymentMethodInfoRoute: Routes = [
  {
    path: '',
    component: PaymentMethodInfoComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentMethodInfoDetailComponent,
    resolve: {
      paymentMethodInfo: PaymentMethodInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentMethodInfoUpdateComponent,
    resolve: {
      paymentMethodInfo: PaymentMethodInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentMethodInfoUpdateComponent,
    resolve: {
      paymentMethodInfo: PaymentMethodInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(paymentMethodInfoRoute)],
  exports: [RouterModule],
})
export class PaymentMethodInfoRoutingModule {}
