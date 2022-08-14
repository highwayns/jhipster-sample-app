import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdminGroupsComponent } from '../list/admin-groups.component';
import { AdminGroupsDetailComponent } from '../detail/admin-groups-detail.component';
import { AdminGroupsUpdateComponent } from '../update/admin-groups-update.component';
import { AdminGroupsRoutingResolveService } from './admin-groups-routing-resolve.service';

const adminGroupsRoute: Routes = [
  {
    path: '',
    component: AdminGroupsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdminGroupsDetailComponent,
    resolve: {
      adminGroups: AdminGroupsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdminGroupsUpdateComponent,
    resolve: {
      adminGroups: AdminGroupsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdminGroupsUpdateComponent,
    resolve: {
      adminGroups: AdminGroupsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(adminGroupsRoute)],
  exports: [RouterModule],
})
export class AdminGroupsRoutingModule {}
