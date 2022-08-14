import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TransactionTypesComponent } from './list/transaction-types.component';
import { TransactionTypesDetailComponent } from './detail/transaction-types-detail.component';
import { TransactionTypesUpdateComponent } from './update/transaction-types-update.component';
import { TransactionTypesDeleteDialogComponent } from './delete/transaction-types-delete-dialog.component';
import { TransactionTypesRoutingModule } from './route/transaction-types-routing.module';

@NgModule({
  imports: [SharedModule, TransactionTypesRoutingModule],
  declarations: [
    TransactionTypesComponent,
    TransactionTypesDetailComponent,
    TransactionTypesUpdateComponent,
    TransactionTypesDeleteDialogComponent,
  ],
  entryComponents: [TransactionTypesDeleteDialogComponent],
})
export class TransactionTypesModule {}
