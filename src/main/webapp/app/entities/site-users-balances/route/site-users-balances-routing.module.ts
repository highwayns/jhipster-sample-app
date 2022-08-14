import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SiteUsersBalancesComponent } from '../list/site-users-balances.component';
import { SiteUsersBalancesDetailComponent } from '../detail/site-users-balances-detail.component';
import { SiteUsersBalancesUpdateComponent } from '../update/site-users-balances-update.component';
import { SiteUsersBalancesRoutingResolveService } from './site-users-balances-routing-resolve.service';

const siteUsersBalancesRoute: Routes = [
  {
    path: '',
    component: SiteUsersBalancesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SiteUsersBalancesDetailComponent,
    resolve: {
      siteUsersBalances: SiteUsersBalancesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SiteUsersBalancesUpdateComponent,
    resolve: {
      siteUsersBalances: SiteUsersBalancesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SiteUsersBalancesUpdateComponent,
    resolve: {
      siteUsersBalances: SiteUsersBalancesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(siteUsersBalancesRoute)],
  exports: [RouterModule],
})
export class SiteUsersBalancesRoutingModule {}
