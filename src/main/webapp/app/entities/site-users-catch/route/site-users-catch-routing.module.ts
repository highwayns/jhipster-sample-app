import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SiteUsersCatchComponent } from '../list/site-users-catch.component';
import { SiteUsersCatchDetailComponent } from '../detail/site-users-catch-detail.component';
import { SiteUsersCatchUpdateComponent } from '../update/site-users-catch-update.component';
import { SiteUsersCatchRoutingResolveService } from './site-users-catch-routing-resolve.service';

const siteUsersCatchRoute: Routes = [
  {
    path: '',
    component: SiteUsersCatchComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SiteUsersCatchDetailComponent,
    resolve: {
      siteUsersCatch: SiteUsersCatchRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SiteUsersCatchUpdateComponent,
    resolve: {
      siteUsersCatch: SiteUsersCatchRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SiteUsersCatchUpdateComponent,
    resolve: {
      siteUsersCatch: SiteUsersCatchRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(siteUsersCatchRoute)],
  exports: [RouterModule],
})
export class SiteUsersCatchRoutingModule {}
