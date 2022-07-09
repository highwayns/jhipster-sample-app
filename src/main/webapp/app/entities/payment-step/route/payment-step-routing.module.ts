import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PaymentStepComponent } from '../list/payment-step.component';
import { PaymentStepDetailComponent } from '../detail/payment-step-detail.component';
import { PaymentStepUpdateComponent } from '../update/payment-step-update.component';
import { PaymentStepRoutingResolveService } from './payment-step-routing-resolve.service';

const paymentStepRoute: Routes = [
  {
    path: '',
    component: PaymentStepComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentStepDetailComponent,
    resolve: {
      paymentStep: PaymentStepRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentStepUpdateComponent,
    resolve: {
      paymentStep: PaymentStepRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentStepUpdateComponent,
    resolve: {
      paymentStep: PaymentStepRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(paymentStepRoute)],
  exports: [RouterModule],
})
export class PaymentStepRoutingModule {}
