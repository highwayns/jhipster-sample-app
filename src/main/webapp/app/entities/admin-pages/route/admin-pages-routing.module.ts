import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdminPagesComponent } from '../list/admin-pages.component';
import { AdminPagesDetailComponent } from '../detail/admin-pages-detail.component';
import { AdminPagesUpdateComponent } from '../update/admin-pages-update.component';
import { AdminPagesRoutingResolveService } from './admin-pages-routing-resolve.service';

const adminPagesRoute: Routes = [
  {
    path: '',
    component: AdminPagesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdminPagesDetailComponent,
    resolve: {
      adminPages: AdminPagesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdminPagesUpdateComponent,
    resolve: {
      adminPages: AdminPagesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdminPagesUpdateComponent,
    resolve: {
      adminPages: AdminPagesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(adminPagesRoute)],
  exports: [RouterModule],
})
export class AdminPagesRoutingModule {}
