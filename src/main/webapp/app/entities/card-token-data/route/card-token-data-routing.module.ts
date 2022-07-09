import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CardTokenDataComponent } from '../list/card-token-data.component';
import { CardTokenDataDetailComponent } from '../detail/card-token-data-detail.component';
import { CardTokenDataUpdateComponent } from '../update/card-token-data-update.component';
import { CardTokenDataRoutingResolveService } from './card-token-data-routing-resolve.service';

const cardTokenDataRoute: Routes = [
  {
    path: '',
    component: CardTokenDataComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CardTokenDataDetailComponent,
    resolve: {
      cardTokenData: CardTokenDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CardTokenDataUpdateComponent,
    resolve: {
      cardTokenData: CardTokenDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CardTokenDataUpdateComponent,
    resolve: {
      cardTokenData: CardTokenDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cardTokenDataRoute)],
  exports: [RouterModule],
})
export class CardTokenDataRoutingModule {}
