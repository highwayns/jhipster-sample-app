import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BankAccountsComponent } from '../list/bank-accounts.component';
import { BankAccountsDetailComponent } from '../detail/bank-accounts-detail.component';
import { BankAccountsUpdateComponent } from '../update/bank-accounts-update.component';
import { BankAccountsRoutingResolveService } from './bank-accounts-routing-resolve.service';

const bankAccountsRoute: Routes = [
  {
    path: '',
    component: BankAccountsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BankAccountsDetailComponent,
    resolve: {
      bankAccounts: BankAccountsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BankAccountsUpdateComponent,
    resolve: {
      bankAccounts: BankAccountsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BankAccountsUpdateComponent,
    resolve: {
      bankAccounts: BankAccountsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bankAccountsRoute)],
  exports: [RouterModule],
})
export class BankAccountsRoutingModule {}
