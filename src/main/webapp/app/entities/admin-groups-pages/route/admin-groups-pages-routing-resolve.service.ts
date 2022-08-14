import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdminGroupsPages, AdminGroupsPages } from '../admin-groups-pages.model';
import { AdminGroupsPagesService } from '../service/admin-groups-pages.service';

@Injectable({ providedIn: 'root' })
export class AdminGroupsPagesRoutingResolveService implements Resolve<IAdminGroupsPages> {
  constructor(protected service: AdminGroupsPagesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdminGroupsPages> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((adminGroupsPages: HttpResponse<AdminGroupsPages>) => {
          if (adminGroupsPages.body) {
            return of(adminGroupsPages.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdminGroupsPages());
  }
}
