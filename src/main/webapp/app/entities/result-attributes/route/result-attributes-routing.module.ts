import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResultAttributesComponent } from '../list/result-attributes.component';
import { ResultAttributesDetailComponent } from '../detail/result-attributes-detail.component';
import { ResultAttributesUpdateComponent } from '../update/result-attributes-update.component';
import { ResultAttributesRoutingResolveService } from './result-attributes-routing-resolve.service';

const resultAttributesRoute: Routes = [
  {
    path: '',
    component: ResultAttributesComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResultAttributesDetailComponent,
    resolve: {
      resultAttributes: ResultAttributesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResultAttributesUpdateComponent,
    resolve: {
      resultAttributes: ResultAttributesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResultAttributesUpdateComponent,
    resolve: {
      resultAttributes: ResultAttributesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(resultAttributesRoute)],
  exports: [RouterModule],
})
export class ResultAttributesRoutingModule {}
