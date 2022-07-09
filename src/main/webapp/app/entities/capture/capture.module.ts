import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CaptureComponent } from './list/capture.component';
import { CaptureDetailComponent } from './detail/capture-detail.component';
import { CaptureUpdateComponent } from './update/capture-update.component';
import { CaptureDeleteDialogComponent } from './delete/capture-delete-dialog.component';
import { CaptureRoutingModule } from './route/capture-routing.module';

@NgModule({
  imports: [SharedModule, CaptureRoutingModule],
  declarations: [CaptureComponent, CaptureDetailComponent, CaptureUpdateComponent, CaptureDeleteDialogComponent],
  entryComponents: [CaptureDeleteDialogComponent],
})
export class CaptureModule {}
