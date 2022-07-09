import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RefundStepComponent } from './list/refund-step.component';
import { RefundStepDetailComponent } from './detail/refund-step-detail.component';
import { RefundStepUpdateComponent } from './update/refund-step-update.component';
import { RefundStepDeleteDialogComponent } from './delete/refund-step-delete-dialog.component';
import { RefundStepRoutingModule } from './route/refund-step-routing.module';

@NgModule({
  imports: [SharedModule, RefundStepRoutingModule],
  declarations: [RefundStepComponent, RefundStepDetailComponent, RefundStepUpdateComponent, RefundStepDeleteDialogComponent],
  entryComponents: [RefundStepDeleteDialogComponent],
})
export class RefundStepModule {}
