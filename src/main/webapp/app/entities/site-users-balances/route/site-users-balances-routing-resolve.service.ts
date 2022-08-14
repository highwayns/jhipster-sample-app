import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISiteUsersBalances, SiteUsersBalances } from '../site-users-balances.model';
import { SiteUsersBalancesService } from '../service/site-users-balances.service';

@Injectable({ providedIn: 'root' })
export class SiteUsersBalancesRoutingResolveService implements Resolve<ISiteUsersBalances> {
  constructor(protected service: SiteUsersBalancesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISiteUsersBalances> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((siteUsersBalances: HttpResponse<SiteUsersBalances>) => {
          if (siteUsersBalances.body) {
            return of(siteUsersBalances.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SiteUsersBalances());
  }
}
