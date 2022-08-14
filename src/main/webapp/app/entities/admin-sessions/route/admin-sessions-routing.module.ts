import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdminSessionsComponent } from '../list/admin-sessions.component';
import { AdminSessionsDetailComponent } from '../detail/admin-sessions-detail.component';
import { AdminSessionsUpdateComponent } from '../update/admin-sessions-update.component';
import { AdminSessionsRoutingResolveService } from './admin-sessions-routing-resolve.service';

const adminSessionsRoute: Routes = [
  {
    path: '',
    component: AdminSessionsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdminSessionsDetailComponent,
    resolve: {
      adminSessions: AdminSessionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdminSessionsUpdateComponent,
    resolve: {
      adminSessions: AdminSessionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdminSessionsUpdateComponent,
    resolve: {
      adminSessions: AdminSessionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(adminSessionsRoute)],
  exports: [RouterModule],
})
export class AdminSessionsRoutingModule {}
