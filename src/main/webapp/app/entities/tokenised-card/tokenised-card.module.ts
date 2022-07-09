import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TokenisedCardComponent } from './list/tokenised-card.component';
import { TokenisedCardDetailComponent } from './detail/tokenised-card-detail.component';
import { TokenisedCardUpdateComponent } from './update/tokenised-card-update.component';
import { TokenisedCardDeleteDialogComponent } from './delete/tokenised-card-delete-dialog.component';
import { TokenisedCardRoutingModule } from './route/tokenised-card-routing.module';

@NgModule({
  imports: [SharedModule, TokenisedCardRoutingModule],
  declarations: [TokenisedCardComponent, TokenisedCardDetailComponent, TokenisedCardUpdateComponent, TokenisedCardDeleteDialogComponent],
  entryComponents: [TokenisedCardDeleteDialogComponent],
})
export class TokenisedCardModule {}
