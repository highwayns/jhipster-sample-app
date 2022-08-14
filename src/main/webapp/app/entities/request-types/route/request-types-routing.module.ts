import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RequestTypesComponent } from '../list/request-types.component';
import { RequestTypesDetailComponent } from '../detail/request-types-detail.component';
import { RequestTypesUpdateComponent } from '../update/request-types-update.component';
import { RequestTypesRoutingResolveService } from './request-types-routing-resolve.service';

const requestTypesRoute: Routes = [
  {
    path: '',
    component: RequestTypesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RequestTypesDetailComponent,
    resolve: {
      requestTypes: RequestTypesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RequestTypesUpdateComponent,
    resolve: {
      requestTypes: RequestTypesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RequestTypesUpdateComponent,
    resolve: {
      requestTypes: RequestTypesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(requestTypesRoute)],
  exports: [RouterModule],
})
export class RequestTypesRoutingModule {}
