import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ApiKeysComponent } from '../list/api-keys.component';
import { ApiKeysDetailComponent } from '../detail/api-keys-detail.component';
import { ApiKeysUpdateComponent } from '../update/api-keys-update.component';
import { ApiKeysRoutingResolveService } from './api-keys-routing-resolve.service';

const apiKeysRoute: Routes = [
  {
    path: '',
    component: ApiKeysComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ApiKeysDetailComponent,
    resolve: {
      apiKeys: ApiKeysRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ApiKeysUpdateComponent,
    resolve: {
      apiKeys: ApiKeysRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ApiKeysUpdateComponent,
    resolve: {
      apiKeys: ApiKeysRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(apiKeysRoute)],
  exports: [RouterModule],
})
export class ApiKeysRoutingModule {}
