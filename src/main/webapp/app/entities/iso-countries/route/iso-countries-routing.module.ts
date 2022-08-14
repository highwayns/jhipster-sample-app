import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IsoCountriesComponent } from '../list/iso-countries.component';
import { IsoCountriesDetailComponent } from '../detail/iso-countries-detail.component';
import { IsoCountriesUpdateComponent } from '../update/iso-countries-update.component';
import { IsoCountriesRoutingResolveService } from './iso-countries-routing-resolve.service';

const isoCountriesRoute: Routes = [
  {
    path: '',
    component: IsoCountriesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IsoCountriesDetailComponent,
    resolve: {
      isoCountries: IsoCountriesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IsoCountriesUpdateComponent,
    resolve: {
      isoCountries: IsoCountriesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IsoCountriesUpdateComponent,
    resolve: {
      isoCountries: IsoCountriesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(isoCountriesRoute)],
  exports: [RouterModule],
})
export class IsoCountriesRoutingModule {}
