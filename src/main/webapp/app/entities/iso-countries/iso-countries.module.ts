import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IsoCountriesComponent } from './list/iso-countries.component';
import { IsoCountriesDetailComponent } from './detail/iso-countries-detail.component';
import { IsoCountriesUpdateComponent } from './update/iso-countries-update.component';
import { IsoCountriesDeleteDialogComponent } from './delete/iso-countries-delete-dialog.component';
import { IsoCountriesRoutingModule } from './route/iso-countries-routing.module';

@NgModule({
  imports: [SharedModule, IsoCountriesRoutingModule],
  declarations: [IsoCountriesComponent, IsoCountriesDetailComponent, IsoCountriesUpdateComponent, IsoCountriesDeleteDialogComponent],
  entryComponents: [IsoCountriesDeleteDialogComponent],
})
export class IsoCountriesModule {}
