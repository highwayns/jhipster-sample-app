import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdminGroups, AdminGroups } from '../admin-groups.model';
import { AdminGroupsService } from '../service/admin-groups.service';

@Injectable({ providedIn: 'root' })
export class AdminGroupsRoutingResolveService implements Resolve<IAdminGroups> {
  constructor(protected service: AdminGroupsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdminGroups> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((adminGroups: HttpResponse<AdminGroups>) => {
          if (adminGroups.body) {
            return of(adminGroups.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdminGroups());
  }
}
