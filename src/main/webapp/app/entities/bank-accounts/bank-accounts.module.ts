import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BankAccountsComponent } from './list/bank-accounts.component';
import { BankAccountsDetailComponent } from './detail/bank-accounts-detail.component';
import { BankAccountsUpdateComponent } from './update/bank-accounts-update.component';
import { BankAccountsDeleteDialogComponent } from './delete/bank-accounts-delete-dialog.component';
import { BankAccountsRoutingModule } from './route/bank-accounts-routing.module';

@NgModule({
  imports: [SharedModule, BankAccountsRoutingModule],
  declarations: [BankAccountsComponent, BankAccountsDetailComponent, BankAccountsUpdateComponent, BankAccountsDeleteDialogComponent],
  entryComponents: [BankAccountsDeleteDialogComponent],
})
export class BankAccountsModule {}
