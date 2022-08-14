import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RequestTypesComponent } from './list/request-types.component';
import { RequestTypesDetailComponent } from './detail/request-types-detail.component';
import { RequestTypesUpdateComponent } from './update/request-types-update.component';
import { RequestTypesDeleteDialogComponent } from './delete/request-types-delete-dialog.component';
import { RequestTypesRoutingModule } from './route/request-types-routing.module';

@NgModule({
  imports: [SharedModule, RequestTypesRoutingModule],
  declarations: [RequestTypesComponent, RequestTypesDetailComponent, RequestTypesUpdateComponent, RequestTypesDeleteDialogComponent],
  entryComponents: [RequestTypesDeleteDialogComponent],
})
export class RequestTypesModule {}
