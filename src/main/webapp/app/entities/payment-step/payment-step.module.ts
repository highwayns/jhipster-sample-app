import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PaymentStepComponent } from './list/payment-step.component';
import { PaymentStepDetailComponent } from './detail/payment-step-detail.component';
import { PaymentStepUpdateComponent } from './update/payment-step-update.component';
import { PaymentStepDeleteDialogComponent } from './delete/payment-step-delete-dialog.component';
import { PaymentStepRoutingModule } from './route/payment-step-routing.module';

@NgModule({
  imports: [SharedModule, PaymentStepRoutingModule],
  declarations: [PaymentStepComponent, PaymentStepDetailComponent, PaymentStepUpdateComponent, PaymentStepDeleteDialogComponent],
  entryComponents: [PaymentStepDeleteDialogComponent],
})
export class PaymentStepModule {}
