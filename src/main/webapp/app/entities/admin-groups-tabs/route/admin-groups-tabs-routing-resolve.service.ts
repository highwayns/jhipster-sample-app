import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdminGroupsTabs, AdminGroupsTabs } from '../admin-groups-tabs.model';
import { AdminGroupsTabsService } from '../service/admin-groups-tabs.service';

@Injectable({ providedIn: 'root' })
export class AdminGroupsTabsRoutingResolveService implements Resolve<IAdminGroupsTabs> {
  constructor(protected service: AdminGroupsTabsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdminGroupsTabs> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((adminGroupsTabs: HttpResponse<AdminGroupsTabs>) => {
          if (adminGroupsTabs.body) {
            return of(adminGroupsTabs.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdminGroupsTabs());
  }
}
