import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdminTabsComponent } from '../list/admin-tabs.component';
import { AdminTabsDetailComponent } from '../detail/admin-tabs-detail.component';
import { AdminTabsUpdateComponent } from '../update/admin-tabs-update.component';
import { AdminTabsRoutingResolveService } from './admin-tabs-routing-resolve.service';

const adminTabsRoute: Routes = [
  {
    path: '',
    component: AdminTabsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdminTabsDetailComponent,
    resolve: {
      adminTabs: AdminTabsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdminTabsUpdateComponent,
    resolve: {
      adminTabs: AdminTabsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdminTabsUpdateComponent,
    resolve: {
      adminTabs: AdminTabsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(adminTabsRoute)],
  exports: [RouterModule],
})
export class AdminTabsRoutingModule {}
