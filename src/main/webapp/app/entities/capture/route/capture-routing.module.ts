import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CaptureComponent } from '../list/capture.component';
import { CaptureDetailComponent } from '../detail/capture-detail.component';
import { CaptureUpdateComponent } from '../update/capture-update.component';
import { CaptureRoutingResolveService } from './capture-routing-resolve.service';

const captureRoute: Routes = [
  {
    path: '',
    component: CaptureComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CaptureDetailComponent,
    resolve: {
      capture: CaptureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CaptureUpdateComponent,
    resolve: {
      capture: CaptureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CaptureUpdateComponent,
    resolve: {
      capture: CaptureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(captureRoute)],
  exports: [RouterModule],
})
export class CaptureRoutingModule {}
