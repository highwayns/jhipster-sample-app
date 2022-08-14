import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ContentFilesComponent } from '../list/content-files.component';
import { ContentFilesDetailComponent } from '../detail/content-files-detail.component';
import { ContentFilesUpdateComponent } from '../update/content-files-update.component';
import { ContentFilesRoutingResolveService } from './content-files-routing-resolve.service';

const contentFilesRoute: Routes = [
  {
    path: '',
    component: ContentFilesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContentFilesDetailComponent,
    resolve: {
      contentFiles: ContentFilesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContentFilesUpdateComponent,
    resolve: {
      contentFiles: ContentFilesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContentFilesUpdateComponent,
    resolve: {
      contentFiles: ContentFilesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(contentFilesRoute)],
  exports: [RouterModule],
})
export class ContentFilesRoutingModule {}
