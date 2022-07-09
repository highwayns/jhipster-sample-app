import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResultAttributesComponent } from './list/result-attributes.component';
import { ResultAttributesDetailComponent } from './detail/result-attributes-detail.component';
import { ResultAttributesUpdateComponent } from './update/result-attributes-update.component';
import { ResultAttributesDeleteDialogComponent } from './delete/result-attributes-delete-dialog.component';
import { ResultAttributesRoutingModule } from './route/result-attributes-routing.module';

@NgModule({
  imports: [SharedModule, ResultAttributesRoutingModule],
  declarations: [
    ResultAttributesComponent,
    ResultAttributesDetailComponent,
    ResultAttributesUpdateComponent,
    ResultAttributesDeleteDialogComponent,
  ],
  entryComponents: [ResultAttributesDeleteDialogComponent],
})
export class ResultAttributesModule {}
