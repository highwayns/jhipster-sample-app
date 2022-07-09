import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PaymentJobAttributesComponent } from './list/payment-job-attributes.component';
import { PaymentJobAttributesDetailComponent } from './detail/payment-job-attributes-detail.component';
import { PaymentJobAttributesUpdateComponent } from './update/payment-job-attributes-update.component';
import { PaymentJobAttributesDeleteDialogComponent } from './delete/payment-job-attributes-delete-dialog.component';
import { PaymentJobAttributesRoutingModule } from './route/payment-job-attributes-routing.module';

@NgModule({
  imports: [SharedModule, PaymentJobAttributesRoutingModule],
  declarations: [
    PaymentJobAttributesComponent,
    PaymentJobAttributesDetailComponent,
    PaymentJobAttributesUpdateComponent,
    PaymentJobAttributesDeleteDialogComponent,
  ],
  entryComponents: [PaymentJobAttributesDeleteDialogComponent],
})
export class PaymentJobAttributesModule {}
