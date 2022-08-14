import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EmailsComponent } from '../list/emails.component';
import { EmailsDetailComponent } from '../detail/emails-detail.component';
import { EmailsUpdateComponent } from '../update/emails-update.component';
import { EmailsRoutingResolveService } from './emails-routing-resolve.service';

const emailsRoute: Routes = [
  {
    path: '',
    component: EmailsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmailsDetailComponent,
    resolve: {
      emails: EmailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmailsUpdateComponent,
    resolve: {
      emails: EmailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmailsUpdateComponent,
    resolve: {
      emails: EmailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(emailsRoute)],
  exports: [RouterModule],
})
export class EmailsRoutingModule {}
