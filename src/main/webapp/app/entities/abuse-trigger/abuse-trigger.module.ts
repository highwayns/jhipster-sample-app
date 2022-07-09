import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AbuseTriggerComponent } from './list/abuse-trigger.component';
import { AbuseTriggerDetailComponent } from './detail/abuse-trigger-detail.component';
import { AbuseTriggerUpdateComponent } from './update/abuse-trigger-update.component';
import { AbuseTriggerDeleteDialogComponent } from './delete/abuse-trigger-delete-dialog.component';
import { AbuseTriggerRoutingModule } from './route/abuse-trigger-routing.module';

@NgModule({
  imports: [SharedModule, AbuseTriggerRoutingModule],
  declarations: [AbuseTriggerComponent, AbuseTriggerDetailComponent, AbuseTriggerUpdateComponent, AbuseTriggerDeleteDialogComponent],
  entryComponents: [AbuseTriggerDeleteDialogComponent],
})
export class AbuseTriggerModule {}
