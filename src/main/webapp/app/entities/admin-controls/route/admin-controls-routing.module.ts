import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdminControlsComponent } from '../list/admin-controls.component';
import { AdminControlsDetailComponent } from '../detail/admin-controls-detail.component';
import { AdminControlsUpdateComponent } from '../update/admin-controls-update.component';
import { AdminControlsRoutingResolveService } from './admin-controls-routing-resolve.service';

const adminControlsRoute: Routes = [
  {
    path: '',
    component: AdminControlsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdminControlsDetailComponent,
    resolve: {
      adminControls: AdminControlsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdminControlsUpdateComponent,
    resolve: {
      adminControls: AdminControlsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdminControlsUpdateComponent,
    resolve: {
      adminControls: AdminControlsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(adminControlsRoute)],
  exports: [RouterModule],
})
export class AdminControlsRoutingModule {}
