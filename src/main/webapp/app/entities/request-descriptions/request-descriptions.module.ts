import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RequestDescriptionsComponent } from './list/request-descriptions.component';
import { RequestDescriptionsDetailComponent } from './detail/request-descriptions-detail.component';
import { RequestDescriptionsUpdateComponent } from './update/request-descriptions-update.component';
import { RequestDescriptionsDeleteDialogComponent } from './delete/request-descriptions-delete-dialog.component';
import { RequestDescriptionsRoutingModule } from './route/request-descriptions-routing.module';

@NgModule({
  imports: [SharedModule, RequestDescriptionsRoutingModule],
  declarations: [
    RequestDescriptionsComponent,
    RequestDescriptionsDetailComponent,
    RequestDescriptionsUpdateComponent,
    RequestDescriptionsDeleteDialogComponent,
  ],
  entryComponents: [RequestDescriptionsDeleteDialogComponent],
})
export class RequestDescriptionsModule {}
