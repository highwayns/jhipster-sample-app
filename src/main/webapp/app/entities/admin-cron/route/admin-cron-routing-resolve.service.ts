import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdminCron, AdminCron } from '../admin-cron.model';
import { AdminCronService } from '../service/admin-cron.service';

@Injectable({ providedIn: 'root' })
export class AdminCronRoutingResolveService implements Resolve<IAdminCron> {
  constructor(protected service: AdminCronService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdminCron> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((adminCron: HttpResponse<AdminCron>) => {
          if (adminCron.body) {
            return of(adminCron.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdminCron());
  }
}
