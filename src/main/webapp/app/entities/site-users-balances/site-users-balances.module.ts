import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SiteUsersBalancesComponent } from './list/site-users-balances.component';
import { SiteUsersBalancesDetailComponent } from './detail/site-users-balances-detail.component';
import { SiteUsersBalancesUpdateComponent } from './update/site-users-balances-update.component';
import { SiteUsersBalancesDeleteDialogComponent } from './delete/site-users-balances-delete-dialog.component';
import { SiteUsersBalancesRoutingModule } from './route/site-users-balances-routing.module';

@NgModule({
  imports: [SharedModule, SiteUsersBalancesRoutingModule],
  declarations: [
    SiteUsersBalancesComponent,
    SiteUsersBalancesDetailComponent,
    SiteUsersBalancesUpdateComponent,
    SiteUsersBalancesDeleteDialogComponent,
  ],
  entryComponents: [SiteUsersBalancesDeleteDialogComponent],
})
export class SiteUsersBalancesModule {}
