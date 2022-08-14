import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AdminPagesComponent } from './list/admin-pages.component';
import { AdminPagesDetailComponent } from './detail/admin-pages-detail.component';
import { AdminPagesUpdateComponent } from './update/admin-pages-update.component';
import { AdminPagesDeleteDialogComponent } from './delete/admin-pages-delete-dialog.component';
import { AdminPagesRoutingModule } from './route/admin-pages-routing.module';

@NgModule({
  imports: [SharedModule, AdminPagesRoutingModule],
  declarations: [AdminPagesComponent, AdminPagesDetailComponent, AdminPagesUpdateComponent, AdminPagesDeleteDialogComponent],
  entryComponents: [AdminPagesDeleteDialogComponent],
})
export class AdminPagesModule {}
