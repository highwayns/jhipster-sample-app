import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdminPages, AdminPages } from '../admin-pages.model';
import { AdminPagesService } from '../service/admin-pages.service';

@Injectable({ providedIn: 'root' })
export class AdminPagesRoutingResolveService implements Resolve<IAdminPages> {
  constructor(protected service: AdminPagesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdminPages> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((adminPages: HttpResponse<AdminPages>) => {
          if (adminPages.body) {
            return of(adminPages.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdminPages());
  }
}
