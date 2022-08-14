import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { InnodbLockMonitorComponent } from '../list/innodb-lock-monitor.component';
import { InnodbLockMonitorDetailComponent } from '../detail/innodb-lock-monitor-detail.component';
import { InnodbLockMonitorUpdateComponent } from '../update/innodb-lock-monitor-update.component';
import { InnodbLockMonitorRoutingResolveService } from './innodb-lock-monitor-routing-resolve.service';

const innodbLockMonitorRoute: Routes = [
  {
    path: '',
    component: InnodbLockMonitorComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InnodbLockMonitorDetailComponent,
    resolve: {
      innodbLockMonitor: InnodbLockMonitorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InnodbLockMonitorUpdateComponent,
    resolve: {
      innodbLockMonitor: InnodbLockMonitorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InnodbLockMonitorUpdateComponent,
    resolve: {
      innodbLockMonitor: InnodbLockMonitorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(innodbLockMonitorRoute)],
  exports: [RouterModule],
})
export class InnodbLockMonitorRoutingModule {}
