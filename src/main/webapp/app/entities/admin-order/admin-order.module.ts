import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AdminOrderComponent } from './list/admin-order.component';
import { AdminOrderDetailComponent } from './detail/admin-order-detail.component';
import { AdminOrderUpdateComponent } from './update/admin-order-update.component';
import { AdminOrderDeleteDialogComponent } from './delete/admin-order-delete-dialog.component';
import { AdminOrderRoutingModule } from './route/admin-order-routing.module';

@NgModule({
  imports: [SharedModule, AdminOrderRoutingModule],
  declarations: [AdminOrderComponent, AdminOrderDetailComponent, AdminOrderUpdateComponent, AdminOrderDeleteDialogComponent],
  entryComponents: [AdminOrderDeleteDialogComponent],
})
export class AdminOrderModule {}
