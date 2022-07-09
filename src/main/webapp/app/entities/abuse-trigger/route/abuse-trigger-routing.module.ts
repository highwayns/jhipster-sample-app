import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AbuseTriggerComponent } from '../list/abuse-trigger.component';
import { AbuseTriggerDetailComponent } from '../detail/abuse-trigger-detail.component';
import { AbuseTriggerUpdateComponent } from '../update/abuse-trigger-update.component';
import { AbuseTriggerRoutingResolveService } from './abuse-trigger-routing-resolve.service';

const abuseTriggerRoute: Routes = [
  {
    path: '',
    component: AbuseTriggerComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AbuseTriggerDetailComponent,
    resolve: {
      abuseTrigger: AbuseTriggerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AbuseTriggerUpdateComponent,
    resolve: {
      abuseTrigger: AbuseTriggerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AbuseTriggerUpdateComponent,
    resolve: {
      abuseTrigger: AbuseTriggerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(abuseTriggerRoute)],
  exports: [RouterModule],
})
export class AbuseTriggerRoutingModule {}
