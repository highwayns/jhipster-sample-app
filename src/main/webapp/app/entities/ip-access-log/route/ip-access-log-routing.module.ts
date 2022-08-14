import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IpAccessLogComponent } from '../list/ip-access-log.component';
import { IpAccessLogDetailComponent } from '../detail/ip-access-log-detail.component';
import { IpAccessLogUpdateComponent } from '../update/ip-access-log-update.component';
import { IpAccessLogRoutingResolveService } from './ip-access-log-routing-resolve.service';

const ipAccessLogRoute: Routes = [
  {
    path: '',
    component: IpAccessLogComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IpAccessLogDetailComponent,
    resolve: {
      ipAccessLog: IpAccessLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IpAccessLogUpdateComponent,
    resolve: {
      ipAccessLog: IpAccessLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IpAccessLogUpdateComponent,
    resolve: {
      ipAccessLog: IpAccessLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ipAccessLogRoute)],
  exports: [RouterModule],
})
export class IpAccessLogRoutingModule {}
