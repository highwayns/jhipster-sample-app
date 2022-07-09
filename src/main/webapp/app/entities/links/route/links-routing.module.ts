import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LinksComponent } from '../list/links.component';
import { LinksDetailComponent } from '../detail/links-detail.component';
import { LinksUpdateComponent } from '../update/links-update.component';
import { LinksRoutingResolveService } from './links-routing-resolve.service';

const linksRoute: Routes = [
  {
    path: '',
    component: LinksComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LinksDetailComponent,
    resolve: {
      links: LinksRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LinksUpdateComponent,
    resolve: {
      links: LinksRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LinksUpdateComponent,
    resolve: {
      links: LinksRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(linksRoute)],
  exports: [RouterModule],
})
export class LinksRoutingModule {}
