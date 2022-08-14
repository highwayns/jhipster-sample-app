import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISiteUsers, SiteUsers } from '../site-users.model';
import { SiteUsersService } from '../service/site-users.service';

@Injectable({ providedIn: 'root' })
export class SiteUsersRoutingResolveService implements Resolve<ISiteUsers> {
  constructor(protected service: SiteUsersService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISiteUsers> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((siteUsers: HttpResponse<SiteUsers>) => {
          if (siteUsers.body) {
            return of(siteUsers.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SiteUsers());
  }
}
