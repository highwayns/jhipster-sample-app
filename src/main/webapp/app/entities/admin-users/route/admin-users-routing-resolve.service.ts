import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdminUsers, AdminUsers } from '../admin-users.model';
import { AdminUsersService } from '../service/admin-users.service';

@Injectable({ providedIn: 'root' })
export class AdminUsersRoutingResolveService implements Resolve<IAdminUsers> {
  constructor(protected service: AdminUsersService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdminUsers> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((adminUsers: HttpResponse<AdminUsers>) => {
          if (adminUsers.body) {
            return of(adminUsers.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdminUsers());
  }
}
