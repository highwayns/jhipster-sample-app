import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SiteUsersCatchComponent } from './list/site-users-catch.component';
import { SiteUsersCatchDetailComponent } from './detail/site-users-catch-detail.component';
import { SiteUsersCatchUpdateComponent } from './update/site-users-catch-update.component';
import { SiteUsersCatchDeleteDialogComponent } from './delete/site-users-catch-delete-dialog.component';
import { SiteUsersCatchRoutingModule } from './route/site-users-catch-routing.module';

@NgModule({
  imports: [SharedModule, SiteUsersCatchRoutingModule],
  declarations: [
    SiteUsersCatchComponent,
    SiteUsersCatchDetailComponent,
    SiteUsersCatchUpdateComponent,
    SiteUsersCatchDeleteDialogComponent,
  ],
  entryComponents: [SiteUsersCatchDeleteDialogComponent],
})
export class SiteUsersCatchModule {}
