import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SettingsFilesComponent } from './list/settings-files.component';
import { SettingsFilesDetailComponent } from './detail/settings-files-detail.component';
import { SettingsFilesUpdateComponent } from './update/settings-files-update.component';
import { SettingsFilesDeleteDialogComponent } from './delete/settings-files-delete-dialog.component';
import { SettingsFilesRoutingModule } from './route/settings-files-routing.module';

@NgModule({
  imports: [SharedModule, SettingsFilesRoutingModule],
  declarations: [SettingsFilesComponent, SettingsFilesDetailComponent, SettingsFilesUpdateComponent, SettingsFilesDeleteDialogComponent],
  entryComponents: [SettingsFilesDeleteDialogComponent],
})
export class SettingsFilesModule {}
