import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdminGroupsPagesComponent } from '../list/admin-groups-pages.component';
import { AdminGroupsPagesDetailComponent } from '../detail/admin-groups-pages-detail.component';
import { AdminGroupsPagesUpdateComponent } from '../update/admin-groups-pages-update.component';
import { AdminGroupsPagesRoutingResolveService } from './admin-groups-pages-routing-resolve.service';

const adminGroupsPagesRoute: Routes = [
  {
    path: '',
    component: AdminGroupsPagesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdminGroupsPagesDetailComponent,
    resolve: {
      adminGroupsPages: AdminGroupsPagesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdminGroupsPagesUpdateComponent,
    resolve: {
      adminGroupsPages: AdminGroupsPagesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdminGroupsPagesUpdateComponent,
    resolve: {
      adminGroupsPages: AdminGroupsPagesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(adminGroupsPagesRoute)],
  exports: [RouterModule],
})
export class AdminGroupsPagesRoutingModule {}
