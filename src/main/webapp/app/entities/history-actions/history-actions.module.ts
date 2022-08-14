import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HistoryActionsComponent } from './list/history-actions.component';
import { HistoryActionsDetailComponent } from './detail/history-actions-detail.component';
import { HistoryActionsUpdateComponent } from './update/history-actions-update.component';
import { HistoryActionsDeleteDialogComponent } from './delete/history-actions-delete-dialog.component';
import { HistoryActionsRoutingModule } from './route/history-actions-routing.module';

@NgModule({
  imports: [SharedModule, HistoryActionsRoutingModule],
  declarations: [
    HistoryActionsComponent,
    HistoryActionsDetailComponent,
    HistoryActionsUpdateComponent,
    HistoryActionsDeleteDialogComponent,
  ],
  entryComponents: [HistoryActionsDeleteDialogComponent],
})
export class HistoryActionsModule {}
