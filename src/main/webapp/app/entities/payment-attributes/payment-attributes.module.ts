import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PaymentAttributesComponent } from './list/payment-attributes.component';
import { PaymentAttributesDetailComponent } from './detail/payment-attributes-detail.component';
import { PaymentAttributesUpdateComponent } from './update/payment-attributes-update.component';
import { PaymentAttributesDeleteDialogComponent } from './delete/payment-attributes-delete-dialog.component';
import { PaymentAttributesRoutingModule } from './route/payment-attributes-routing.module';

@NgModule({
  imports: [SharedModule, PaymentAttributesRoutingModule],
  declarations: [
    PaymentAttributesComponent,
    PaymentAttributesDetailComponent,
    PaymentAttributesUpdateComponent,
    PaymentAttributesDeleteDialogComponent,
  ],
  entryComponents: [PaymentAttributesDeleteDialogComponent],
})
export class PaymentAttributesModule {}
