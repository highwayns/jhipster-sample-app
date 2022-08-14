import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdminGroupsTabsComponent } from '../list/admin-groups-tabs.component';
import { AdminGroupsTabsDetailComponent } from '../detail/admin-groups-tabs-detail.component';
import { AdminGroupsTabsUpdateComponent } from '../update/admin-groups-tabs-update.component';
import { AdminGroupsTabsRoutingResolveService } from './admin-groups-tabs-routing-resolve.service';

const adminGroupsTabsRoute: Routes = [
  {
    path: '',
    component: AdminGroupsTabsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdminGroupsTabsDetailComponent,
    resolve: {
      adminGroupsTabs: AdminGroupsTabsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdminGroupsTabsUpdateComponent,
    resolve: {
      adminGroupsTabs: AdminGroupsTabsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdminGroupsTabsUpdateComponent,
    resolve: {
      adminGroupsTabs: AdminGroupsTabsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(adminGroupsTabsRoute)],
  exports: [RouterModule],
})
export class AdminGroupsTabsRoutingModule {}
