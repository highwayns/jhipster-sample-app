import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CurrencysComponent } from './list/currencys.component';
import { CurrencysDetailComponent } from './detail/currencys-detail.component';
import { CurrencysUpdateComponent } from './update/currencys-update.component';
import { CurrencysDeleteDialogComponent } from './delete/currencys-delete-dialog.component';
import { CurrencysRoutingModule } from './route/currencys-routing.module';

@NgModule({
  imports: [SharedModule, CurrencysRoutingModule],
  declarations: [CurrencysComponent, CurrencysDetailComponent, CurrencysUpdateComponent, CurrencysDeleteDialogComponent],
  entryComponents: [CurrencysDeleteDialogComponent],
})
export class CurrencysModule {}
