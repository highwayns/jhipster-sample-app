import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TokenisedCardComponent } from '../list/tokenised-card.component';
import { TokenisedCardDetailComponent } from '../detail/tokenised-card-detail.component';
import { TokenisedCardUpdateComponent } from '../update/tokenised-card-update.component';
import { TokenisedCardRoutingResolveService } from './tokenised-card-routing-resolve.service';

const tokenisedCardRoute: Routes = [
  {
    path: '',
    component: TokenisedCardComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TokenisedCardDetailComponent,
    resolve: {
      tokenisedCard: TokenisedCardRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TokenisedCardUpdateComponent,
    resolve: {
      tokenisedCard: TokenisedCardRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TokenisedCardUpdateComponent,
    resolve: {
      tokenisedCard: TokenisedCardRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tokenisedCardRoute)],
  exports: [RouterModule],
})
export class TokenisedCardRoutingModule {}
