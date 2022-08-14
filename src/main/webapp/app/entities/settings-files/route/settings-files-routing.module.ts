import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SettingsFilesComponent } from '../list/settings-files.component';
import { SettingsFilesDetailComponent } from '../detail/settings-files-detail.component';
import { SettingsFilesUpdateComponent } from '../update/settings-files-update.component';
import { SettingsFilesRoutingResolveService } from './settings-files-routing-resolve.service';

const settingsFilesRoute: Routes = [
  {
    path: '',
    component: SettingsFilesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SettingsFilesDetailComponent,
    resolve: {
      settingsFiles: SettingsFilesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SettingsFilesUpdateComponent,
    resolve: {
      settingsFiles: SettingsFilesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SettingsFilesUpdateComponent,
    resolve: {
      settingsFiles: SettingsFilesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(settingsFilesRoute)],
  exports: [RouterModule],
})
export class SettingsFilesRoutingModule {}
