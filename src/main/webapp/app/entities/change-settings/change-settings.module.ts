import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ChangeSettingsComponent } from './list/change-settings.component';
import { ChangeSettingsDetailComponent } from './detail/change-settings-detail.component';
import { ChangeSettingsUpdateComponent } from './update/change-settings-update.component';
import { ChangeSettingsDeleteDialogComponent } from './delete/change-settings-delete-dialog.component';
import { ChangeSettingsRoutingModule } from './route/change-settings-routing.module';

@NgModule({
  imports: [SharedModule, ChangeSettingsRoutingModule],
  declarations: [
    ChangeSettingsComponent,
    ChangeSettingsDetailComponent,
    ChangeSettingsUpdateComponent,
    ChangeSettingsDeleteDialogComponent,
  ],
  entryComponents: [ChangeSettingsDeleteDialogComponent],
})
export class ChangeSettingsModule {}
