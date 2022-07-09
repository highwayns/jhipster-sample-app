import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CurrencysComponent } from '../list/currencys.component';
import { CurrencysDetailComponent } from '../detail/currencys-detail.component';
import { CurrencysUpdateComponent } from '../update/currencys-update.component';
import { CurrencysRoutingResolveService } from './currencys-routing-resolve.service';

const currencysRoute: Routes = [
  {
    path: '',
    component: CurrencysComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CurrencysDetailComponent,
    resolve: {
      currencys: CurrencysRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CurrencysUpdateComponent,
    resolve: {
      currencys: CurrencysRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CurrencysUpdateComponent,
    resolve: {
      currencys: CurrencysRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(currencysRoute)],
  exports: [RouterModule],
})
export class CurrencysRoutingModule {}
