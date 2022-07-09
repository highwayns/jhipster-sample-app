import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RecurrenceCriteriaComponent } from './list/recurrence-criteria.component';
import { RecurrenceCriteriaDetailComponent } from './detail/recurrence-criteria-detail.component';
import { RecurrenceCriteriaUpdateComponent } from './update/recurrence-criteria-update.component';
import { RecurrenceCriteriaDeleteDialogComponent } from './delete/recurrence-criteria-delete-dialog.component';
import { RecurrenceCriteriaRoutingModule } from './route/recurrence-criteria-routing.module';

@NgModule({
  imports: [SharedModule, RecurrenceCriteriaRoutingModule],
  declarations: [
    RecurrenceCriteriaComponent,
    RecurrenceCriteriaDetailComponent,
    RecurrenceCriteriaUpdateComponent,
    RecurrenceCriteriaDeleteDialogComponent,
  ],
  entryComponents: [RecurrenceCriteriaDeleteDialogComponent],
})
export class RecurrenceCriteriaModule {}
