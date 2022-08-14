import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SiteUsersComponent } from '../list/site-users.component';
import { SiteUsersDetailComponent } from '../detail/site-users-detail.component';
import { SiteUsersUpdateComponent } from '../update/site-users-update.component';
import { SiteUsersRoutingResolveService } from './site-users-routing-resolve.service';

const siteUsersRoute: Routes = [
  {
    path: '',
    component: SiteUsersComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SiteUsersDetailComponent,
    resolve: {
      siteUsers: SiteUsersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SiteUsersUpdateComponent,
    resolve: {
      siteUsers: SiteUsersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SiteUsersUpdateComponent,
    resolve: {
      siteUsers: SiteUsersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(siteUsersRoute)],
  exports: [RouterModule],
})
export class SiteUsersRoutingModule {}
