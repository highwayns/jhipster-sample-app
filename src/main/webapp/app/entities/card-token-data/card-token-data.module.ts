import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CardTokenDataComponent } from './list/card-token-data.component';
import { CardTokenDataDetailComponent } from './detail/card-token-data-detail.component';
import { CardTokenDataUpdateComponent } from './update/card-token-data-update.component';
import { CardTokenDataDeleteDialogComponent } from './delete/card-token-data-delete-dialog.component';
import { CardTokenDataRoutingModule } from './route/card-token-data-routing.module';

@NgModule({
  imports: [SharedModule, CardTokenDataRoutingModule],
  declarations: [CardTokenDataComponent, CardTokenDataDetailComponent, CardTokenDataUpdateComponent, CardTokenDataDeleteDialogComponent],
  entryComponents: [CardTokenDataDeleteDialogComponent],
})
export class CardTokenDataModule {}
