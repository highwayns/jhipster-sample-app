import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISiteUsersCatch, SiteUsersCatch } from '../site-users-catch.model';
import { SiteUsersCatchService } from '../service/site-users-catch.service';

@Injectable({ providedIn: 'root' })
export class SiteUsersCatchRoutingResolveService implements Resolve<ISiteUsersCatch> {
  constructor(protected service: SiteUsersCatchService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISiteUsersCatch> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((siteUsersCatch: HttpResponse<SiteUsersCatch>) => {
          if (siteUsersCatch.body) {
            return of(siteUsersCatch.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SiteUsersCatch());
  }
}
