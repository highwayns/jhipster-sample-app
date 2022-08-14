import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StatusEscrowsComponent } from '../list/status-escrows.component';
import { StatusEscrowsDetailComponent } from '../detail/status-escrows-detail.component';
import { StatusEscrowsUpdateComponent } from '../update/status-escrows-update.component';
import { StatusEscrowsRoutingResolveService } from './status-escrows-routing-resolve.service';

const statusEscrowsRoute: Routes = [
  {
    path: '',
    component: StatusEscrowsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StatusEscrowsDetailComponent,
    resolve: {
      statusEscrows: StatusEscrowsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StatusEscrowsUpdateComponent,
    resolve: {
      statusEscrows: StatusEscrowsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StatusEscrowsUpdateComponent,
    resolve: {
      statusEscrows: StatusEscrowsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(statusEscrowsRoute)],
  exports: [RouterModule],
})
export class StatusEscrowsRoutingModule {}
