import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SiteUsersAccessComponent } from '../list/site-users-access.component';
import { SiteUsersAccessDetailComponent } from '../detail/site-users-access-detail.component';
import { SiteUsersAccessUpdateComponent } from '../update/site-users-access-update.component';
import { SiteUsersAccessRoutingResolveService } from './site-users-access-routing-resolve.service';

const siteUsersAccessRoute: Routes = [
  {
    path: '',
    component: SiteUsersAccessComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SiteUsersAccessDetailComponent,
    resolve: {
      siteUsersAccess: SiteUsersAccessRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SiteUsersAccessUpdateComponent,
    resolve: {
      siteUsersAccess: SiteUsersAccessRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SiteUsersAccessUpdateComponent,
    resolve: {
      siteUsersAccess: SiteUsersAccessRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(siteUsersAccessRoute)],
  exports: [RouterModule],
})
export class SiteUsersAccessRoutingModule {}
