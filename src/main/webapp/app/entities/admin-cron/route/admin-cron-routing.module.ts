import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdminCronComponent } from '../list/admin-cron.component';
import { AdminCronDetailComponent } from '../detail/admin-cron-detail.component';
import { AdminCronUpdateComponent } from '../update/admin-cron-update.component';
import { AdminCronRoutingResolveService } from './admin-cron-routing-resolve.service';

const adminCronRoute: Routes = [
  {
    path: '',
    component: AdminCronComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdminCronDetailComponent,
    resolve: {
      adminCron: AdminCronRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdminCronUpdateComponent,
    resolve: {
      adminCron: AdminCronRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdminCronUpdateComponent,
    resolve: {
      adminCron: AdminCronRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(adminCronRoute)],
  exports: [RouterModule],
})
export class AdminCronRoutingModule {}
