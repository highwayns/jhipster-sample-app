import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LinksComponent } from './list/links.component';
import { LinksDetailComponent } from './detail/links-detail.component';
import { LinksUpdateComponent } from './update/links-update.component';
import { LinksDeleteDialogComponent } from './delete/links-delete-dialog.component';
import { LinksRoutingModule } from './route/links-routing.module';

@NgModule({
  imports: [SharedModule, LinksRoutingModule],
  declarations: [LinksComponent, LinksDetailComponent, LinksUpdateComponent, LinksDeleteDialogComponent],
  entryComponents: [LinksDeleteDialogComponent],
})
export class LinksModule {}
