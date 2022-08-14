import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ConversionsComponent } from '../list/conversions.component';
import { ConversionsDetailComponent } from '../detail/conversions-detail.component';
import { ConversionsUpdateComponent } from '../update/conversions-update.component';
import { ConversionsRoutingResolveService } from './conversions-routing-resolve.service';

const conversionsRoute: Routes = [
  {
    path: '',
    component: ConversionsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConversionsDetailComponent,
    resolve: {
      conversions: ConversionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConversionsUpdateComponent,
    resolve: {
      conversions: ConversionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConversionsUpdateComponent,
    resolve: {
      conversions: ConversionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(conversionsRoute)],
  exports: [RouterModule],
})
export class ConversionsRoutingModule {}
