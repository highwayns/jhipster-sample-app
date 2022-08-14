import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ChangeSettingsComponent } from '../list/change-settings.component';
import { ChangeSettingsDetailComponent } from '../detail/change-settings-detail.component';
import { ChangeSettingsUpdateComponent } from '../update/change-settings-update.component';
import { ChangeSettingsRoutingResolveService } from './change-settings-routing-resolve.service';

const changeSettingsRoute: Routes = [
  {
    path: '',
    component: ChangeSettingsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ChangeSettingsDetailComponent,
    resolve: {
      changeSettings: ChangeSettingsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ChangeSettingsUpdateComponent,
    resolve: {
      changeSettings: ChangeSettingsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ChangeSettingsUpdateComponent,
    resolve: {
      changeSettings: ChangeSettingsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(changeSettingsRoute)],
  exports: [RouterModule],
})
export class ChangeSettingsRoutingModule {}
