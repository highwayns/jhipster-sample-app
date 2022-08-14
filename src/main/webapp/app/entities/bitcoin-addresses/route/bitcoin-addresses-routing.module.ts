import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BitcoinAddressesComponent } from '../list/bitcoin-addresses.component';
import { BitcoinAddressesDetailComponent } from '../detail/bitcoin-addresses-detail.component';
import { BitcoinAddressesUpdateComponent } from '../update/bitcoin-addresses-update.component';
import { BitcoinAddressesRoutingResolveService } from './bitcoin-addresses-routing-resolve.service';

const bitcoinAddressesRoute: Routes = [
  {
    path: '',
    component: BitcoinAddressesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BitcoinAddressesDetailComponent,
    resolve: {
      bitcoinAddresses: BitcoinAddressesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BitcoinAddressesUpdateComponent,
    resolve: {
      bitcoinAddresses: BitcoinAddressesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BitcoinAddressesUpdateComponent,
    resolve: {
      bitcoinAddresses: BitcoinAddressesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bitcoinAddressesRoute)],
  exports: [RouterModule],
})
export class BitcoinAddressesRoutingModule {}
