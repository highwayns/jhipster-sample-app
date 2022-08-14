import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BitcoindLogComponent } from '../list/bitcoind-log.component';
import { BitcoindLogDetailComponent } from '../detail/bitcoind-log-detail.component';
import { BitcoindLogUpdateComponent } from '../update/bitcoind-log-update.component';
import { BitcoindLogRoutingResolveService } from './bitcoind-log-routing-resolve.service';

const bitcoindLogRoute: Routes = [
  {
    path: '',
    component: BitcoindLogComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BitcoindLogDetailComponent,
    resolve: {
      bitcoindLog: BitcoindLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BitcoindLogUpdateComponent,
    resolve: {
      bitcoindLog: BitcoindLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BitcoindLogUpdateComponent,
    resolve: {
      bitcoindLog: BitcoindLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bitcoindLogRoute)],
  exports: [RouterModule],
})
export class BitcoindLogRoutingModule {}
