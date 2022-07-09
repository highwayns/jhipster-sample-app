import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RefundStepComponent } from '../list/refund-step.component';
import { RefundStepDetailComponent } from '../detail/refund-step-detail.component';
import { RefundStepUpdateComponent } from '../update/refund-step-update.component';
import { RefundStepRoutingResolveService } from './refund-step-routing-resolve.service';

const refundStepRoute: Routes = [
  {
    path: '',
    component: RefundStepComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RefundStepDetailComponent,
    resolve: {
      refundStep: RefundStepRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RefundStepUpdateComponent,
    resolve: {
      refundStep: RefundStepRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RefundStepUpdateComponent,
    resolve: {
      refundStep: RefundStepRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(refundStepRoute)],
  exports: [RouterModule],
})
export class RefundStepRoutingModule {}
