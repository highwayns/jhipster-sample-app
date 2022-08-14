import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EmailsComponent } from './list/emails.component';
import { EmailsDetailComponent } from './detail/emails-detail.component';
import { EmailsUpdateComponent } from './update/emails-update.component';
import { EmailsDeleteDialogComponent } from './delete/emails-delete-dialog.component';
import { EmailsRoutingModule } from './route/emails-routing.module';

@NgModule({
  imports: [SharedModule, EmailsRoutingModule],
  declarations: [EmailsComponent, EmailsDetailComponent, EmailsUpdateComponent, EmailsDeleteDialogComponent],
  entryComponents: [EmailsDeleteDialogComponent],
})
export class EmailsModule {}
