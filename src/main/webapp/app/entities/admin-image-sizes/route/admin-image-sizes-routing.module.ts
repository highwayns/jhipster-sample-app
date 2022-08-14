import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdminImageSizesComponent } from '../list/admin-image-sizes.component';
import { AdminImageSizesDetailComponent } from '../detail/admin-image-sizes-detail.component';
import { AdminImageSizesUpdateComponent } from '../update/admin-image-sizes-update.component';
import { AdminImageSizesRoutingResolveService } from './admin-image-sizes-routing-resolve.service';

const adminImageSizesRoute: Routes = [
  {
    path: '',
    component: AdminImageSizesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdminImageSizesDetailComponent,
    resolve: {
      adminImageSizes: AdminImageSizesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdminImageSizesUpdateComponent,
    resolve: {
      adminImageSizes: AdminImageSizesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdminImageSizesUpdateComponent,
    resolve: {
      adminImageSizes: AdminImageSizesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(adminImageSizesRoute)],
  exports: [RouterModule],
})
export class AdminImageSizesRoutingModule {}
