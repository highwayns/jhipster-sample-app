import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RecurrenceCriteriaComponent } from '../list/recurrence-criteria.component';
import { RecurrenceCriteriaDetailComponent } from '../detail/recurrence-criteria-detail.component';
import { RecurrenceCriteriaUpdateComponent } from '../update/recurrence-criteria-update.component';
import { RecurrenceCriteriaRoutingResolveService } from './recurrence-criteria-routing-resolve.service';

const recurrenceCriteriaRoute: Routes = [
  {
    path: '',
    component: RecurrenceCriteriaComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RecurrenceCriteriaDetailComponent,
    resolve: {
      recurrenceCriteria: RecurrenceCriteriaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RecurrenceCriteriaUpdateComponent,
    resolve: {
      recurrenceCriteria: RecurrenceCriteriaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RecurrenceCriteriaUpdateComponent,
    resolve: {
      recurrenceCriteria: RecurrenceCriteriaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(recurrenceCriteriaRoute)],
  exports: [RouterModule],
})
export class RecurrenceCriteriaRoutingModule {}
