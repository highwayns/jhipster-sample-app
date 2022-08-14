import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TransactionTypesComponent } from '../list/transaction-types.component';
import { TransactionTypesDetailComponent } from '../detail/transaction-types-detail.component';
import { TransactionTypesUpdateComponent } from '../update/transaction-types-update.component';
import { TransactionTypesRoutingResolveService } from './transaction-types-routing-resolve.service';

const transactionTypesRoute: Routes = [
  {
    path: '',
    component: TransactionTypesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TransactionTypesDetailComponent,
    resolve: {
      transactionTypes: TransactionTypesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TransactionTypesUpdateComponent,
    resolve: {
      transactionTypes: TransactionTypesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TransactionTypesUpdateComponent,
    resolve: {
      transactionTypes: TransactionTypesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(transactionTypesRoute)],
  exports: [RouterModule],
})
export class TransactionTypesRoutingModule {}
