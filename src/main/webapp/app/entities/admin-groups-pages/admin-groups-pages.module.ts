import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AdminGroupsPagesComponent } from './list/admin-groups-pages.component';
import { AdminGroupsPagesDetailComponent } from './detail/admin-groups-pages-detail.component';
import { AdminGroupsPagesUpdateComponent } from './update/admin-groups-pages-update.component';
import { AdminGroupsPagesDeleteDialogComponent } from './delete/admin-groups-pages-delete-dialog.component';
import { AdminGroupsPagesRoutingModule } from './route/admin-groups-pages-routing.module';

@NgModule({
  imports: [SharedModule, AdminGroupsPagesRoutingModule],
  declarations: [
    AdminGroupsPagesComponent,
    AdminGroupsPagesDetailComponent,
    AdminGroupsPagesUpdateComponent,
    AdminGroupsPagesDeleteDialogComponent,
  ],
  entryComponents: [AdminGroupsPagesDeleteDialogComponent],
})
export class AdminGroupsPagesModule {}
