import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PaymentJobComponent } from './list/payment-job.component';
import { PaymentJobDetailComponent } from './detail/payment-job-detail.component';
import { PaymentJobUpdateComponent } from './update/payment-job-update.component';
import { PaymentJobDeleteDialogComponent } from './delete/payment-job-delete-dialog.component';
import { PaymentJobRoutingModule } from './route/payment-job-routing.module';

@NgModule({
  imports: [SharedModule, PaymentJobRoutingModule],
  declarations: [PaymentJobComponent, PaymentJobDetailComponent, PaymentJobUpdateComponent, PaymentJobDeleteDialogComponent],
  entryComponents: [PaymentJobDeleteDialogComponent],
})
export class PaymentJobModule {}
