import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdminTabs, AdminTabs } from '../admin-tabs.model';
import { AdminTabsService } from '../service/admin-tabs.service';

@Injectable({ providedIn: 'root' })
export class AdminTabsRoutingResolveService implements Resolve<IAdminTabs> {
  constructor(protected service: AdminTabsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdminTabs> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((adminTabs: HttpResponse<AdminTabs>) => {
          if (adminTabs.body) {
            return of(adminTabs.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdminTabs());
  }
}
