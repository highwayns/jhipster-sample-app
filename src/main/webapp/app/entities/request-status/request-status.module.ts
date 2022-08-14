import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RequestStatusComponent } from './list/request-status.component';
import { RequestStatusDetailComponent } from './detail/request-status-detail.component';
import { RequestStatusUpdateComponent } from './update/request-status-update.component';
import { RequestStatusDeleteDialogComponent } from './delete/request-status-delete-dialog.component';
import { RequestStatusRoutingModule } from './route/request-status-routing.module';

@NgModule({
  imports: [SharedModule, RequestStatusRoutingModule],
  declarations: [RequestStatusComponent, RequestStatusDetailComponent, RequestStatusUpdateComponent, RequestStatusDeleteDialogComponent],
  entryComponents: [RequestStatusDeleteDialogComponent],
})
export class RequestStatusModule {}
