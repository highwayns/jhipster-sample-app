import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IssuerComponent } from './list/issuer.component';
import { IssuerDetailComponent } from './detail/issuer-detail.component';
import { IssuerUpdateComponent } from './update/issuer-update.component';
import { IssuerDeleteDialogComponent } from './delete/issuer-delete-dialog.component';
import { IssuerRoutingModule } from './route/issuer-routing.module';

@NgModule({
  imports: [SharedModule, IssuerRoutingModule],
  declarations: [IssuerComponent, IssuerDetailComponent, IssuerUpdateComponent, IssuerDeleteDialogComponent],
  entryComponents: [IssuerDeleteDialogComponent],
})
export class IssuerModule {}
