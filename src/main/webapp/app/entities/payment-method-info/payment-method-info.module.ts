import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PaymentMethodInfoComponent } from './list/payment-method-info.component';
import { PaymentMethodInfoDetailComponent } from './detail/payment-method-info-detail.component';
import { PaymentMethodInfoUpdateComponent } from './update/payment-method-info-update.component';
import { PaymentMethodInfoDeleteDialogComponent } from './delete/payment-method-info-delete-dialog.component';
import { PaymentMethodInfoRoutingModule } from './route/payment-method-info-routing.module';

@NgModule({
  imports: [SharedModule, PaymentMethodInfoRoutingModule],
  declarations: [
    PaymentMethodInfoComponent,
    PaymentMethodInfoDetailComponent,
    PaymentMethodInfoUpdateComponent,
    PaymentMethodInfoDeleteDialogComponent,
  ],
  entryComponents: [PaymentMethodInfoDeleteDialogComponent],
})
export class PaymentMethodInfoModule {}
