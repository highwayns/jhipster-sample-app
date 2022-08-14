import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LangComponent } from './list/lang.component';
import { LangDetailComponent } from './detail/lang-detail.component';
import { LangUpdateComponent } from './update/lang-update.component';
import { LangDeleteDialogComponent } from './delete/lang-delete-dialog.component';
import { LangRoutingModule } from './route/lang-routing.module';

@NgModule({
  imports: [SharedModule, LangRoutingModule],
  declarations: [LangComponent, LangDetailComponent, LangUpdateComponent, LangDeleteDialogComponent],
  entryComponents: [LangDeleteDialogComponent],
})
export class LangModule {}
