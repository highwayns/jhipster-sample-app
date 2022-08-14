import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AdminImageSizesComponent } from './list/admin-image-sizes.component';
import { AdminImageSizesDetailComponent } from './detail/admin-image-sizes-detail.component';
import { AdminImageSizesUpdateComponent } from './update/admin-image-sizes-update.component';
import { AdminImageSizesDeleteDialogComponent } from './delete/admin-image-sizes-delete-dialog.component';
import { AdminImageSizesRoutingModule } from './route/admin-image-sizes-routing.module';

@NgModule({
  imports: [SharedModule, AdminImageSizesRoutingModule],
  declarations: [
    AdminImageSizesComponent,
    AdminImageSizesDetailComponent,
    AdminImageSizesUpdateComponent,
    AdminImageSizesDeleteDialogComponent,
  ],
  entryComponents: [AdminImageSizesDeleteDialogComponent],
})
export class AdminImageSizesModule {}
