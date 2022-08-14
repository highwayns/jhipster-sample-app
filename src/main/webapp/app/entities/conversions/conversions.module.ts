import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ConversionsComponent } from './list/conversions.component';
import { ConversionsDetailComponent } from './detail/conversions-detail.component';
import { ConversionsUpdateComponent } from './update/conversions-update.component';
import { ConversionsDeleteDialogComponent } from './delete/conversions-delete-dialog.component';
import { ConversionsRoutingModule } from './route/conversions-routing.module';

@NgModule({
  imports: [SharedModule, ConversionsRoutingModule],
  declarations: [ConversionsComponent, ConversionsDetailComponent, ConversionsUpdateComponent, ConversionsDeleteDialogComponent],
  entryComponents: [ConversionsDeleteDialogComponent],
})
export class ConversionsModule {}
