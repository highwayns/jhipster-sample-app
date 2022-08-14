import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LangComponent } from '../list/lang.component';
import { LangDetailComponent } from '../detail/lang-detail.component';
import { LangUpdateComponent } from '../update/lang-update.component';
import { LangRoutingResolveService } from './lang-routing-resolve.service';

const langRoute: Routes = [
  {
    path: '',
    component: LangComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LangDetailComponent,
    resolve: {
      lang: LangRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LangUpdateComponent,
    resolve: {
      lang: LangRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LangUpdateComponent,
    resolve: {
      lang: LangRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(langRoute)],
  exports: [RouterModule],
})
export class LangRoutingModule {}
