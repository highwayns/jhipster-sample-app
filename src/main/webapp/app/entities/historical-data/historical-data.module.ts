import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HistoricalDataComponent } from './list/historical-data.component';
import { HistoricalDataDetailComponent } from './detail/historical-data-detail.component';
import { HistoricalDataUpdateComponent } from './update/historical-data-update.component';
import { HistoricalDataDeleteDialogComponent } from './delete/historical-data-delete-dialog.component';
import { HistoricalDataRoutingModule } from './route/historical-data-routing.module';

@NgModule({
  imports: [SharedModule, HistoricalDataRoutingModule],
  declarations: [
    HistoricalDataComponent,
    HistoricalDataDetailComponent,
    HistoricalDataUpdateComponent,
    HistoricalDataDeleteDialogComponent,
  ],
  entryComponents: [HistoricalDataDeleteDialogComponent],
})
export class HistoricalDataModule {}
