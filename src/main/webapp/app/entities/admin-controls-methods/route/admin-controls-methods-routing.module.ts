import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdminControlsMethodsComponent } from '../list/admin-controls-methods.component';
import { AdminControlsMethodsDetailComponent } from '../detail/admin-controls-methods-detail.component';
import { AdminControlsMethodsUpdateComponent } from '../update/admin-controls-methods-update.component';
import { AdminControlsMethodsRoutingResolveService } from './admin-controls-methods-routing-resolve.service';

const adminControlsMethodsRoute: Routes = [
  {
    path: '',
    component: AdminControlsMethodsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdminControlsMethodsDetailComponent,
    resolve: {
      adminControlsMethods: AdminControlsMethodsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdminControlsMethodsUpdateComponent,
    resolve: {
      adminControlsMethods: AdminControlsMethodsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdminControlsMethodsUpdateComponent,
    resolve: {
      adminControlsMethods: AdminControlsMethodsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(adminControlsMethodsRoute)],
  exports: [RouterModule],
})
export class AdminControlsMethodsRoutingModule {}
