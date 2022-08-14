import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ContentFilesComponent } from './list/content-files.component';
import { ContentFilesDetailComponent } from './detail/content-files-detail.component';
import { ContentFilesUpdateComponent } from './update/content-files-update.component';
import { ContentFilesDeleteDialogComponent } from './delete/content-files-delete-dialog.component';
import { ContentFilesRoutingModule } from './route/content-files-routing.module';

@NgModule({
  imports: [SharedModule, ContentFilesRoutingModule],
  declarations: [ContentFilesComponent, ContentFilesDetailComponent, ContentFilesUpdateComponent, ContentFilesDeleteDialogComponent],
  entryComponents: [ContentFilesDeleteDialogComponent],
})
export class ContentFilesModule {}
