import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SiteUsersComponent } from './list/site-users.component';
import { SiteUsersDetailComponent } from './detail/site-users-detail.component';
import { SiteUsersUpdateComponent } from './update/site-users-update.component';
import { SiteUsersDeleteDialogComponent } from './delete/site-users-delete-dialog.component';
import { SiteUsersRoutingModule } from './route/site-users-routing.module';

@NgModule({
  imports: [SharedModule, SiteUsersRoutingModule],
  declarations: [SiteUsersComponent, SiteUsersDetailComponent, SiteUsersUpdateComponent, SiteUsersDeleteDialogComponent],
  entryComponents: [SiteUsersDeleteDialogComponent],
})
export class SiteUsersModule {}
