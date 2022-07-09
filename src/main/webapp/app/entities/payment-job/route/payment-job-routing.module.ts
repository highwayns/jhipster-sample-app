import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PaymentJobComponent } from '../list/payment-job.component';
import { PaymentJobDetailComponent } from '../detail/payment-job-detail.component';
import { PaymentJobUpdateComponent } from '../update/payment-job-update.component';
import { PaymentJobRoutingResolveService } from './payment-job-routing-resolve.service';

const paymentJobRoute: Routes = [
  {
    path: '',
    component: PaymentJobComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentJobDetailComponent,
    resolve: {
      paymentJob: PaymentJobRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentJobUpdateComponent,
    resolve: {
      paymentJob: PaymentJobRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentJobUpdateComponent,
    resolve: {
      paymentJob: PaymentJobRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(paymentJobRoute)],
  exports: [RouterModule],
})
export class PaymentJobRoutingModule {}
