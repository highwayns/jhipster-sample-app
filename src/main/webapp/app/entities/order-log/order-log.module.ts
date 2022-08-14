import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OrderLogComponent } from './list/order-log.component';
import { OrderLogDetailComponent } from './detail/order-log-detail.component';
import { OrderLogUpdateComponent } from './update/order-log-update.component';
import { OrderLogDeleteDialogComponent } from './delete/order-log-delete-dialog.component';
import { OrderLogRoutingModule } from './route/order-log-routing.module';

@NgModule({
  imports: [SharedModule, OrderLogRoutingModule],
  declarations: [OrderLogComponent, OrderLogDetailComponent, OrderLogUpdateComponent, OrderLogDeleteDialogComponent],
  entryComponents: [OrderLogDeleteDialogComponent],
})
export class OrderLogModule {}
