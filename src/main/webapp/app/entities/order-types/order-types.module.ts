import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OrderTypesComponent } from './list/order-types.component';
import { OrderTypesDetailComponent } from './detail/order-types-detail.component';
import { OrderTypesUpdateComponent } from './update/order-types-update.component';
import { OrderTypesDeleteDialogComponent } from './delete/order-types-delete-dialog.component';
import { OrderTypesRoutingModule } from './route/order-types-routing.module';

@NgModule({
  imports: [SharedModule, OrderTypesRoutingModule],
  declarations: [OrderTypesComponent, OrderTypesDetailComponent, OrderTypesUpdateComponent, OrderTypesDeleteDialogComponent],
  entryComponents: [OrderTypesDeleteDialogComponent],
})
export class OrderTypesModule {}
