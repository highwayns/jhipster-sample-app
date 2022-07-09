import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IssuerComponent } from '../list/issuer.component';
import { IssuerDetailComponent } from '../detail/issuer-detail.component';
import { IssuerUpdateComponent } from '../update/issuer-update.component';
import { IssuerRoutingResolveService } from './issuer-routing-resolve.service';

const issuerRoute: Routes = [
  {
    path: '',
    component: IssuerComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IssuerDetailComponent,
    resolve: {
      issuer: IssuerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IssuerUpdateComponent,
    resolve: {
      issuer: IssuerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IssuerUpdateComponent,
    resolve: {
      issuer: IssuerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(issuerRoute)],
  exports: [RouterModule],
})
export class IssuerRoutingModule {}
