import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AdminUsersComponent } from './list/admin-users.component';
import { AdminUsersDetailComponent } from './detail/admin-users-detail.component';
import { AdminUsersUpdateComponent } from './update/admin-users-update.component';
import { AdminUsersDeleteDialogComponent } from './delete/admin-users-delete-dialog.component';
import { AdminUsersRoutingModule } from './route/admin-users-routing.module';

@NgModule({
  imports: [SharedModule, AdminUsersRoutingModule],
  declarations: [AdminUsersComponent, AdminUsersDetailComponent, AdminUsersUpdateComponent, AdminUsersDeleteDialogComponent],
  entryComponents: [AdminUsersDeleteDialogComponent],
})
export class AdminUsersModule {}
