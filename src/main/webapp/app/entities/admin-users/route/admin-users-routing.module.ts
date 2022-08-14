import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdminUsersComponent } from '../list/admin-users.component';
import { AdminUsersDetailComponent } from '../detail/admin-users-detail.component';
import { AdminUsersUpdateComponent } from '../update/admin-users-update.component';
import { AdminUsersRoutingResolveService } from './admin-users-routing-resolve.service';

const adminUsersRoute: Routes = [
  {
    path: '',
    component: AdminUsersComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdminUsersDetailComponent,
    resolve: {
      adminUsers: AdminUsersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdminUsersUpdateComponent,
    resolve: {
      adminUsers: AdminUsersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdminUsersUpdateComponent,
    resolve: {
      adminUsers: AdminUsersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(adminUsersRoute)],
  exports: [RouterModule],
})
export class AdminUsersRoutingModule {}
