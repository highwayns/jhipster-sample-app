import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SiteUsersAccessComponent } from './list/site-users-access.component';
import { SiteUsersAccessDetailComponent } from './detail/site-users-access-detail.component';
import { SiteUsersAccessUpdateComponent } from './update/site-users-access-update.component';
import { SiteUsersAccessDeleteDialogComponent } from './delete/site-users-access-delete-dialog.component';
import { SiteUsersAccessRoutingModule } from './route/site-users-access-routing.module';

@NgModule({
  imports: [SharedModule, SiteUsersAccessRoutingModule],
  declarations: [
    SiteUsersAccessComponent,
    SiteUsersAccessDetailComponent,
    SiteUsersAccessUpdateComponent,
    SiteUsersAccessDeleteDialogComponent,
  ],
  entryComponents: [SiteUsersAccessDeleteDialogComponent],
})
export class SiteUsersAccessModule {}
