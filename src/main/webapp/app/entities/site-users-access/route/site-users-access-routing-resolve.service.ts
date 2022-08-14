import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISiteUsersAccess, SiteUsersAccess } from '../site-users-access.model';
import { SiteUsersAccessService } from '../service/site-users-access.service';

@Injectable({ providedIn: 'root' })
export class SiteUsersAccessRoutingResolveService implements Resolve<ISiteUsersAccess> {
  constructor(protected service: SiteUsersAccessService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISiteUsersAccess> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((siteUsersAccess: HttpResponse<SiteUsersAccess>) => {
          if (siteUsersAccess.body) {
            return of(siteUsersAccess.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SiteUsersAccess());
  }
}
